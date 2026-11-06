package com.example.zt_customnavhost

/**
 * @author: zeting
 * @date: 2025/7/8
 *
 */
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Stack

// 1. 定义导航目的地 (Destinations)
// 使用密封类 (sealed class) 来表示应用中的所有可能的导航目的地。
// 这样可以确保类型安全，并且方便地传递参数。
sealed class AppDestination {
    // 没有参数的固定目的地
    object Home : AppDestination()
    object Profile : AppDestination()

    // 带有参数的目的地，使用数据类来封装参数
    data class Detail(val itemId: String) : AppDestination()
    data class Settings(val initialTab: Int? = null) : AppDestination()

    // 可以为每个目的地定义一个路由字符串，方便调试或外部链接解析
    val route: String
        get() = when (this) {
            Home -> "home"
            Profile -> "profile"
            is Detail -> "detail/{${Keys.ITEM_ID}}" // 路由模板
            is Settings -> "settings"
        }

    // 定义参数键，避免硬编码字符串
    object Keys {
        const val ITEM_ID = "itemId"
        const val INITIAL_TAB = "initialTab"
    }

    // 辅助函数：根据参数创建实际的路由字符串，用于导航
    fun createRoute(vararg args: Pair<String, Any>): String {
        return when (this) {
            is Detail -> "detail/${args.first { it.first == Keys.ITEM_ID }.second}"
            is Settings -> {
                val tabArg = args.firstOrNull { it.first == Keys.INITIAL_TAB }
                if (tabArg != null) "settings?${Keys.INITIAL_TAB}=${tabArg.second}" else "settings"
            }

            else -> route
        }
    }
}

// 2. 实现自定义导航管理器 (CustomNavigator)
// 这个类负责管理导航栈（后退栈）的状态，并提供导航操作。
class CustomNavigator {
    // 使用 MutableStateFlow 来持有导航栈的状态，以便 Composable 可以观察其变化
    private val _backStack = MutableStateFlow<List<AppDestination>>(emptyList())
    val backStack: StateFlow<List<AppDestination>> = _backStack.asStateFlow()

    // 初始块：设置应用的起始目的地
    init {
        navigateTo(AppDestination.Home)
    }

    /**
     * 导航到新的目的地。
     * @param destination 目标目的地。
     * @param popUpTo 可选，在导航到新目的地前，将栈弹出直到指定目的地。
     * @param inclusive 如果 popUpTo 不为空，是否也弹出 popUpTo 自身。
     */
    fun navigateTo(
        destination: AppDestination,
        popUpTo: AppDestination? = null,
        inclusive: Boolean = false
    ) {
        _backStack.value = _backStack.value.toMutableList().apply {
            if (popUpTo != null) {
                val index = lastIndexOf(popUpTo)
                if (index != -1) {
                    val newSize = if (inclusive) index else index + 1
                    if (newSize < size) { // 确保新大小不会超出当前栈的范围
                        subList(newSize, size).clear() // 弹出指定范围内的元素
                    }
                }
            }
            add(destination) // 将新目的地添加到栈顶
        }
        // 可以在这里添加日志，方便调试导航路径
        // Log.d("CustomNavigator", "Navigated to $destination, current stack: ${_backStack.value.map { it.route }}")
    }

    /**
     * 执行返回操作（弹出栈顶目的地）。
     * @return true 如果成功返回（栈中还有其他元素），false 如果栈已空（只剩一个根页面）。
     */
    fun popBack(): Boolean {
        if (_backStack.value.size > 1) { // 至少需要两个元素才能执行 pop（保留一个根页面）
            _backStack.value = _backStack.value.toMutableList().apply {
                // 移除栈顶元素
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
                    removeLast()
                } else {
                    removeAt(_backStack.value.size - 1)
                }
            }
            // Log.d("CustomNavigator", "Popped, current stack: ${_backStack.value.map { it.route }}")
            return true
        }
        // Log.d("CustomNavigator", "Pop failed, stack is empty or has only one element.")
        return false // 栈已空或只剩一个元素（通常是根页面）
    }

    /**
     * 获取当前栈顶的目的地。
     */
    fun currentDestination(): AppDestination? {
        return _backStack.value.lastOrNull()
    }
}

// 3. 通过 CompositionLocal 提供导航管理器
// 使用 CompositionLocal 可以让 Composable 树中的任何子组件轻松访问 CustomNavigator 实例，而无需层层传递。
val LocalCustomNavigator = staticCompositionLocalOf<CustomNavigator> {
    error("No CustomNavigator provided") // 如果没有提供 CustomNavigator，则抛出错误
}

// 4. 构建导航宿主 (CustomNavHost)
// 这是一个 Composable 函数，它观察 CustomNavigator 的状态，并根据当前的目的地渲染不同的 UI。
@Composable
fun CustomNavHost(
    // 从 CompositionLocal 获取 CustomNavigator 实例
    navigator: CustomNavigator = LocalCustomNavigator.current
) {
    // 观察导航栈的变化，当栈顶目的地改变时，UI 会自动重组
    val currentDestinationStack by navigator.backStack.collectAsState()
    val topDestination = currentDestinationStack.lastOrNull()

    // 处理系统返回键：当栈中有多个目的地时，按下返回键会执行 popBack 操作
    BackHandler(enabled = currentDestinationStack.size > 1) {
        navigator.popBack()
    }

    // 使用 AnimatedContent 实现页面之间的动画过渡
    AnimatedContent(
        targetState = topDestination,
        modifier = Modifier.fillMaxSize(),
        // 定义页面切换时的动画效果
        transitionSpec = {
            // 示例：新页面从右侧滑入，旧页面从左侧滑出 (标准的 Push 动画)
            slideInHorizontally { it } + fadeIn() togetherWith
                    slideOutHorizontally { -it } + fadeOut()
            // 你可以根据需求定义更复杂的动画，例如：
            // (slideInHorizontally(initialOffsetX = { it }) + fadeIn()).togetherWith(slideOutHorizontally(targetOffsetX = { -it }) + fadeOut())
        }, label = "CustomNavHostAnimation"
    ) { targetDestination ->
        // 根据当前目的地渲染对应的 Composable 屏幕
        when (targetDestination) {
            AppDestination.Home -> HomeScreen()
            AppDestination.Profile -> ProfileScreen()
            is AppDestination.Detail -> DetailScreen(itemId = targetDestination.itemId)
            is AppDestination.Settings -> SettingsScreen(initialTab = targetDestination.initialTab)
            null -> EmptyScreen() // 当栈为空时（理论上不会发生，因为有初始目的地），显示一个空屏幕
        }
    }
}

// 5. 示例屏幕 Composable
// 这些是实际的 UI 页面，它们会通过 LocalCustomNavigator 访问导航器来执行导航操作。

@Composable
fun HomeScreen() {
    val navigator = LocalCustomNavigator.current // 获取导航器实例
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "主页", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navigator.navigateTo(AppDestination.Detail("item_abc_123")) }) {
            Text("前往详情页 (item_abc_123)")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navigator.navigateTo(AppDestination.Profile) }) {
            Text("前往个人资料页")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navigator.navigateTo(AppDestination.Settings(initialTab = 1)) }) {
            Text("前往设置页 (Tab 1)")
        }
    }
}

@Composable
fun DetailScreen(itemId: String) {
    val navigator = LocalCustomNavigator.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "详情页", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "项目 ID: $itemId", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navigator.popBack() }) {
            Text("返回")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // 示例：从详情页直接回到主页，并清空详情页之前的栈
        Button(onClick = {
            navigator.navigateTo(
                AppDestination.Home,
                popUpTo = AppDestination.Home,
                inclusive = true
            )
        }) {
            Text("返回主页 (清除栈)")
        }
    }
}

@Composable
fun ProfileScreen() {
    val navigator = LocalCustomNavigator.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "个人资料页", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navigator.popBack() }) {
            Text("返回")
        }
    }
}

@Composable
fun SettingsScreen(initialTab: Int?) {
    val navigator = LocalCustomNavigator.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "设置页", style = MaterialTheme.typography.headlineMedium)
        initialTab?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "初始 Tab: $it", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navigator.popBack() }) {
            Text("返回")
        }
    }
}

@Composable
fun EmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "加载中或未找到页面...", style = MaterialTheme.typography.headlineSmall)
    }
}

// 6. 应用入口点 (MainActivity 或 App Composable)
@Composable
fun CustomNavigationApp(modifier: Modifier = Modifier) {
    // 在应用的根 Composable 中创建并记住 CustomNavigator 实例
    val navigator = remember { CustomNavigator() }

    // 通过 CompositionLocalProvider 将 navigator 提供给整个 Composable 树
    CompositionLocalProvider(LocalCustomNavigator provides navigator) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            // 放置自定义的导航宿主
            CustomNavHost()
        }
    }
}

// 预览
@Preview(showBackground = true)
@Composable
fun PreviewCustomNavigationApp() {
    MaterialTheme {
        CustomNavigationApp()
    }
}
