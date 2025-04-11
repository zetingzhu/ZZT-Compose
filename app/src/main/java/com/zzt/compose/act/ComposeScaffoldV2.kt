package com.zzt.compose.act

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

class ComposeScaffoldV2 : ComponentActivity() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ComposeScaffoldV2::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            createMessageV1()
//            createMessageV2()
        }
    }

//    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//    @Preview
//    @Composable
//    fun createMessageV1() {
//        val scaffoldState = rememberScaffoldState()
//        val scope = rememberCoroutineScope()
//        Scaffold(
//            scaffoldState = scaffoldState,
//            floatingActionButton = {
//                ExtendedFloatingActionButton(
//                    text = { Text("Show snackbar") },
//                    onClick = {
//                        scope.launch {
//                            scaffoldState.snackbarHostState.showSnackbar("Snackbar")
//                        }
//                    }
//                )
//            }
//        ) {
//            // Screen content
//            Text("Hello World")
//        }
//    }

//    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//    @Preview
//    @Composable
//    fun createMessageV2() {
//        val scaffoldState = rememberScaffoldState()
//        val scope = rememberCoroutineScope()
//        Scaffold(
//            scaffoldState = scaffoldState,
//            floatingActionButton = {
//                ExtendedFloatingActionButton(
//                    text = { Text("Show snackbar") },
//                    onClick = {
//                        scope.launch {
//                            val result = scaffoldState.snackbarHostState
//                                .showSnackbar(
//                                    message = "Snackbar",
//                                    actionLabel = "Action",
//                                    // Defaults to SnackbarDuration.Short
//                                    duration = SnackbarDuration.Indefinite
//                                )
//                            when (result) {
//                                SnackbarResult.ActionPerformed -> {
//                                    /* Handle snackbar action performed */
//                                }
//                                SnackbarResult.Dismissed -> {
//                                    /* Handle snackbar dismissed */
//                                }
//                            }
//                        }
//                    }
//                )
//            }
//        ) {
//            // Screen content
//            Text("Hello World")
//        }
//    }
}