package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class ComposeScaffoldV1 : ComponentActivity() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ComposeScaffoldV1::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            createTitleV1()
//            createTitleV2()
//            createTitleV3()
        }
    }

//    @OptIn(ExperimentalMaterial3Api::class)
//    @Preview
//    @Composable
//    fun createTitleV1() {
//        Scaffold(
//            topBar = {
//                TopAppBar { /* Top app bar content */ }
//            },
//            floatingActionButton = {
//                FloatingActionButton(onClick = { /* ... */ }) {
//                    /* FAB content */
//                }
//            },
//            isFloatingActionButtonDocked = true,
//            bottomBar = {
//                BottomAppBar(
//                    // Defaults to null, that is, No cutout
//                    cutoutShape = MaterialTheme.shapes.small.copy(
//                        CornerSize(percent = 50)
//                    )
//                ) {
//                    /* Bottom app bar content */
//                }
//            }
//        ) { contentPadding ->
//            // Screen content
//            Box(modifier = Modifier.padding(contentPadding)) {
//                /* ... */
//                Text("Hello World")
//            }
//        }
//    }

//    @Preview
//    @Composable
//    fun createTitleV2() {
//        Scaffold(
//            topBar = {
//                TopAppBar { /* Top app bar content */ }
//            },
//            floatingActionButton = {
//                FloatingActionButton(onClick = { /* ... */ }) {
//                    /* FAB content */
//                }
//            },
//            // Defaults to false
//            isFloatingActionButtonDocked = true,
//            bottomBar = {
//                BottomAppBar { /* Bottom app bar content */ }
//            }
//        ) { contentPadding ->
//            // Screen content
//            Box(modifier = Modifier.padding(contentPadding)) {
//                /* ... */
//                Text("Hello World")
//            }
//        }
//    }

//    @Preview
//    @Composable
//    fun createTitleV3() {
//        Scaffold(
//            topBar = {
//                TopAppBar { /* Top app bar content */ }
//            },
//            floatingActionButton = {
//                FloatingActionButton(onClick = { /* ... */ }) {
//                    /* FAB content */
//                }
//            },
//            // Defaults to false
//            isFloatingActionButtonDocked = false,
//            bottomBar = {
//                BottomAppBar { /* Bottom app bar content */ }
//            }
//        ) { contentPadding ->
//            // Screen content
//            Box(modifier = Modifier.padding(contentPadding)) {
//                /* ... */
//                Text("Hello World")
//            }
//        }
//    }
}