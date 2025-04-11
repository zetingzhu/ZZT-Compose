package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.material3.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class ComposeLayoutV1 : ComponentActivity() {

    companion object {
        val TAG = ComposeLayoutV1::class.java.simpleName
        fun start(context: Context) {
            val starter = Intent(context, ComposeLayoutV1::class.java)
            starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(starter)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stringIndex = intent.getStringExtra("index")
        Log.d(TAG, "====:$stringIndex")

        setContent {
            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .verticalScroll(rememberScrollState())
            ) {
                TextWithPaddingToBaselinePreview()
                TextWithNormalPaddingPreview()
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
        Text(
            "Hi there!",
            Modifier.firstBaselineToTop(32.dp)
        )
    }

    @Preview
    @Composable
    fun TextWithNormalPaddingPreview() {
        Text("Hi there!", Modifier.padding(top = 32.dp))
    }


}