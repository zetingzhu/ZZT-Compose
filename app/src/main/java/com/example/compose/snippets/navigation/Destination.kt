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
    BrushExamples("brushExamples", "Brush Examples"),
    ImageExamples("imageExamples", "Image Examples"),
    CanvasExamples("canvasExamples", "Canvas 简单使用 "),
    AnimationQuickGuideExamples("animationExamples", "Animation Examples"),
    ComponentsExamples("topComponents", "Top Compose Components"),
    ScreenshotExample("screenshotExample", "Screenshot Examples"),
    ShapesExamples("shapesExamples", "Shapes Examples"),
    SharedElementExamples("sharedElement", "Shared elements"),
    PagerExamples("pagerExamples", "Pager examples"),
    PagerExamplesV2("pagerExamplesV2", "Pager 分页显示")
}

// Enum class for compose components navigation screen.
enum class TopComponentsDestination(val route: String, val title: String) {
    FlowLayoutExamples("FlowLayoutExamples", "FlowRow 或 FlowColumn 使用"),
    CustomLayoutExamples("customLayoutExamples", "自定义布局"),
    ButtonExamples("buttonExamples", "Button"),
    CardExamples("cardExamples", "Card"),
    CheckboxExamples("checkboxExamples", "Checkbox"),
    ChipExamples("chipExamples", "Chip"),
    DatePickerExamples("datePickerExamples", "Date Pickers"),
    DialogExamples("dialogExamples", "Dialog"),
    DividerExamples("dividerExamples", "Dividers"),
    FloatingActionButtonExamples("floatingActionButtonExamples", "Floating Action Button"),
    LazyListExamples("lazyListExamples", "列表，网格"),
    LazyListExamplesV2("lazyListExamplesV2", "列表，网格 V2"),
    MenusExample("menusExamples", "Menus"),
    DrawerExample("drawerExample", "抽屉式导航栏使用"),
    NavigationDrawerExamples("navigationDrawerExamples", "Navigation drawer"),
    ProgressIndicatorExamples("progressIndicatorExamples", "Progress Indicators"),

    SwitchExamples("switchExamples", "Switch"),
    SliderExamples("sliderExamples", "Slider"),
    ScaffoldExample("scaffoldExample", "Scaffold"),
    AppBarExamples("appBarExamples", "App bars"),
    BadgeExamples("badgeExamples", "Badges"),
    PartialBottomSheet("partialBottomSheets", "Partial Bottom Sheet"),
    TimePickerExamples("timePickerExamples", "Time Pickers"),
    CarouselExamples("carouselExamples", "Carousel"),
    TooltipExamples("tooltipExamples", "Tooltips"),
    SegmentedButtonExamples("segmentedButtonExamples", "Segmented button"),
    SwipeToDismissBoxExamples("swipeToDismissBoxExamples", "Swipe to dismiss box examples"),
    SearchBarExamples("searchBarExamples", "Search bar")
}
