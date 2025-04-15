package com.zzt.compose.ui.main

import NavigationDrawerExamples
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.snippets.animations.AnimationExamplesScreen
import com.example.compose.snippets.animations.sharedelement.PlaceholderSizeAnimated_Demo
import com.example.compose.snippets.components.CardExamples
import com.example.compose.snippets.components.ComponentsScreen
import com.example.compose.snippets.components.SwitchExamples
import com.example.compose.snippets.graphics.ApplyPolygonAsClipImage
import com.example.compose.snippets.graphics.BitmapFromComposableFullSnippet
import com.example.compose.snippets.graphics.BrushExamplesScreen
import com.example.compose.snippets.images.ImageExamplesScreen
import com.example.compose.snippets.landing.LandingScreen
import com.example.compose.snippets.navigation.Destination
import com.zzt.compose.theme.ZZTComposeTheme
import com.example.compose.snippets.animations.AnimationExamplesScreen
import com.example.compose.snippets.animations.sharedelement.PlaceholderSizeAnimated_Demo
import com.example.compose.snippets.components.AppBarExamples
import com.example.compose.snippets.components.BadgeExamples
import com.example.compose.snippets.components.ButtonExamples
import com.example.compose.snippets.components.CardExamples
import com.example.compose.snippets.components.CarouselExamples
import com.example.compose.snippets.components.CheckboxExamples
import com.example.compose.snippets.components.ChipExamples
import com.example.compose.snippets.components.ComponentsScreen
import com.example.compose.snippets.components.DatePickerExamples
import com.example.compose.snippets.components.DialogExamples
import com.example.compose.snippets.components.DividerExamples
import com.example.compose.snippets.components.FloatingActionButtonExamples
import com.example.compose.snippets.components.MenusExamples
import com.example.compose.snippets.components.PartialBottomSheet
import com.example.compose.snippets.components.ProgressIndicatorExamples
import com.example.compose.snippets.components.ScaffoldExample
import com.example.compose.snippets.components.SearchBarExamples
import com.example.compose.snippets.components.SegmentedButtonExamples
import com.example.compose.snippets.components.SliderExamples
import com.example.compose.snippets.components.SwipeToDismissBoxExamples
import com.example.compose.snippets.components.TimePickerExamples
import com.example.compose.snippets.components.TooltipExamples
import com.example.compose.snippets.layouts.PagerExamples
import com.example.compose.snippets.navigation.TopComponentsDestination

/**
 * @author: zeting
 * @date: 2025/4/15
 *
 */
@Composable
fun SystemPage() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(navController, startDestination = "LandingScreen") {
            composable("LandingScreen") {
                LandingScreen { navController.navigate(it.route) }
            }
            Destination.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        Destination.BrushExamples -> BrushExamplesScreen()
                        Destination.ImageExamples -> ImageExamplesScreen()
                        Destination.AnimationQuickGuideExamples -> AnimationExamplesScreen()
                        Destination.ScreenshotExample -> BitmapFromComposableFullSnippet()
                        Destination.ComponentsExamples -> ComponentsScreen {
                            navController.navigate(
                                it.route
                            )
                        }

                        Destination.ShapesExamples -> ApplyPolygonAsClipImage()
                        Destination.SharedElementExamples -> PlaceholderSizeAnimated_Demo()
                        Destination.PagerExamples -> PagerExamples()
                    }
                }
            }
            TopComponentsDestination.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        TopComponentsDestination.CardExamples -> CardExamples()
                        TopComponentsDestination.SwitchExamples -> SwitchExamples()
                        TopComponentsDestination.SliderExamples -> SliderExamples()
                        TopComponentsDestination.DialogExamples -> DialogExamples()
                        TopComponentsDestination.ChipExamples -> ChipExamples()
                        TopComponentsDestination.FloatingActionButtonExamples -> FloatingActionButtonExamples()
                        TopComponentsDestination.ButtonExamples -> ButtonExamples()
                        TopComponentsDestination.ProgressIndicatorExamples -> ProgressIndicatorExamples()
                        TopComponentsDestination.ScaffoldExample -> ScaffoldExample()
                        TopComponentsDestination.AppBarExamples -> AppBarExamples {
                            navController.popBackStack()
                        }

                        TopComponentsDestination.CheckboxExamples -> CheckboxExamples()
                        TopComponentsDestination.DividerExamples -> DividerExamples()
                        TopComponentsDestination.BadgeExamples -> BadgeExamples()
                        TopComponentsDestination.PartialBottomSheet -> PartialBottomSheet()
                        TopComponentsDestination.TimePickerExamples -> TimePickerExamples()
                        TopComponentsDestination.DatePickerExamples -> DatePickerExamples()
                        TopComponentsDestination.CarouselExamples -> CarouselExamples()
                        TopComponentsDestination.MenusExample -> MenusExamples()
                        TopComponentsDestination.TooltipExamples -> TooltipExamples()
                        TopComponentsDestination.NavigationDrawerExamples -> NavigationDrawerExamples()
                        TopComponentsDestination.SegmentedButtonExamples -> SegmentedButtonExamples()
                        TopComponentsDestination.SwipeToDismissBoxExamples -> SwipeToDismissBoxExamples()
                        TopComponentsDestination.SearchBarExamples -> SearchBarExamples()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SystemPreview() {
    ZZTComposeTheme {
        SystemPage()
    }
}
