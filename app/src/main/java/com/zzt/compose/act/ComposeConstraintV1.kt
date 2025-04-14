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
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet


class ComposeConstraintV1 : ComponentActivity() {

    companion object {
        val TAG = ComposeConstraintV1::class.java.simpleName
        fun start(context: Context) {
            val starter = Intent(context, ComposeConstraintV1::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConstraintLayoutContentPreview()
        }
    }

    @Composable
    fun ConstraintLayoutContent() {
        ConstraintLayout {

            // Create references for the composables to constrain
            val (button, text) = createRefs()
            val (t1) = createRefs()
            Button(
                onClick = { /* Do something */ },
                // Assign reference "button" to the Button composable
                // and constrain it to the top of the ConstraintLayout
                modifier = Modifier.constrainAs(button) {
                    top.linkTo(parent.top, margin = 30.dp)
                }
            ) {
                Text("Button")
            }

            // Assign reference "text" to the Text composable
            // and constrain it to the bottom of the Button composable
            Text("Text", Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp)
                centerHorizontallyTo(parent)
            })

            Text("测试另外一个文本", Modifier.constrainAs(t1) {
                top.linkTo(button.top)
                start.linkTo(button.end, margin = 30.dp)
            })
        }
    }

    @Preview
    @Composable
    fun ConstraintLayoutContentPreview() {
        MaterialTheme {
            Column {
                ConstraintLayoutContent()


                Spacer(Modifier.width(10.dp))


                DecoupledConstraintLayout()
            }

        }
    }

    @Composable
    fun DecoupledConstraintLayout() {
        BoxWithConstraints {
            val constraints = if (maxWidth < maxHeight) {
                decoupledConstraints(margin = 16.dp) // Portrait constraints
            } else {
                decoupledConstraints(margin = 32.dp) // Landscape constraints
            }

            ConstraintLayout(constraints) {
                Button(
                    onClick = { /* Do something */ },
                    modifier = Modifier.layoutId("button")
                ) {
                    Text("Button")
                }

                Text("Text", Modifier.layoutId("text"))
            }
        }
    }

    private fun decoupledConstraints(margin: Dp): ConstraintSet {
        return ConstraintSet {
            val button = createRefFor("button")
            val text = createRefFor("text")

            constrain(button) {
                top.linkTo(parent.top, margin = margin)
            }
            constrain(text) {
                top.linkTo(button.bottom, margin)
            }
        }
    }
}