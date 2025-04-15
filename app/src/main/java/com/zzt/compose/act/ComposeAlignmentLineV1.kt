package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.dp

/**
 * 对齐线
 */
class ComposeAlignmentLineV1 : ComponentActivity() {

    companion object {
        val TAG = ComposeAlignmentLineV1::class.java.simpleName
        fun start(context: Context) {
            val starter = Intent(context, ComposeAlignmentLineV1::class.java)
            context.startActivity(starter)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

//            TwoTextsPreview()

            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .verticalScroll(rememberScrollState())
            ) {
                TextWithPaddingToBaselinePreview()

                Divider(
                    color = Color.Red,
                    modifier = Modifier
                        .height(10.dp)
                )

                TwoTextsV1(text1 = "Hi", text2 = "there")

                Divider(
                    color = Color.Red,
                    modifier = Modifier
                        .height(10.dp)
                )

                TwoTextsV2(text1 = "222fdadfdf2", text2 = "33fdadf33\n3fdadfdaf333")
            }
        }
    }

    fun Modifier.firstBaselineToTop(
        firstBaselineToTop: Dp
    ) = layout { measurable, constraints ->
        // Measure the composable
        val placeable = measurable.measure(constraints)

        // Check the composable has a first baseline
        check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
        val firstBaseline = placeable[FirstBaseline]

        // Height of the composable with padding - first baseline
        val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
        val height = placeable.height + placeableY
        layout(placeable.width, height) {
            // Where the composable gets placed
            placeable.placeRelative(0, placeableY)
        }
    }

    @Preview
    @Composable
    fun TextWithPaddingToBaselinePreview() {
        MaterialTheme {
            Text(
                text = "Hi there!",
                Modifier
                    .firstBaselineToTop(32.dp)
                    .height(100.dp)
            )
        }
    }

    @Preview
    @Composable
    fun TwoTextsPreview() {
        MaterialTheme {
            Surface {
                TwoTextsV1(text1 = "Hi", text2 = "there")
            }
        }
    }

    @Composable
    fun TwoTextsV1(
        text1: String,
        text2: String,
        modifier: Modifier = Modifier
    ) {
        Row(modifier = modifier.height(100.dp)) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .wrapContentWidth(Alignment.Start),
                text = text1
            )

            Divider(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
                    .wrapContentWidth(Alignment.End),
                text = text2
            )
        }
    }

    @Composable
    fun TwoTextsV2(
        text1: String,
        text2: String,
        modifier: Modifier = Modifier
    ) {
        Row(modifier = modifier.height(IntrinsicSize.Min)) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .wrapContentWidth(Alignment.Start),
                text = text1
            )
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
                    .wrapContentWidth(Alignment.End),
                text = text2
            )
        }
    }
}