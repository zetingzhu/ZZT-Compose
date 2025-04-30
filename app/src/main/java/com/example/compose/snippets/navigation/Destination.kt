/*
 * Copyright 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.snippets.navigation

enum class Destination(val route: String, val title: String) {
    ArchitectingExamples("ArchitectingExamples", "架构相关"),
    PaddingExamples("paddingExamples", "边距修饰符"),
    ImageShapeExamples("ImageShapeExamples", "Image Shape Examples 图片形状"),
    AnimationQuickGuideExamples("animationExamples", "Animation Examples 动画"),
    ComponentsExamples("topComponents", "Top Compose Components"),
    ScreenshotExample("screenshotExample", "Screenshot Examples"),
    SharedElementExamples("sharedElement", "Shared elements"),
    PagerExamples("pagerExamples", "Pager examples"),
    PagerExamplesV2("pagerExamplesV2", "Pager 分页显示"),
    PullToRefreshExamples("pullComponents", "下拉刷新"),
    ResourceExamples("resourceExamples", "资源 Resource 使用"),
    ClickExamples("ClickExamples", "手势，输入，轻触，点击"),
}

// Enum class for compose components navigation screen.
enum class TopComponentsDestination(val route: String, val title: String) {
    FlowLayoutExamples("FlowLayoutExamples", "FlowRow 或 FlowColumn 使用"),
    CustomLayoutExamples("customLayoutExamples", "自定义布局"),
    AppBarExamples("appBarExamples", "App bars 应用栏"),
    ButtonExamples("buttonExamples", "Button"),
    CardExamples("cardExamples", "Card 卡"),
    CheckboxExamples("checkboxExamples", "Checkbox 复选框"),
    ChipExamples("chipExamples", "Chip 条状标签"),
    DatePickerExamples("datePickerExamples", "Date Pickers 日期选择框"),
    DialogExamples("dialogExamples", "Dialog 对话框"),
    DividerExamples("dividerExamples", "Dividers 分割线"),
    FloatingActionButtonExamples("floatingActionButtonExamples", "Floating Action Button"),
    LazyListExamples("lazyListExamples", "列表，网格"),
    LazyListExamplesV2("lazyListExamplesV2", "列表，网格 V2"),
    MenusExample("menusExamples", "Menus"),
    DrawerExample("drawerExample", "抽屉式导航栏使用"),
    NavigationDrawerExamples("navigationDrawerExamples", "Navigation drawer"),
    ProgressIndicatorExamples("progressIndicatorExamples", "Progress Indicators 进度条"),
    RadioButtonExamples("radioButtonExamples", "单选按钮"),
    ScaffoldExample("scaffoldExample", "Scaffold"),
    SegmentedButtonExamples("segmentedButtonExamples", "Segmented button 分段按钮"),
    SliderExamples("sliderExamples", "Slider 滑块"),
    SnackbarExamples("SnackbarExamples", "Snackbar 快捷信息栏"),
    SwitchExamples("switchExamples", "Switch 切换"),
    TimePickerExamples("timePickerExamples", "Time Pickers 时间选择"),
    BadgeExamples("badgeExamples", "Badges 标志"),
    PartialBottomSheet("partialBottomSheets", "Partial Bottom Sheet 部分底部动作条"),

    CarouselExamples("carouselExamples", "Carousel 轮播"),
    TooltipExamples("tooltipExamples", "Tooltips"),
    SwipeToDismissBoxExamples("swipeToDismissBoxExamples", "Swipe to dismiss box examples"),


    SearchBarExamples("searchBarExamples", "Search bar, 不好用"),
}

enum class PullToRefreshComponents(val route: String, val title: String) {
    PullToRefreshV1("pullToRefreshExamplesV1", "下拉刷新 V1"),
    PullToRefreshV2("pullToRefreshExamplesV2", "下拉刷新 V2"),
    PullToRefreshV3("pullToRefreshExamplesV3", "下拉刷新 V2"),
}

enum class ImageShapeComponents(val route: String, val title: String) {
    ImageExamples("imageExamples", "Image Examples"),
    ImageBitmapVectorExamples("ImageBitmapVectorExamples", "ImageBitmap 与 ImageVector"),
    CanvasExamples("canvasExamples", "Canvas 简单使用 "),
    CanvasDrawExamples(
        "CanvasDrawExamples",
        "Canvas Modifier.drawWithContent：选择绘制顺序，手电筒效果 "
    ),
    ModifierDrawBehind("ModifierDrawBehind", "Canvas Modifier.drawBehind：在可组合项后面绘制"),
    CreateBitmapFromGraphicsLayer("CreateBitmapFromGraphicsLayer", "将可组合项的内容写入位图"),
    BrushExamples("brushExamples", "Brush Examples Brush：渐变和着色器 "),
    FadingLazyComments("FadingLazyComments", "淡化"),
    StackedAvatars("StackedAvatars", "堆叠头像"),
    ShapeExamples("shapeExamples", "画图形状，裁剪，动画"),
    ShapesExamples("shapesExamples", "Shapes Examples 裁剪图片并在裁剪区域周围应用阴影"),
}

enum class AnimComponents(val route: String, val title: String) {
    AnimNormal("AnimNormal", "普通动画，平移，颜色，位置"),
    AnimNav("AnimNav", "在导航到不同目的地时添加动画"),
    AnimEnterTransition("AnimEnterTransition", "自定义过渡效果"),
    AnimGesture("AnimGestureV1", "手势动画 V1"),
    AnimGestureV2("AnimGestureV2", "手势动画 V2"),
    AnimSharV1("AnimSharV1", "共享元素"),
    AnimSharV2("AnimSharV2", "共享元素"),
    AnimSharV3("AnimSharV3", "常见共享元素用例-异步图片"),
    AnimSharV4("AnimSharV4", "常见共享元素用例-文本"),
    AnimSharNav("AnimSharNav", "使用 Navigation Compose 构建共享元素"),
}

enum class ClickComponents(val route: String, val title: String) {
    ClickExample("ClickExample", "各种点击事件"),
    ClickExampleV2("ClickExampleV2", "点按并按下"),
    ClickExampleV3("ClickExampleV3", "点击 放大图片，双击缩放图片，背景关闭图片"),
    ClickExampleV4("ClickExampleV4", "长按弹出底部弹框"),
    ClickExampleV5("ClickExampleV5", "长按选中数据个数"),
    ScrollExampleV1("ScrollExampleV1", "普通滚动"),
}