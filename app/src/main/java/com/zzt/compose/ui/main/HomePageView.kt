package com.zzt.compose.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.zzt.adapter.StartActivityRecyclerAdapter
import com.zzt.compose.act.ActivityCompostMain
import com.zzt.compose.act.ComposeActText
import com.zzt.compose.act.ComposeActV2
import com.zzt.compose.act.ComposeAlignmentLineV1
import com.zzt.compose.act.ComposeConstraintV1
import com.zzt.compose.act.ComposeConstraintV2
import com.zzt.compose.act.ComposeDrawerV1
import com.zzt.compose.act.ComposeLayoutV1
import com.zzt.compose.act.ComposeLayoutV2
import com.zzt.compose.act.ComposeLayoutV3
import com.zzt.compose.act.ComposeLayoutV4
import com.zzt.compose.act.ComposeListV1
import com.zzt.compose.act.ComposeListV2
import com.zzt.compose.act.ComposeNavV1
import com.zzt.compose.act.ComposeScaffoldV1
import com.zzt.compose.act.ComposeScaffoldV2
import com.zzt.compose.theme.ZZTComposeTheme
import com.zzt.entity.StartActivityDao

/**
 * @author: zeting
 * @date: 2025/4/14
 *
 */

@Composable
fun MainPage(toSettingPage: () -> Unit) {
    val context = LocalContext.current

    val contentPadding = WindowInsets.systemBars.add(
        WindowInsets(
            left = 16.dp, top = 0.dp, right = 16.dp, bottom = 0.dp
        )
    ).asPaddingValues()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {


        item {
            MessageRow(message = "Compost 布局基本知识", onClick = {
                ActivityCompostMain.start(context)
            })
        }
        item {
            MessageRow(message = "Compost text  这个是文本的使用和各种api", onClick = {
                ComposeActText.start(context)
            })
        }
        item {
            MessageRow(message = "Compost Button", onClick = { ComposeActV2.start(context) })
        }
        item {
            MessageRow(
                message = "Compost Scaffold （没用）", onClick = { ComposeScaffoldV1.start(context) })
        }
        item {
            MessageRow(
                message = "Compost Scaffold message （没用）",
                onClick = { ComposeScaffoldV2.start(context) })
        }
        item {
            MessageRow(
                message = "Compost Drawer（没用）", onClick = { ComposeDrawerV1.start(context) })
        }
        item {
            MessageRow(message = "Compost Layout   这玩意完全没看明白", onClick = {
                ComposeLayoutV1.start(context)
            })
        }
        item {
            MessageRow(message = "Compost Layout2   自定义布局", onClick = {
                ComposeLayoutV2.start(context)
            })
        }
        item {
            MessageRow(message = "Compost Layout3  自定义网格列表", onClick = {
                ComposeLayoutV3.start(context)
            })
        }
        item {
            MessageRow(message = "Compost Layout4   自定义布局了解修饰符", onClick = {
                ComposeLayoutV4.start(context)
            })
        }
        item {
            MessageRow(
                message = "AlignmentLine   自定义对齐线", onClick = {
                    ComposeAlignmentLineV1.start(context)
                })
        }
        item { MessageRow(message = "列表1", onClick = { ComposeListV1.start(context) }) }
        item { MessageRow(message = "列表2 ", onClick = { ComposeListV2.start(context) }) }
        item {
            MessageRow(
                message = "ConstraintLayout ", onClick = { ComposeConstraintV1.start(context) })
        }
        item {
            MessageRow(
                message = "ConstraintLayout 2 ", onClick = { ComposeConstraintV2.start(context) })
        }
        item {
            MessageRow(
                message = "Nav v1   底部导航栏", onClick = { ComposeNavV1.start(context) })
        }
        item {
            MessageRow(
                message = "HorizontalPager 水平滚动",
                onClick = { })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageRow(
    message: String, onClick: () -> Unit
) {
    Card(
        modifier = Modifier.padding(0.dp)/*.background(Color.Blue)*/, onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(8.dp)/*.background(Color.Yellow)*/,
            verticalArrangement = Arrangement.Center
        ) {
            Text(message)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    ZZTComposeTheme {
        MainPage() {}
    }
}
