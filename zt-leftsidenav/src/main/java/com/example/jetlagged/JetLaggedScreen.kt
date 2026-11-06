package com.example.jetlagged

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetlagged.backgrounds.movingStripesBackground
import com.example.jetlagged.sleep.JetLaggedHeader
import com.trade.zt_leftsidenav.SystemDrawerActivity
import com.trade.zt_leftsidenav.theme.JetLaggedTheme

@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun JetLaggedScreen(
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier.movingStripesBackground(
                stripeColor = JetLaggedTheme.extraColors.header,
                backgroundColor = MaterialTheme.colorScheme.background,
            ),
        ) {
            JetLaggedHeader(
                modifier = Modifier.fillMaxWidth(),
                onDrawerClicked = onDrawerClicked,
            )
        }

        val insets = WindowInsets.safeDrawing.only(
            WindowInsetsSides.Bottom + WindowInsetsSides.Horizontal,
        )
        val context = LocalContext.current
        FlowRow(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(insets),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.Center,
            maxItemsInEachRow = 3,
        ) {
            Text(
                text = "这里有内容（点击打开系统抽屉页面）",
                modifier = Modifier
                    .clickable {
                        SystemDrawerActivity.start(context)
                    }
                    .padding(16.dp),
            )
        }
    }
}
