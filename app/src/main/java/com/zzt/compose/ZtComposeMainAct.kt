package com.zzt.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zzt.compose.theme.ZZTComposeTheme
import com.zzt.compose.ui.main.ZtFullScreenNavUI
import com.zzt.compose.ui.main.ZtNavMainView
import kotlin.math.log

class ZtComposeMainAct : ComponentActivity() {
    val TAG = ZtComposeMainAct::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 使用 rememberNavController 来处理全屏和非全屏之间的切换
            val mainNavController = rememberNavController()

            NavHost(navController = mainNavController, startDestination = "main_ui") {
                composable("main_ui") {
                    ZtNavMainView(mainNavController)
                }
                composable(
                    route = "full_screen/{viewPager}",
                    arguments = listOf(navArgument("viewPager") { type = NavType.StringType }),
                    content = { backStackEntry ->
                        val viewPager = backStackEntry.arguments?.getString("viewPager")
                        Log.d(TAG, "composable backStackEntry viewPager: $viewPager")
                        ZtFullScreenNavUI(mainNavController, viewPager)
                    }
                )
            }
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ZZTComposeTheme {
        ZtNavMainView()
    }
}