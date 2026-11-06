# Compose 侧滑抽屉（底层显露式）实现原理学习笔记

> 对应源码：`zt-leftsidenav/src/main/java/com/example/jetlagged/JetLaggedDrawer.kt`

---

## 一、 核心设计思路

传统的侧滑抽屉（如 `ModalNavigationDrawer`）通常是**“主界面不动，抽屉从屏幕左侧滑出来盖在主界面上方”**。

而 `JetLaggedDrawer` 采用的是**“底层显露式（Reveal Drawer）”**设计：
1. **底层（抽屉菜单层）**：始终固定铺在最底下，位置和大小都不变。
2. **顶层（主页面内容层）**：平时全屏覆盖在底层之上，完全挡住底层菜单。
3. **交互形变**：当用户向右滑动或点击菜单按钮时，**顶层主界面向右平移、等比缩小、变成圆角卡片并带阴影**，从而让底层的抽屉菜单自然“显露”出来。

```text
  ┌───────────────────────────────────────────────┐
  │ 底层：HomeScreenDrawerContents (抽屉菜单)       │  <-- 固定不动，一直都在底下
  │  ┌─────────────────────────────────────────┐  │
  │  │ 顶层：ScreenContents (主页面内容)         │  │  <-- 平时全屏盖住底层；
  │  │   - translationX: 0px -> 300.dp         │  │      向右滑动 + 缩小 + 变圆角时，
  │  │   - scaleX / Y:   1.0 -> 0.8            │  │      底层的菜单就自然“露”出来了
  │  │   - shape:        0dp -> 32dp 圆角       │  │
  │  │   - elevation:    32f 阴影               │  │
  │  └─────────────────────────────────────────┘  │
  └───────────────────────────────────────────────┘
```

---

## 二、 关键实现步骤拆解

### 1. 视图层级堆叠（Z 轴顺序）

在 `HomeScreenDrawer` 的根容器 `Surface` 中，依次摆放了两个 Composable：

```kotlin
Surface(modifier = Modifier.fillMaxSize()) {
    // ... 状态声明 ...

    // 1. 先调用：绘制在【底层】
    HomeScreenDrawerContents(
        selectedScreen = screenState,
        onScreenSelected = { screen -> screenState = screen },
    )

    // 2. 后调用：绘制在【顶层】，默认全屏遮挡底层
    ScreenContents(
        selectedScreen = screenState,
        onDrawerClicked = ::toggleDrawerState,
        modifier = Modifier
            .graphicsLayer { /* 位移、缩放、圆角变换 */ }
            .draggable( /* 水平拖拽手势 */ )
    )
}
```

* 在 Compose 中，同一层级下**先声明的组件先绘制（在底层），后声明的组件后绘制（在顶层）**。
* 因为 `ScreenContents` 内部包含了带有背景色的全屏页面，当它没有发生位移时，底层的 `HomeScreenDrawerContents` 被完全遮挡。

---

### 2. 核心驱动状态与物理边界（`Animatable`）

整个抽屉的开合动画和手势跟随，全部由一个 `Animatable<Float>` 类型的变量 `translationX` 驱动：

```kotlin
// 记录顶层主界面向右偏移的像素值（px），初始为 0f（关闭状态）
val translationX = remember {
    Animatable(0f)
}

// 将抽屉最大宽度 300.dp 转换为像素值（px）
val drawerWidth = with(LocalDensity.current) {
    DrawerWidth.toPx() // DrawerWidth = 300.dp
}

// 限制 translationX 的取值范围只能在 [0f, drawerWidth] 之间
translationX.updateBounds(0f, drawerWidth)
```

* **`updateBounds(0f, drawerWidth)`**：极其关键的保护机制。它限制了无论手指往左怎么滑，`translationX.value` 最小只能是 `0f`；往右怎么滑，最大只能是 `drawerWidth`。
* **开合进度百分比（`fraction`）**：
  $$\text{fraction} = \frac{\text{translationX.value}}{\text{drawerWidth}} \in [0.0, 1.0]$$
  * 当 `fraction == 0f` 时：抽屉完全关闭。
  * 当 `fraction == 1f` 时：抽屉完全打开。

---

### 3. 视觉形变：`Modifier.graphicsLayer` + `lerp` 线性插值

顶层主界面在向右滑开时，同时发生了**平移、缩放、圆角裁剪、阴影投射**四种变化，全部在 `Modifier.graphicsLayer` 中完成：

```kotlin
.graphicsLayer {
    // 1. 水平平移：直接赋值为当前的偏移像素
    this.translationX = translationX.value

    // 2. 等比缩放：随着打开进度从 0 -> 1，缩放比例从 1.0f 线性过渡到 0.8f
    val scale = lerp(1f, 0.8f, translationX.value / drawerWidth)
    this.scaleX = scale
    this.scaleY = scale

    // 3. 动态圆角：随着打开进度从 0 -> 1，圆角半径从 0px 线性过渡到 32.dp
    val roundedCorners = lerp(0f, 32.dp.toPx(), translationX.value / drawerWidth)
    this.shape = RoundedCornerShape(roundedCorners)
    this.clip = true

    // 4. 阴影高度：赋予立体悬浮卡片感
    this.shadowElevation = 32f
}
```

#### 为什么用 `graphicsLayer { ... }` 而不是 `Modifier.offset` / `Modifier.scale`？
* **性能极佳**：Compose 的渲染分为三个阶段：**Composition（组合） -> Layout（布局） -> Drawing（绘制）**。
* 在 `graphicsLayer` 的 Lambda 闭包内读取 `translationX.value`，属于在 **Drawing（绘制阶段）** 读取状态。
* 手指滑动的每一帧，只会更新 GPU 渲染图层的变换矩阵（RenderNode 属性），**完全不会触发组件的 Recomposition（重组）和 Relayout（重新测量布局）**。

---

### 4. 点击开关抽屉（`animateTo`）

基于 `translationX`，封装了打开、关闭、切换三个函数，供顶部导航栏的菜单按钮调用：

```kotlin
suspend fun closeDrawer(velocity: Float = 0f) {
    translationX.animateTo(targetValue = 0f, initialVelocity = velocity)
    drawerState = DrawerState.Closed
}

suspend fun openDrawer(velocity: Float = 0f) {
    translationX.animateTo(targetValue = drawerWidth, initialVelocity = velocity)
    drawerState = DrawerState.Open
}

fun toggleDrawerState() {
    coroutineScope.launch {
        if (drawerState == DrawerState.Open) {
            closeDrawer()
        } else {
            openDrawer()
        }
    }
}
```

---

### 5. 手势拖拽与松手惯性吸附（`Modifier.draggable`）

手势交互分为 **“手指拖动中”** 和 **“手指松开瞬间”** 两部分：

#### （1）手指拖动中：实时跟手（`snapTo`）

```kotlin
val draggableState = rememberDraggableState(onDelta = { dragAmount ->
    coroutineScope.launch {
        // dragAmount 是手指单次移动的增量像素，用 snapTo 立即跳转到新位置
        translationX.snapTo(translationX.value + dragAmount)
    }
})
```

#### （2）手指松开时：惯性预测与自动吸附（`onDragStopped`）

松手时，并不是简单判断 `translationX.value > drawerWidth * 0.5`，而是结合**松手时的滑动速度 `velocity`** 做物理惯性预测：

```kotlin
val decay = rememberSplineBasedDecay<Float>()

.draggable(
    state = draggableState,
    orientation = Orientation.Horizontal,
    onDragStopped = { velocity ->
        // 1. 预测惯性终点：如果按照当前位置和松手速度自然减速滑行，最终会停在哪里？
        val targetOffsetX = decay.calculateTargetValue(
            initialValue = translationX.value,
            initialVelocity = velocity,
        )

        coroutineScope.launch {
            // 2. 决定最终吸附目标：预测落点超过抽屉宽度的一半，则目标为全开(drawerWidth)，否则为全关(0f)
            val actualTargetX = if (targetOffsetX > drawerWidth * 0.5) {
                drawerWidth
            } else {
                0f
            }

            // 3. 判断纯粹靠自然惯性衰减能否刚好越过目标点（因为有 updateBounds 兜底，越过边界会被截停在边界上）
            val targetDifference = (actualTargetX - targetOffsetX)
            val canReachTargetWithDecay =
                (targetOffsetX > actualTargetX && velocity > 0f && targetDifference > 0f) ||
                (targetOffsetX < actualTargetX && velocity < 0 && targetDifference < 0f)

            if (canReachTargetWithDecay) {
                // 靠惯性自然滑过去
                translationX.animateDecay(
                    initialVelocity = velocity,
                    animationSpec = decay,
                )
            } else {
                // 速度不够或方向相反，主动播放动画吸附到目标点（保留初始速度使衔接自然）
                translationX.animateTo(actualTargetX, initialVelocity = velocity)
            }

            // 4. 更新最终的抽屉枚举状态
            drawerState = if (actualTargetX == drawerWidth) {
                DrawerState.Open
            } else {
                DrawerState.Closed
            }
        }
    },
)
```

> **进阶提示**：在较新的 Compose Foundation 版本中，可以使用官方提供的 `Modifier.anchoredDraggable()` 来简化上述手动计算 `decay` 的代码，只需定义 `Closed -> 0f` 和 `Open -> drawerWidth` 两个锚点即可自动完成手势吸附。

---

### 6. Android 预测式返回手势（`PredictiveBackHandler`）

当抽屉处于打开状态时，用户从手机屏幕边缘向内侧滑返回，不应该直接退出 App，而应该**跟随返回手势实时把抽屉关上**：

```kotlin
val velocityTracker = remember { VelocityTracker() }

// 仅当抽屉处于 Open 状态时拦截系统返回手势
PredictiveBackHandler(drawerState == DrawerState.Open) { progress ->
    try {
        // 1. 手指在屏幕边缘滑动过程中，progress 会持续发出 BackEvent (progress: 0f -> 1f)
        progress.collect { backEvent ->
            val targetSize = (drawerWidth - (drawerWidth * backEvent.progress))
            translationX.snapTo(targetSize)
            // 记录轨迹用于计算松手速度
            velocityTracker.addPosition(
                SystemClock.uptimeMillis(),
                Offset(backEvent.touchX, backEvent.touchY),
            )
        }
        // 2. 用户松手完成返回：关闭抽屉
        closeDrawer(velocityTracker.calculateVelocity().x)
    } catch (_: CancellationException) {
        // 3. 用户半途取消了返回手势（手指滑回边缘）：重新打开抽屉
        openDrawer(velocityTracker.calculateVelocity().x)
    }
    velocityTracker.resetTracking()
}
```

---

## 三、 核心知识点总结表

| 关键 API / 技术点 | 在本效果中的作用 |
| :--- | :--- |
| **同级组件顺序堆叠** | 先写 `HomeScreenDrawerContents`（底层），后写 `ScreenContents`（顶层遮罩）。 |
| **`Animatable(0f)`** | 维护顶层主界面的当前水平偏移量，支持携程动画（`animateTo`）和瞬移（`snapTo`）。 |
| **`updateBounds(0f, max)`** | 限制 `Animatable` 的物理边界，防止左滑过头或右滑超出抽屉宽度。 |
| **`lerp(start, stop, fraction)`** | 线性插值工具函数，将 `[0, 1]` 的滑动比例映射为缩放比例 `[1.0, 0.8]` 和圆角 `[0dp, 32dp]`。 |
| **`Modifier.graphicsLayer { }`** | 在绘制阶段执行平移、缩放、圆角裁剪和阴影，避免重组与重布局，保证 60/120fps 满帧流畅。 |
| **`Modifier.draggable`** | 监听水平滑动手势增量，并在 `onDragStopped` 中结合 `rememberSplineBasedDecay` 实现惯性吸附。 |
| **`PredictiveBackHandler`** | 适配 Android 13/14+ 预测式返回手势，让系统返回手势也能实时驱动抽屉收起动画。 |
