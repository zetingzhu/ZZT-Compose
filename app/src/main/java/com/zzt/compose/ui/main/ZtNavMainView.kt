package com.zzt.compose.ui.main

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration.Short
import androidx.compose.material3.SnackbarResult.ActionPerformed
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zzt.compose.R
import com.zzt.compose.ui.theme.ZZTComposeTheme
import kotlinx.coroutines.launch
import java.io.Serializable

/**
 * @author: zeting
 * @date: 2025/4/14
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZtNavMainView(
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                ZtNavDrawView()
            }
        },
        drawerState = drawerState
    ) {

        var currentSelect by remember {
            mutableStateOf(0)
        }

        val menuData = listOf(
            BottomItemData(Pages.PAGE_MAIN, "首页", Icons.Filled.Home),
            BottomItemData(Pages.PAGE_SETTING, "设置", Icons.Filled.Settings)
        )

        val navController = rememberNavController()
        //将底部栏菜单数据的路由名整成一个list
        val routes = menuData.map { it.route }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Navigation Drawer Example") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            Toast.makeText(
                                context,
                                "Search is not yet implemented in this configuration",
                                Toast.LENGTH_LONG
                            ).show()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search"
                            )
                        }
                    },
                )
            },
            bottomBar = {
                //只要当页面为首页或者为设置页面,才展示底部菜单栏
                if (currentDestination?.hierarchy?.any { routes.contains(it.route) } == true) {
                    NavigationBar() {
                        menuData.forEach { bottomItemData ->
                            NavigationBarItem(
                                //设置当前项是否为选中
                                selected = currentDestination.hierarchy.any {
                                    it.route?.contains(
                                        bottomItemData.route
                                    ) == true
                                },
                                icon = {
                                    Icon(
                                        imageVector = bottomItemData.icon,
                                        contentDescription = "点击按钮"
                                    )
                                },
                                label = {
                                    Text(
                                        text = (bottomItemData.label)
                                    )
                                },
                                onClick = {
                                    navController.navigate(bottomItemData.route) {
                                        //使用此方法,可以避免生成一个重复的路由堆栈
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        //避免重复选择会创建一个新的页面副本
                                        launchSingleTop = true
                                        //当重新选择之前已选择项目恢复页面状态
                                        restoreState = true
                                    }
                                },
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding)
            ) {
                NavHost(navController = navController, startDestination = Pages.PAGE_MAIN) {
                    //声明名为MainPage的页面路由
                    composable(Pages.PAGE_MAIN) {
                        MainPage() {
                            navController.navigate(Pages.PAGE_SETTING)
                        }
                    }
                    composable(Pages.PAGE_SETTING) {
                        SettingPage()
                    }
                }
            }
        }
    }
}


//页面的枚举类
object Pages {
    const val PAGE_MAIN = "main"
    const val PAGE_SETTING = "setting"
}


//底部菜单的实体数据类,包含路由,文字和图标
data class BottomItemData(val route: String, val label: String, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZZTComposeTheme {
        ZtNavMainView()
    }
}