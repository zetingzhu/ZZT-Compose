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
import com.example.compose.snippets.animations.AnimEnterScreen
import com.example.compose.snippets.animations.AnimateBetweenComposableDestinations
import com.example.compose.snippets.animations.AnimationExamplesScreen
import com.example.compose.snippets.animations.GestureAndAnimationSimple
import com.example.compose.snippets.animations.GestureAndAnimationSwipeToDismiss
import com.example.compose.snippets.animations.sharedelement.PlaceholderSizeAnimated_Demo
import com.example.compose.snippets.animations.sharedelement.SharedAsyncImage
import com.example.compose.snippets.animations.sharedelement.SharedElementBasicUsage2
import com.example.compose.snippets.animations.sharedelement.SharedElementBasicUsage3
import com.example.compose.snippets.animations.sharedelement.SharedElementTypicalUseText
import com.example.compose.snippets.animations.sharedelement.SharedElement_PredictiveBack
import com.example.compose.snippets.architecture.ArchitectingExamplesScreen
import com.example.compose.snippets.components.AnimShapeScreen
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
import com.example.compose.snippets.components.AppBarExamples
import com.example.compose.snippets.components.BadgeExamples
import com.example.compose.snippets.components.ButtonExamples
import com.example.compose.snippets.components.CarouselExamples
import com.example.compose.snippets.components.CheckboxExamples
import com.example.compose.snippets.components.ChipExamples
import com.example.compose.snippets.components.ClickScreen
import com.example.compose.snippets.components.DatePickerExamples
import com.example.compose.snippets.components.DialogExamples
import com.example.compose.snippets.components.DividerExamples
import com.example.compose.snippets.components.FloatingActionButtonExamples
import com.example.compose.snippets.components.ImageShapeScreen
import com.example.compose.snippets.components.MenusExamples
import com.example.compose.snippets.components.PartialBottomSheet
import com.example.compose.snippets.components.ProgressIndicatorExamples
import com.example.compose.snippets.components.PullScreen
import com.example.compose.snippets.components.PullToRefreshBasicPreview
import com.example.compose.snippets.components.PullToRefreshCustomIndicatorPreview
import com.example.compose.snippets.components.PullToRefreshCustomStylePreview
import com.example.compose.snippets.components.RadioButtonSingleSelection
import com.example.compose.snippets.components.RadioButtonSingleSelectionPreview
import com.example.compose.snippets.components.ScaffoldExample
import com.example.compose.snippets.components.SearchBarExamples
import com.example.compose.snippets.components.SegmentedButtonExamples
import com.example.compose.snippets.components.SliderExamples
import com.example.compose.snippets.components.SwipeToDismissBoxExamples
import com.example.compose.snippets.components.TimePickerExamples
import com.example.compose.snippets.components.TooltipExamples
import com.example.compose.snippets.graphics.CanvasExamplesScreen
import com.example.compose.snippets.graphics.CreateBitmapFromGraphicsLayer
import com.example.compose.snippets.graphics.DrawModifierExamples
import com.example.compose.snippets.graphics.FadingLazyComments
import com.example.compose.snippets.graphics.ModifierDrawBehind
import com.example.compose.snippets.graphics.ModifierDrawWithContent
import com.example.compose.snippets.graphics.StackedAvatars
import com.example.compose.snippets.graphics.shapeExamples
import com.example.compose.snippets.images.ImageBitmapSnippets
import com.example.compose.snippets.layouts.CustomLayoutExamples
import com.example.compose.snippets.layouts.DrawerExample
import com.example.compose.snippets.layouts.FlowLayoutExamples
import com.example.compose.snippets.layouts.PagerExamples
import com.example.compose.snippets.layouts.PagerExamplesV2
import com.example.compose.snippets.layouts.SnackbarExamples
import com.example.compose.snippets.lists.AnimatedOrderedListScreenPreview
import com.example.compose.snippets.lists.LazyListExamples
import com.example.compose.snippets.modifiers.ModifierPaddingExamplesScreen
import com.example.compose.snippets.navigation.AnimComponents
import com.example.compose.snippets.navigation.ClickComponents
import com.example.compose.snippets.navigation.ImageShapeComponents
import com.example.compose.snippets.navigation.PullToRefreshComponents
import com.example.compose.snippets.navigation.TopComponentsDestination
import com.example.compose.snippets.resources.ResourceExample
import com.example.compose.snippets.touchinput.gestures.ScrollExampleV1
import com.example.compose.snippets.touchinput.pointerinput.ClickExamplesScreen
import com.example.compose.snippets.touchinput.pointerinput.ClickScreenV2
import com.example.compose.snippets.touchinput.pointerinput.ClickScreenV3
import com.example.compose.snippets.touchinput.pointerinput.ClickScreenV4
import com.example.compose.snippets.touchinput.pointerinput.ClickScreenV5

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
                        Destination.ArchitectingExamples -> ArchitectingExamplesScreen()
                        Destination.PaddingExamples -> ModifierPaddingExamplesScreen()
                        Destination.ImageShapeExamples -> ImageShapeScreen {
                            navController.navigate(
                                it.route
                            )
                        }

                        Destination.AnimationQuickGuideExamples -> AnimShapeScreen {
                            navController.navigate(
                                it.route
                            )
                        }

                        Destination.ScreenshotExample -> BitmapFromComposableFullSnippet()
                        Destination.ComponentsExamples -> ComponentsScreen {
                            navController.navigate(
                                it.route
                            )
                        }

                        Destination.SharedElementExamples -> PlaceholderSizeAnimated_Demo()
                        Destination.PagerExamples -> PagerExamples()
                        Destination.PagerExamplesV2 -> PagerExamplesV2()
                        Destination.PullToRefreshExamples -> PullScreen {
                            navController.navigate(
                                it.route
                            )
                        }

                        Destination.ResourceExamples -> ResourceExample()
                        Destination.ClickExamples -> ClickScreen {
                            navController.navigate(
                                it.route
                            )
                        }
                    }
                }
            }

            TopComponentsDestination.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        TopComponentsDestination.FlowLayoutExamples -> FlowLayoutExamples()
                        TopComponentsDestination.CustomLayoutExamples -> CustomLayoutExamples()
                        TopComponentsDestination.ButtonExamples -> ButtonExamples()
                        TopComponentsDestination.CardExamples -> CardExamples()
                        TopComponentsDestination.CheckboxExamples -> CheckboxExamples()
                        TopComponentsDestination.ChipExamples -> ChipExamples()
                        TopComponentsDestination.DatePickerExamples -> DatePickerExamples()
                        TopComponentsDestination.DialogExamples -> DialogExamples()
                        TopComponentsDestination.FloatingActionButtonExamples -> FloatingActionButtonExamples()
                        TopComponentsDestination.LazyListExamples -> LazyListExamples()
                        TopComponentsDestination.LazyListExamplesV2 -> AnimatedOrderedListScreenPreview()
                        TopComponentsDestination.MenusExample -> MenusExamples()
                        TopComponentsDestination.DrawerExample -> DrawerExample()
                        TopComponentsDestination.NavigationDrawerExamples -> NavigationDrawerExamples()
                        TopComponentsDestination.ProgressIndicatorExamples -> ProgressIndicatorExamples()
                        TopComponentsDestination.RadioButtonExamples -> RadioButtonSingleSelectionPreview()
                        TopComponentsDestination.ScaffoldExample -> ScaffoldExample()
                        TopComponentsDestination.SearchBarExamples -> SearchBarExamples()
                        TopComponentsDestination.SliderExamples -> SliderExamples()
                        TopComponentsDestination.SnackbarExamples -> SnackbarExamples()
                        TopComponentsDestination.SwitchExamples -> SwitchExamples()
                        TopComponentsDestination.TimePickerExamples -> TimePickerExamples()
                        TopComponentsDestination.AppBarExamples -> AppBarExamples {
                            navController.popBackStack()
                        }

                        TopComponentsDestination.DividerExamples -> DividerExamples()
                        TopComponentsDestination.SegmentedButtonExamples -> SegmentedButtonExamples()
                        TopComponentsDestination.BadgeExamples -> BadgeExamples()
                        TopComponentsDestination.PartialBottomSheet -> PartialBottomSheet()
                        TopComponentsDestination.CarouselExamples -> CarouselExamples()
                        TopComponentsDestination.TooltipExamples -> TooltipExamples()
                        TopComponentsDestination.SwipeToDismissBoxExamples -> SwipeToDismissBoxExamples()
                    }
                }
            }

            PullToRefreshComponents.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        PullToRefreshComponents.PullToRefreshV1 -> PullToRefreshBasicPreview()
                        PullToRefreshComponents.PullToRefreshV2 -> PullToRefreshCustomStylePreview()
                        PullToRefreshComponents.PullToRefreshV3 -> PullToRefreshCustomIndicatorPreview()
                    }
                }
            }

            ImageShapeComponents.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        ImageShapeComponents.ImageExamples -> ImageExamplesScreen()
                        ImageShapeComponents.ImageBitmapVectorExamples -> ImageBitmapSnippets()
                        ImageShapeComponents.CanvasExamples -> CanvasExamplesScreen()
                        ImageShapeComponents.CanvasDrawExamples -> ModifierDrawWithContent()
                        ImageShapeComponents.ModifierDrawBehind -> DrawModifierExamples()
                        ImageShapeComponents.CreateBitmapFromGraphicsLayer -> CreateBitmapFromGraphicsLayer()
                        ImageShapeComponents.BrushExamples -> BrushExamplesScreen()
                        ImageShapeComponents.FadingLazyComments -> FadingLazyComments()
                        ImageShapeComponents.StackedAvatars -> StackedAvatars()
                        ImageShapeComponents.ShapeExamples -> shapeExamples()
                        ImageShapeComponents.ShapesExamples -> ApplyPolygonAsClipImage()
                    }
                }
            }

            AnimComponents.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        AnimComponents.AnimNormal -> AnimationExamplesScreen()
                        AnimComponents.AnimNav -> AnimateBetweenComposableDestinations()
                        AnimComponents.AnimEnterTransition -> AnimEnterScreen()
                        AnimComponents.AnimGesture -> GestureAndAnimationSimple.Gesture()
                        AnimComponents.AnimGestureV2 -> GestureAndAnimationSwipeToDismiss.GestureV2()
                        AnimComponents.AnimSharV1 -> SharedElementBasicUsage2.SharedElementApp()
                        AnimComponents.AnimSharV2 -> SharedElementBasicUsage3.SharedElementApp()
                        AnimComponents.AnimSharV3 -> SharedAsyncImage()
                        AnimComponents.AnimSharV4 -> SharedElementTypicalUseText()
                        AnimComponents.AnimSharNav -> SharedElement_PredictiveBack()
                    }
                }
            }

            ClickComponents.entries.forEach { destination ->
                composable(destination.route) {
                    when (destination) {
                        ClickComponents.ClickExample -> ClickExamplesScreen()
                        ClickComponents.ClickExampleV2 -> ClickScreenV2()
                        ClickComponents.ClickExampleV3 -> ClickScreenV3()
                        ClickComponents.ClickExampleV4 -> ClickScreenV4()
                        ClickComponents.ClickExampleV5 -> ClickScreenV5()
                        ClickComponents.ScrollExampleV1 -> ScrollExampleV1()
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
