/*
 * Copyright 2022 The Android Open Source Project
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

package com.example.compose.snippets.graphics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.snippets.layouts.Box300
import com.example.compose.snippets.layouts.ScaffoldSnackbarDemo
import com.example.compose.snippets.layouts.ScaffoldSnackbarResultDemo
import com.zzt.compose.R
import kotlin.random.Random

/*
* Copyright 2022 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
**/

@Preview
@Composable
fun DrawModifierExamples() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Text("Modifier.drawWithContent：选择绘制顺序")
        Box300() {
            ModifierDrawWithContent()
        }

        Text("Modifier.drawBehind：在可组合项后面绘制")
        Box300() {
            ModifierDrawBehind()
        }

        Text("Modifier.drawWithCache：绘制和缓存绘制对象")
        Box300() {
            ModifierDrawWithCache()
        }

        Text("Modifier.graphicsLayer：缩放")
        Box400() {
            ModifierGraphicsLayerModifierScale()
        }

        Text("Modifier.graphicsLayer：平移")
        Box400() {
            ModifierGraphicsLayerModifierTranslation()
        }

        Text("Modifier.graphicsLayer：旋转")
        Box400() {
            ModifierGraphicsLayerModifierRotation()
        }

        Text("Modifier.graphicsLayer：指定原点旋转")
        Box400() {
            ModifierGraphicsLayerModifierOrigin()
        }

        Text("Modifier.graphicsLayer：裁剪和形状")
        ModifierGraphicsLayerModifierClipShape()

        Text("Modifier.graphicsLayer：alpha 值")
        Box400() {
            ModifierGraphicsLayerAlpha()
        }

        Text("Modifier.graphicsLayer：合成策略")
        Box400() {
            ModifierGraphicsLayerCompositingStrategy()
        }


        Text("Modifier.graphicsLayer：CompositingStrategy.Offscreen 作用")
        Box600() {
            CompositingStrategyExamples()
        }

        Text("Modifier.graphicsLayer：ModulateAlpha")
        CompositingStrategy_ModulateAlpha()


        Text("Modifier.graphicsLayer：自定义绘制修饰符")
        Box400() {
            ModifierGraphicsFlippedUsage()
        }

    }
}

@Composable
fun Box400(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier.height(400.dp), content = content)
}

@Composable
fun Box500(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier.height(500.dp), content = content)
}

@Composable
fun Box600(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier.height(600.dp), content = content)
}

@Composable
fun Box800(modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Box(modifier = modifier.height(800.dp), content = content)
}


@Composable
@Preview
fun ModifierDrawWithContent() {
    // [START android_compose_graphics_modifiers_drawWithContent]
    var pointerOffset by remember {
        mutableStateOf(Offset(0f, 0f))
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput("dragging") {
                detectDragGestures { change, dragAmount ->
                    pointerOffset += dragAmount
                }
            }
            .onSizeChanged {
                pointerOffset = Offset(it.width / 2f, it.height / 2f)
            }
            .drawWithContent {
                drawContent()
                // draws a fully black area with a small keyhole at pointerOffset that’ll show part of the UI.
                drawRect(
                    Brush.radialGradient(
                        listOf(Color.Transparent, Color.Black),
                        center = pointerOffset,
                        radius = 100.dp.toPx(),
                    )
                )
            }
    ) {
        // Your composables here
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.code_bg),
            contentDescription = stringResource(id = R.string.dog_content_description),
            contentScale = ContentScale.Crop,
        )
    }
    // [END android_compose_graphics_modifiers_drawWithContent]
}

@Composable
@Preview
fun ModifierDrawBehind() {
    // [START android_compose_graphics_modifiers_drawBehind]
    Text(
        "Hello Compose!",
        modifier = Modifier
            .drawBehind {
                drawRoundRect(
                    Color(0xFFBBAAEE),
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            }
            .padding(4.dp)
    )
    // [END android_compose_graphics_modifiers_drawBehind]
}

@Composable
@Preview
fun ModifierDrawWithCache() {
    // [START android_compose_graphics_modifiers_drawWithCache]
    Text(
        "Hello Compose!",
        modifier = Modifier
            .drawWithCache {
                val brush = Brush.linearGradient(
                    listOf(
                        Color(0xFF9E82F0),
                        Color(0xFF42A5F5)
                    )
                )
                onDrawBehind {
                    drawRoundRect(
                        brush,
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
            }
    )
    // [END android_compose_graphics_modifiers_drawWithCache]
}

@Composable
@Preview
fun ModifierGraphicsLayerModifierScale() {
    // [START android_compose_graphics_modifiers_graphicsLayer_scale]
    Column {
        var scaleX by remember { mutableFloatStateOf(1f) }
        var scaleY by remember { mutableFloatStateOf(1f) }

        Slider(
            value = scaleX,
            onValueChange = { scaleX = it },
            valueRange = 0f..3f
        )

        Slider(
            value = scaleY,
            onValueChange = { scaleY = it },
            valueRange = 0f..3f
        )

        Image(
            painter = painterResource(id = R.drawable.sunset),
            contentDescription = "Sunset",
            modifier = Modifier
                .graphicsLayer {
                    this.scaleX = scaleX
                    this.scaleY = scaleY
                }
        )
    }
    // [END android_compose_graphics_modifiers_graphicsLayer_scale]
}

@Composable
@Preview
fun ModifierGraphicsLayerModifierTranslation() {
    // [START android_compose_graphics_modifiers_graphicsLayer_translation]
    Column {
        var scaleX by remember { mutableFloatStateOf(1f) }
        var scaleY by remember { mutableFloatStateOf(1f) }

        Slider(
            value = scaleX,
            onValueChange = { scaleX = it },
            valueRange = 0f..100f
        )

        Slider(
            value = scaleY,
            onValueChange = { scaleY = it },
            valueRange = 0f..100f
        )

        Image(
            painter = painterResource(id = R.drawable.sunset),
            contentDescription = "Sunset",
            modifier = Modifier
                .graphicsLayer {
                    this.translationX = scaleX.dp.toPx()
                    this.translationY = scaleY.dp.toPx()
                }
        )
    }
    // [END android_compose_graphics_modifiers_graphicsLayer_translation]
}

@Composable
@Preview
fun ModifierGraphicsLayerModifierRotation() {
    // [START android_compose_graphics_modifiers_graphicsLayer_rotation]
    Column {
        var scaleX by remember { mutableFloatStateOf(1f) }
        var scaleY by remember { mutableFloatStateOf(1f) }
        var scaleZ by remember { mutableFloatStateOf(1f) }

        Slider(
            value = scaleX,
            onValueChange = { scaleX = it },
            valueRange = 0f..360f
        )

        Slider(
            value = scaleY,
            onValueChange = { scaleY = it },
            valueRange = 0f..360f
        )

        Slider(
            value = scaleZ,
            onValueChange = { scaleZ = it },
            valueRange = 0f..360f
        )

        Image(
            painter = painterResource(id = R.drawable.sunset),
            contentDescription = "Sunset",
            modifier = Modifier
                .graphicsLayer {
                    this.rotationX = scaleX
                    this.rotationY = scaleY
                    this.rotationZ = scaleZ
                }
        )
    }
    // [END android_compose_graphics_modifiers_graphicsLayer_rotation]
}

@Preview
@Composable
fun ModifierGraphicsLayerModifierOrigin() {
    // [START android_compose_graphics_modifiers_graphicsLayer_origin]
    Column {
        var scaleX by remember { mutableFloatStateOf(1f) }
        var scaleY by remember { mutableFloatStateOf(1f) }
        var scaleZ by remember { mutableFloatStateOf(1f) }

        Slider(
            value = scaleX,
            onValueChange = { scaleX = it },
            valueRange = 0f..360f
        )

        Slider(
            value = scaleY,
            onValueChange = { scaleY = it },
            valueRange = 0f..360f
        )

        Slider(
            value = scaleZ,
            onValueChange = { scaleZ = it },
            valueRange = 0f..360f
        )
        Image(
            painter = painterResource(id = R.drawable.sunset),
            contentDescription = "Sunset",
            modifier = Modifier
                .graphicsLayer {
                    this.transformOrigin = TransformOrigin(0f, 0f)
                    this.rotationX = scaleX
                    this.rotationY = scaleY
                    this.rotationZ = scaleZ
                }
        )
    }
    // [END android_compose_graphics_modifiers_graphicsLayer_origin]
}

@Preview
@Composable
fun ModifierGraphicsLayerModifierClipShape() {
    // [START android_compose_graphics_modifiers_graphicsLayer_clip_shape]
    Column(
        modifier = Modifier
            .padding(16.dp)
            .height(500.dp)
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    clip = true
                    shape = CircleShape
                }
                .background(Color(0xFFF06292))
        ) {
            Text(
                "Hello Compose",
                style = TextStyle(color = Color.Black, fontSize = 46.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(Color(0xFF4DB6AC))
        )
    }
    // [END android_compose_graphics_modifiers_graphicsLayer_clip_shape]

    Text("translationY 内容被移除原始区域")
    Column(
        modifier = Modifier
            .padding(16.dp)
            .height(500.dp)
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .border(2.dp, Color.Black)
                .graphicsLayer {
                    clip = true
                    shape = CircleShape
                    translationY = 50.dp.toPx()
                }
                .background(Color(0xFFF06292))
        ) {
            Text(
                "Hello Compose",
                style = TextStyle(color = Color.Black, fontSize = 46.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(500.dp))
                .background(Color(0xFF4DB6AC))
        )
    }


    Text("clip(RectangleShape) 内容保留在原始区域")
    // [START android_compose_graphics_modifiers_graphicsLayer_clip_shape_2]
    Column(
        modifier = Modifier
            .padding(16.dp)
            .height(500.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RectangleShape)
                .size(200.dp)
                .border(2.dp, Color.Black)
                .graphicsLayer {
                    clip = true
                    shape = CircleShape
                    translationY = 50.dp.toPx()
                }
                .background(Color(0xFFF06292))
        ) {
            Text(
                "Hello Compose",
                style = TextStyle(color = Color.Black, fontSize = 46.sp),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(500.dp))
                .background(Color(0xFF4DB6AC))
        )
    }
    // [END android_compose_graphics_modifiers_graphicsLayer_clip_shape_2]
}

@Preview
@Composable
fun ModifierGraphicsLayerAlpha() {
    // [START android_compose_graphics_modifiers_graphicsLayer_alpha]
    Column {
        var alpha by remember { mutableFloatStateOf(1f) }

        Slider(
            value = alpha,
            onValueChange = { alpha = it },
            valueRange = 0f..1f
        )


        Image(
            painter = painterResource(id = R.drawable.sunset),
            contentDescription = "clock",
            modifier = Modifier
                .graphicsLayer {
                    this.alpha = alpha
                }
        )
    }
    // [END android_compose_graphics_modifiers_graphicsLayer_alpha]
}

@Preview
@Composable
fun ModifierGraphicsLayerCompositingStrategy() {
    // [START android_compose_graphics_modifiers_graphicsLayer_compositing_strategy]

    Image(
        painter = painterResource(id = R.drawable.dog),
        contentDescription = "Dog",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(120.dp)
            .aspectRatio(1f)
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFFC5E1A5),
                        Color(0xFF80DEEA)
                    )
                )
            )
            .padding(8.dp)
            .graphicsLayer {
                compositingStrategy = CompositingStrategy.Offscreen
            }
            .drawWithCache {
                val path = Path()
                path.addOval(
                    Rect(
                        topLeft = Offset.Zero,
                        bottomRight = Offset(size.width, size.height)
                    )
                )
                onDrawWithContent {
                    clipPath(path) {
                        // this draws the actual image - if you don't call drawContent, it wont
                        // render anything
                        this@onDrawWithContent.drawContent()
                    }
                    val dotSize = size.width / 8f
                    // Clip a white border for the content
                    drawCircle(
                        Color.Black,
                        radius = dotSize,
                        center = Offset(
                            x = size.width - dotSize,
                            y = size.height - dotSize
                        ),
                        blendMode = BlendMode.Clear
                    )
                    // draw the red circle indication
                    drawCircle(
                        Color(0xFFEF5350), radius = dotSize * 0.8f,
                        center = Offset(
                            x = size.width - dotSize,
                            y = size.height - dotSize
                        )
                    )
                }
            }
    )
    // [END android_compose_graphics_modifiers_graphicsLayer_compositing_strategy]
}

@Preview
// [START android_compose_graphics_modifier_compositing_strategy_differences]
@Composable
fun CompositingStrategyExamples() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        // Does not clip content even with a graphics layer usage here. By default, graphicsLayer
        // does not allocate + rasterize content into a separate layer but instead is used
        // for isolation. That is draw invalidations made outside of this graphicsLayer will not
        // re-record the drawing instructions in this composable as they have not changed
        Canvas(
            modifier = Modifier
                .graphicsLayer()
                .size(100.dp) // Note size of 100 dp here
                .border(2.dp, color = Color.Blue)
        ) {
            // ... and drawing a size of 200 dp here outside the bounds
            drawRect(color = Color.Magenta, size = Size(200.dp.toPx(), 200.dp.toPx()))
        }

        Spacer(modifier = Modifier.size(150.dp))

        /* Clips content as alpha usage here creates an offscreen buffer to rasterize content
        into first then draws to the original destination */
        Canvas(
            modifier = Modifier
                // force to an offscreen buffer
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
                .size(100.dp) // Note size of 100 dp here
                .border(2.dp, color = Color.Blue)
        ) {
            /* ... and drawing a size of 200 dp. However, because of the CompositingStrategy.Offscreen usage above, the
            content gets clipped */
            drawRect(color = Color.Red, size = Size(200.dp.toPx(), 200.dp.toPx()))
        }
    }
}
// [END android_compose_graphics_modifier_compositing_strategy_differences]

// [START android_compose_graphics_modifier_compositing_strategy_modulate_alpha]
@Preview
@Composable
fun CompositingStrategy_ModulateAlpha() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        // Base drawing, no alpha applied
        Canvas(
            modifier = Modifier.size(200.dp)
        ) {
            drawSquares()
        }

        Spacer(modifier = Modifier.size(36.dp))

        // Alpha 0.5f applied to whole composable
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Auto
                    alpha = 0.5f
                }
        ) {
            drawSquares()
        }
        Spacer(modifier = Modifier.size(36.dp))

        // 0.75f alpha applied to each draw call when using ModulateAlpha
        Canvas(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.ModulateAlpha
                    alpha = 0.5f
                }
        ) {
            drawSquares()
        }

        Canvas(
            modifier = Modifier
                .size(200.dp)
                .graphicsLayer {
                    compositingStrategy = CompositingStrategy.Offscreen
                    alpha = 0.5f
                }
        ) {
            drawSquares()
        }
    }
}

private fun DrawScope.drawSquares() {

    val size = Size(100.dp.toPx(), 100.dp.toPx())
    drawRect(color = Red, size = size)
    drawRect(
        color = Purple, size = size,
        topLeft = Offset(size.width / 4f, size.height / 4f)
    )
    drawRect(
        color = Yellow, size = size,
        topLeft = Offset(size.width / 4f * 2f, size.height / 4f * 2f)
    )
}

val Purple = Color(0xFF7E57C2)
val Yellow = Color(0xFFFFCA28)
val Red = Color(0xFFEF5350)
// [END android_compose_graphics_modifier_compositing_strategy_modulate_alpha]

// [START android_compose_graphics_modifier_flipped]
class FlippedModifier : DrawModifier {
    override fun ContentDrawScope.draw() {
        scale(1f, -1f) {
            this@draw.drawContent()
        }
    }
}

fun Modifier.flipped() = this.then(FlippedModifier())
// [END android_compose_graphics_modifier_flipped]

@Preview
@Composable
fun ModifierGraphicsFlippedUsage() {
    // [START android_compose_graphics_modifier_flipped_usage]
    Text(
        "Hello Compose!",
        modifier = Modifier
            .flipped()
    )
    // [END android_compose_graphics_modifier_flipped_usage]
}

// [START android_compose_graphics_faded_edge_example]
@Composable
fun FadedEdgeBox(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
            .drawWithContent {
                drawContent()
                drawRect(
                    brush = Brush.verticalGradient(
                        listOf(Color.Black, Color.Transparent)
                    ),
                    blendMode = BlendMode.DstIn
                )
            }
    ) {
        content()
    }
}

// [END android_compose_graphics_faded_edge_example]
@Preview
@Composable
fun FadingLazyComments() {
    FadedEdgeBox(
        modifier = Modifier
            .padding(32.dp)
            .height(300.dp)
            .fillMaxWidth()
    ) {
        LazyColumn {
            items(listComments, key = { it.key }) {
                ListCommentItem(it)
            }
            item {
                Spacer(Modifier.height(100.dp))
            }
        }
    }
}

@Composable
private fun ListCommentItem(it: Comment) {
    Row(modifier = Modifier.padding(bottom = 8.dp)) {
        val strokeWidthPx = with(LocalDensity.current) {
            2.dp.toPx()
        }
        Avatar(strokeWidth = strokeWidthPx, modifier = Modifier.size(48.dp)) {
            Image(
                painter = painterResource(id = it.avatar),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Spacer(Modifier.width(6.dp))
        Text(
            it.text,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

data class Comment(
    val avatar: Int,
    val text: String,
    val key: Int = Random.nextInt()
)

val listComments = listOf(
    Comment(R.drawable.dog, "Woof 🐶"),
    Comment(R.drawable.froyo, "I love ice cream..."),
    Comment(R.drawable.donut, "Mmmm delicious"),
    Comment(R.drawable.cupcake, "I love cupcakes"),
    Comment(R.drawable.gingerbread, "🍪🍪❤️"),
    Comment(R.drawable.eclair, "Where do I get the recipe?"),
    Comment(R.drawable.froyo, "🍦The ice cream is BEST"),
)

// [START android_compose_graphics_stacked_clipped_avatars]
@Composable
fun Avatar(
    strokeWidth: Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val stroke = remember(strokeWidth) {
        Stroke(width = strokeWidth)
    }
    Box(
        modifier = modifier
            .drawWithContent {
                drawContent()
                drawCircle(
                    Color.Black,
                    size.minDimension / 2,
                    size.center,
                    style = stroke,
                    blendMode = BlendMode.Clear
                )
            }
            .clip(CircleShape)
    ) {
        content()
    }
}

@Preview
@Composable
fun StackedAvatars() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color.Magenta.copy(alpha = 0.5f),
                        Color.Blue.copy(alpha = 0.5f)
                    )
                )
            )
    ) {
        val size = 80.dp
        val strokeWidth = 2.dp
        val strokeWidthPx = with(LocalDensity.current) {
            strokeWidth.toPx()
        }
        val sizeModifier = Modifier.size(size)
        val avatars = listOf(
            R.drawable.cupcake,
            R.drawable.donut,
            R.drawable.eclair,
            R.drawable.froyo,
            R.drawable.gingerbread,
            R.drawable.dog
        )
        val width = ((size / 2) + strokeWidth * 2) * (avatars.size + 1)
        Box(
            modifier = Modifier
                .size(width, size)
                .graphicsLayer {
                    // Use an offscreen buffer as underdraw protection when
                    // using blendmodes that clear destination pixels
                    compositingStrategy = CompositingStrategy.Offscreen
                }
                .align(Alignment.Center),
        ) {
            var offset = 0.dp
            for (avatar in avatars) {
                Avatar(
                    strokeWidth = strokeWidthPx,
                    modifier = sizeModifier.offset(offset)
                ) {
                    Image(
                        painter = painterResource(id = avatar),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }
                offset += size / 2
            }
        }
    }
}
// [END android_compose_graphics_stacked_clipped_avatars]
