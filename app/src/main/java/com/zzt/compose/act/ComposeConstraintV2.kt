package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.compose.material3.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension


class ComposeConstraintV2 : ComponentActivity() {

    companion object {
        val TAG = ComposeConstraintV2::class.java.simpleName
        fun start(context: Context) {
            val starter = Intent(context, ComposeConstraintV2::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LargeConstraintLayoutPreview()
        }
    }

    @Composable
    fun LargeConstraintLayout() {
        ConstraintLayout {
            val text = createRef()

            val guideline = createGuidelineFromStart(fraction = 0.5f)
            Text(
                "This is a very very very very1 very1 very1 very long text",
                Modifier.constrainAs(text) {
                    linkTo(start = guideline, end = parent.end)
                }
            )
        }
    }

    @Composable
    fun LargeConstraintLayout2() {
        ConstraintLayout {
            val text = createRef()

            val guideline = createGuidelineFromStart(0.5f)
            Text(
                "This is a very very very very2 very2 very2 very long text",
                Modifier.constrainAs(text) {
                    linkTo(guideline, parent.end)
                    width = Dimension.preferredWrapContent
                }
            )
        }
    }

    @Preview
    @Composable
    fun LargeConstraintLayoutPreview() {
        MaterialTheme {
            Column() {
                LargeConstraintLayout()

                LargeConstraintLayout2()
            }
        }
    }
}