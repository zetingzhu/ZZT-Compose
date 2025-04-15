package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp


class ComposeLayoutV4 : ComponentActivity() {

    companion object {
        val TAG = ComposeLayoutV4::class.java.simpleName
        fun start(context: Context) {
            val starter = Intent(context, ComposeLayoutV4::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stringIndex = intent.getStringExtra("index")
        Log.d(TAG, "====:$stringIndex")

        setContent {
            LayoutsCodelabPreview()
        }
    }

    val topics = listOf(
        "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
        "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
        "Religion", "Social sciences", "Technology", "TV", "Writing"
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LayoutsCodelab() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "LayoutsCodelab")
                    },
                    actions = {
                        IconButton(onClick = { /* doSomething() */ }) {
                            Icon(Icons.Filled.Favorite, contentDescription = null)
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column() {
                BodyContent(Modifier.padding(innerPadding))
                BodyContent2(Modifier.padding(innerPadding))
            }
        }
    }

    /**
     * 在这种情况下，可滚动的 Row 和 padding 最初具有的约束条件将被强制转换为 size 约束条件，以测量子元素。因此，对于最小和最大 width 以及 height，StaggeredGrid 将被约束为 200 dp。StaggeredGrid 大小为 200x200 dp，而随着从右向左修改大小，padding 修饰符会将大小增大为 (200+16+16)x(200+16+16)=232x232，这也就是 Row 的最终大小。
     */
    @Composable
    fun BodyContent(modifier: Modifier = Modifier) {
        Row(modifier = modifier
            .background(color = Color.LightGray)
            .padding(16.dp)
            .size(200.dp)
            .horizontalScroll(rememberScrollState()),
            content = {
                StaggeredGrid {
                    for (topic in topics) {
                        Chip(modifier = Modifier.padding(8.dp), text = topic)
                    }
                }
            })
    }

    /**
     * 由于约束条件在链中是从左到右传播的，因此对要测量的 Row 的内容采用的约束条件为：width 和 height 的最大值和最小值都为 (200-16-16)=168 dp。这意味着，StaggeredGrid 的大小正好为 168x168 dp。因此，在 modifySize 链从右向左运行后，可滚动的 Row 的最终大小为 200x200 dp。
     */
    @Composable
    fun BodyContent2(modifier: Modifier = Modifier) {
        Row(modifier = modifier
            .background(color = Color.LightGray)
            .size(200.dp)
            .padding(16.dp)
            .horizontalScroll(rememberScrollState()),
            content = {
                StaggeredGrid {
                    for (topic in topics) {
                        Chip(modifier = Modifier.padding(8.dp), text = topic)
                    }
                }
            })
    }


    @Composable
    fun StaggeredGrid(
        modifier: Modifier = Modifier,
        rows: Int = 3,
        content: @Composable () -> Unit
    ) {
        Layout(
            modifier = modifier,
            content = content
        ) { measurables, constraints ->

            // Keep track of the width of each row
            val rowWidths = IntArray(rows) { 0 }

            // Keep track of the max height of each row
            val rowHeights = IntArray(rows) { 0 }

            // Don't constrain child views further, measure them with given constraints
            // List of measured children
            val placeables = measurables.mapIndexed { index, measurable ->
                // Measure each child
                val placeable = measurable.measure(constraints)

                // Track the width and max height of each row
                val row = index % rows
                rowWidths[row] += placeable.width
                rowHeights[row] = Math.max(rowHeights[row], placeable.height)

                placeable
            }

            // Grid's width is the widest row
            val width = rowWidths.maxOrNull()
                ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth))
                ?: constraints.minWidth

            // Grid's height is the sum of the tallest element of each row
            // coerced to the height constraints
            val height = rowHeights.sumOf { it }
                .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

            // Y of each row, based on the height accumulation of previous rows
            val rowY = IntArray(rows) { 0 }
            for (i in 1 until rows) {
                rowY[i] = rowY[i - 1] + rowHeights[i - 1]
            }

            // Set the size of the parent layout
            layout(width, height) {
                // x co-ord we have placed up to, per row
                val rowX = IntArray(rows) { 0 }

                placeables.forEachIndexed { index, placeable ->
                    val row = index % rows
                    placeable.placeRelative(
                        x = rowX[row],
                        y = rowY[row]
                    )
                    rowX[row] += placeable.width
                }
            }
        }
    }

    @Composable
    fun Chip(modifier: Modifier = Modifier, text: String) {
        Card(
            modifier = modifier,
            border = BorderStroke(color = Color.Black, width = Dp.Hairline),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(16.dp, 16.dp)
                        .background(color = MaterialTheme.colorScheme.secondary)
                )
                Spacer(Modifier.width(4.dp))
                Text(text = text)
            }
        }
    }

    @Preview
    @Composable
    fun ChipPreview() {
        Chip(text = "Hi there")
    }

    @Preview
    @Composable
    fun LayoutsCodelabPreview() {
        LayoutsCodelab()
    }
}