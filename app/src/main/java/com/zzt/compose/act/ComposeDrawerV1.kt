package com.zzt.compose.act

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.material3.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class ComposeDrawerV1 : ComponentActivity() {
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ComposeDrawerV1::class.java)
            context.startActivity(starter)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            createDrawerV1()
//            createDrawerV2()
//            createDrawerV3()
//            createDrawerV4()
//            createDrawerV5()
//            createDrawerV6()
//            createDrawerV7()
        }
    }

//    @OptIn(ExperimentalMaterial3Api::class)
//    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//    @Preview
//    @Composable
//    fun createDrawerV1() {
//        Box {
//            Scaffold(
//                drawerContent = {
//                    Text("Drawer title", modifier = Modifier.padding(16.dp))
//                    Divider()
//                    // Drawer items
//                },
//                // Defaults to true
//                drawerGesturesEnabled = false
//            ) {
//                // Screen content
//                Text("Hello World")
//            }
//        }
//    }
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//    @Preview
//    @Composable
//    fun createDrawerV2() {
//        val scaffoldState = rememberScaffoldState()
//        val scope = rememberCoroutineScope()
//        Scaffold(
//            topBar = {
//                TopAppBar { /* Top app bar content */ }
//            },
//            // Defaults to false
//            isFloatingActionButtonDocked = true,
//            bottomBar = {
//                BottomAppBar { /* Bottom app bar content */ }
//            },
//            scaffoldState = scaffoldState,
//            drawerContent = {
//                // Drawer content
//                Text("Drawer v2", modifier = Modifier.padding(16.dp))
//                Divider()
//            },
//            floatingActionButton = {
//                ExtendedFloatingActionButton(
//                    text = { Text("Open or close drawer") },
//                    onClick = {
//                        scope.launch {
//                            scaffoldState.drawerState.apply {
//                                if (isClosed) {
//                                    open()
//                                } else {
//                                    close()
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
//
//    @Preview
//    @Composable
//    fun createDrawerV3() {
//        val drawerState = rememberDrawerState(DrawerValue.Closed)
//        ModalDrawer(
//            drawerState = drawerState,
//            drawerContent = {
//                // Drawer content
//                Text("Drawer v3", modifier = Modifier.padding(16.dp))
//                Divider()
//            }
//        ) {
//            // Screen content
//            Text("Hello World")
//        }
//    }
//
//    @OptIn(ExperimentalMaterialApi::class)
//    @Preview
//    @Composable
//    fun createDrawerV4() {
//        val drawerState = rememberBottomDrawerState(BottomDrawerValue.Closed)
//        BottomDrawer(
//            drawerState = drawerState,
//            drawerContent = {
//                // Drawer content
//                Text("Drawer v4", modifier = Modifier.padding(16.dp))
//                Divider()
//                Text("Drawer 內容", modifier = Modifier.padding(16.dp))
//            }
//        ) {
//            // Screen content
//            Text("Hello World")
//        }
//    }
//
//    @OptIn(ExperimentalMaterialApi::class)
//    @Preview
//    @Composable
//    fun createDrawerV5() {
//        BottomSheetScaffold(
//            topBar = {
//                TopAppBar { /* Top app bar content */ }
//            },
//            sheetContent = {
//                // Sheet content
//                Text("Hello World")
//            },
//            // Defaults to BottomSheetScaffoldDefaults.SheetPeekHeight
//            sheetPeekHeight = 128.dp,
//            // Defaults to true
//            sheetGesturesEnabled = false,
//        ) {
//            // Screen content
//            Text("Hello World")
//        }
//    }
//
//    @OptIn(ExperimentalMaterialApi::class)
//    @Preview
//    @Composable
//    fun createDrawerV6() {
//        val scaffoldState = rememberBottomSheetScaffoldState()
//        val scope = rememberCoroutineScope()
//        BottomSheetScaffold(
//            scaffoldState = scaffoldState,
//            sheetContent = {
//                // Sheet content
//                Text("Hello World")
//            },
//            floatingActionButton = {
//                ExtendedFloatingActionButton(
//                    text = { Text("Expand or collapse sheet") },
//                    onClick = {
//                        scope.launch {
//                            scaffoldState.bottomSheetState.apply {
//                                if (isCollapsed) expand() else collapse()
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
//
//    @OptIn(ExperimentalMaterialApi::class)
//    @Preview
//    @Composable
//    fun createDrawerV7() {
//        val scaffoldState = rememberBackdropScaffoldState(
//            BackdropValue.Concealed
//        )
//        val scope = rememberCoroutineScope()
//        BackdropScaffold(
//            scaffoldState = scaffoldState,
//            appBar = {
//                TopAppBar(
//                    title = { Text("Backdrop") },
//                    navigationIcon = {
//                        if (scaffoldState.isConcealed) {
//                            IconButton(
//                                onClick = {
//                                    scope.launch { scaffoldState.reveal() }
//                                }
//                            ) {
//                                Icon(
//                                    Icons.Default.Menu,
//                                    contentDescription = "Menu"
//                                )
//                            }
//                        } else {
//                            IconButton(
//                                onClick = {
//                                    scope.launch { scaffoldState.conceal() }
//                                }
//                            ) {
//                                Icon(
//                                    Icons.Default.Close,
//                                    contentDescription = "Close"
//                                )
//                            }
//                        }
//                    },
//                    elevation = 0.dp,
//                    backgroundColor = Color.Transparent
//                )
//            },
//            backLayerContent = {
//                // Back layer content
//                Text(
//                    text = "Hello World",
//                    modifier = Modifier.height(100.dp)
//                )
//            },
//            frontLayerContent = {
//                // Front layer content
//                Text("Hello World")
//            }
//        )
//    }
}