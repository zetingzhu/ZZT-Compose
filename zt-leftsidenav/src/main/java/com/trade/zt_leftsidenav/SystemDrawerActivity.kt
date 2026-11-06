package com.trade.zt_leftsidenav

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trade.zt_leftsidenav.theme.JetLaggedTheme
import kotlinx.coroutines.launch

class SystemDrawerActivity : ComponentActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SystemDrawerActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            JetLaggedTheme {
                SystemLeftDrawerScreen()
            }
        }
    }
}

private enum class SystemDrawerMenu(val title: String, @DrawableRes val icon: Int) {
    Home("首页", R.drawable.ic_home),
    Sleep("睡眠", R.drawable.ic_bedtime),
    Leaderboard("排行榜", R.drawable.ic_leaderboard),
    Settings("设置", R.drawable.ic_settings),
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SystemLeftDrawerScreen() {
    // 1. 系统抽屉状态管理 (Closed / Open)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedMenu by remember { mutableStateOf(SystemDrawerMenu.Home) }

    // 2. Material3 官方标准侧滑抽屉控件：ModalNavigationDrawer
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true, // 支持手势从左侧边缘滑出
        drawerContent = {
            // 3. 抽屉面板容器：ModalDrawerSheet
            // - modifier = Modifier.width(240.dp)：自定义抽屉拉出来的宽度（默认是 360.dp，也可以用 fillMaxWidth(0.7f) 按屏幕比例）
            // - drawerShape：自定义抽屉圆角（仅右侧上下两个角有小圆角，设为 0.dp 则为完全直角）
            ModalDrawerSheet(
                modifier = Modifier.width(240.dp),
                drawerShape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp),
            ) {
                Text(
                    text = "系统左侧抽屉菜单",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 28.dp, vertical = 24.dp),
                )
                HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))

                SystemDrawerMenu.entries.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                            )
                        },
                        selected = selectedMenu == item,
                        shape = RoundedCornerShape(8.dp), // 自定义菜单项选中背景的圆角
                        onClick = {
                            selectedMenu = item
                            // 点击菜单项后关闭抽屉
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    )
                }
            }
        },
    ) {
        // 4. 主界面内容区域
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "系统抽屉 - ${selectedMenu.title}") },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }
                                }
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu),
                                contentDescription = "打开抽屉",
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                    ),
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "当前选中页面：${selectedMenu.title}",
                    style = MaterialTheme.typography.headlineSmall,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        scope.launch { drawerState.open() }
                    },
                ) {
                    Text(text = "点击打开左侧抽屉")
                }
            }
        }
    }
}
