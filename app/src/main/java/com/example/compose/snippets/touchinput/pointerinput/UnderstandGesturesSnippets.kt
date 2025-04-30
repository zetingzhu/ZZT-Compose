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

@file:Suppress("ControlFlowWithEmptyBody", "UNUSED_VARIABLE", "unused")

package com.example.compose.snippets.touchinput.pointerinput

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.snippets.layouts.Box300
import com.example.compose.snippets.touchinput.keyboardinput.showToast
import com.zzt.compose.MyApplication


@Preview
@Composable
fun ClickExamplesScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, end = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Box300 {
            Layering()
        }

        Text("使用 pointerInput 修饰符向任意可组合项添加自定义手势")
        Box300 {
            LogPointerEvents()
        }


        Text("检测完整手势-单个检测")
        Box300 {
            IncorrectDoubleGestureDetector()
        }

        Text("检测完整手势-组合检测")
        Box300 {
            CorrectDoubleGestureDetector()
        }

        Text("每个手势处理事件")
        Box300 {
            SimpleClickable() {
                showToast("点击了")
            }
        }

        Text("事件消耗")
        Box300 {
            Consuming()
        }

    }
}

fun showToast(message: String) {
    val toast = Toast.makeText(MyApplication.context, message, Toast.LENGTH_SHORT)
    toast.show()
}

@Preview
@Composable
private fun Layering() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        // [START android_compose_touchinput_pointerinput_layering]
        // Talkback: "Click me!, Button, double tap to activate"
        Button(onClick = { /* TODO */ }) { Text("Click me!") }
        // Talkback: "Click me!, double tap to activate"
        Box(Modifier.clickable { /* TODO */ }) { Text("Click me!") }
        // [END android_compose_touchinput_pointerinput_layering]
    }
}

@Preview
// [START android_compose_touchinput_pointerinput_raw_event_listening]
@Composable
private fun LogPointerEvents(filter: PointerEventType? = null) {
    var log by remember { mutableStateOf("") }
    Column {
        Text(log)
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Red)
                .pointerInput(filter) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            // handle pointer event
                            if (filter == null || event.type == filter) {
                                log = "${event.type}, ${event.changes.first().position}"
                            }
                        }
                    }
                }
        )
    }
}
// [END android_compose_touchinput_pointerinput_raw_event_listening]

@Composable
private fun Consuming() {
    Box(
        // [START android_compose_touchinput_pointerinput_consuming]
        Modifier.pointerInput(Unit) {

            awaitEachGesture {
                while (true) {
                    val event = awaitPointerEvent()
                    // consume all changes
                    event.changes.forEach { it.consume() }
                }
            }
        }
        // [END android_compose_touchinput_pointerinput_consuming]
    )
}

@Composable
private fun CheckConsumption() {
    Box(
        // [START android_compose_touchinput_pointerinput_check_consumption]
        Modifier.pointerInput(Unit) {
            awaitEachGesture {
                while (true) {
                    val event = awaitPointerEvent()
                    if (event.changes.any { it.isConsumed }) {
                        // A pointer is consumed by another gesture handler
                    } else {
                        // Handle unconsumed event
                    }
                }
            }
        }
        // [END android_compose_touchinput_pointerinput_check_consumption]
    )
}

@Preview
@Composable
private fun IncorrectDoubleGestureDetector() {
    // [START android_compose_touchinput_pointerinput_incorrect_double_gesture_detector]
    var log by remember { mutableStateOf("") }
    Column {
        Text(log)
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Red)
                .pointerInput(Unit) {
                    detectTapGestures { log = "Tap!" }
                    // Never reached
                    detectDragGestures { _, _ -> log = "Dragging" }
                }
        )
    }
    // [END android_compose_touchinput_pointerinput_incorrect_double_gesture_detector]
}

@Preview
@Composable
private fun CorrectDoubleGestureDetector() {
    // [START android_compose_touchinput_pointerinput_correct_double_gesture_detector]
    var log by remember { mutableStateOf("") }
    Column {
        Text(log)
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Red)
                .pointerInput(Unit) {
                    detectTapGestures { log = "Tap!" }
                }
                .pointerInput(Unit) {
                    // These drag events will correctly be triggered
                    detectDragGestures { _, _ -> log = "Dragging" }
                }
        )
    }
    // [END android_compose_touchinput_pointerinput_correct_double_gesture_detector]
}

// [START android_compose_touchinput_pointerinput_simple_clickable]
@Composable
private fun SimpleClickable(onClick: () -> Unit) {
    Box(
        Modifier
            .size(100.dp)
            .pointerInput(onClick) {
                awaitEachGesture {
                    awaitFirstDown().also { it.consume() }
                    val up = waitForUpOrCancellation()
                    if (up != null) {
                        up.consume()
                        onClick()
                    }
                }
            }
    )
}
// [END android_compose_touchinput_pointerinput_simple_clickable]

@Composable
private fun ThreePasses() {
    Box(
        // [START android_compose_touchinput_pointerinput_three_passes]
        Modifier.pointerInput(Unit) {
            awaitPointerEventScope {
                val eventOnInitialPass = awaitPointerEvent(PointerEventPass.Initial)
                val eventOnMainPass = awaitPointerEvent(PointerEventPass.Main) // default
                val eventOnFinalPass = awaitPointerEvent(PointerEventPass.Final)
            }
        }
        // [END android_compose_touchinput_pointerinput_three_passes]
    )
}

@Preview
@Composable
fun Demo() {
    var log by remember { mutableStateOf("") }
    var log2 by remember { mutableStateOf("") }
    Column(
        Modifier
            .background(Color.White)
            .size(300.dp)
    ) {
        Text(log)
        Text(log2)
        Box {
            Box(
                Modifier
                    .size(100.dp)
                    .background(Color.Red)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                log2 = "RED: ${event.type}, ${event.changes.first().position}"
                            }
                        }
                    }
            )
            Box(
                Modifier
                    .size(100.dp)
                    .offset(x = 50.dp, y = 50.dp)
                    .background(Color.Blue)
                    .pointerInput(Unit) {
                        awaitPointerEventScope {
                            while (true) {
                                val event = awaitPointerEvent()
                                log = "BLUE: ${event.type}, ${event.changes.first().position}"
                            }
                        }
                    }
            )
        }
    }
}
