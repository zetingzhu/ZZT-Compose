package com.zzt.compose.act

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.zzt.compose.entity.MessageObj
import kotlinx.coroutines.launch

/**
 * 对齐线
 */
@OptIn(ExperimentalFoundationApi::class)
class ComposeListV1 : ComponentActivity() {

    companion object {
        val TAG = ComposeListV1::class.java.simpleName
        fun start(context: Context) {
            val starter = Intent(context, ComposeListV1::class.java)
            context.startActivity(starter)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column {
                ImageListButton()
                Row {
                    MessageListV1()
                    MessageListV2()
                    ImageList()

                }
            }
        }
    }

    @Composable
    fun MessageListV1() {
        LazyColumn(
            modifier = Modifier.background(color = Color.Blue),
            //内容边缘添加内边距
            contentPadding = PaddingValues(
                horizontal = 16.dp, vertical = 8.dp
            ),
            // 如需在列表项之间添加间距
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            // Add a single item
            item {
                Text(text = "First item")
            }

            // Add 5 items
            items(100) { index ->
                Text(text = "Item: $index")
            }

            // Add another single item
            item {
                Text(text = "Last item")
            }
        }
    }

    fun getMessageList(): MutableList<MessageObj> {
        var messageList = mutableListOf<MessageObj>()

        for (i in 0..100) {
            messageList.add(MessageObj(i, "title:$i"))
        }

        return messageList;
    }

    @Composable
    fun MessageListV2() {
        MessageList(messages = getMessageList())
    }

    @Composable
    fun MessageList(messages: List<MessageObj>) {
        LazyColumn(
            modifier = Modifier.background(color = Color.Red),
        ) {
            items(
                count = messages.size,
                key = { index -> messages[index].id }
            ) { pos ->
                Row(Modifier.animateItemPlacement(tween(durationMillis = 250))) {
                    Text(text = messages[pos].title)
                }
//                MessageRow(messages.get(pos))
            }
        }
    }


    @Composable
    fun MessageRow(message: MessageObj) {
        Text(text = message.title)
    }

    @Composable
    fun ImageListItem(index: Int) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = rememberImagePainter(
                    data = "https://developer.android.com/images/brand/Android_Robot.png"
                ),
                contentDescription = "Android Logo",
                modifier = Modifier.size(50.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text("Item #$index")
        }
    }

    @Composable
    fun ImageList() {
        // We save the scrolling position with this state
        val scrollState = rememberLazyListState()

        LazyColumn(state = scrollState) {
            items(100) {
                ImageListItem(it)
            }
        }
    }

    @Composable
    fun ImageListButton() {
        val listSize = 100
        val scrollState = rememberLazyListState()
        // We save the coroutine scope where our animated scroll will be executed
        val coroutineScope = rememberCoroutineScope()
        Row {
            Button(onClick = {
                coroutineScope.launch {
                    // 0 is the first item index
                    scrollState.animateScrollToItem(0)
                }
            }) {
                Text("Scroll to the top")
            }

            Button(onClick = {
                coroutineScope.launch {
                    // listSize - 1 is the last index of the list
                    scrollState.animateScrollToItem(listSize - 1)
                }
            }) {
                Text("Scroll to the end")
            }
        }
    }
}