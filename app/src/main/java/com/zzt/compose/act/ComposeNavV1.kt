package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material3.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzt.compose.R
import com.zzt.compose.entity.AlignYourBodyData
import com.zzt.compose.entity.MessageObj
import java.util.*


class ComposeNavV1 : ComponentActivity() {

    companion object {
        val TAG = ComposeNavV1::class.java.simpleName
        fun start(context: Context) {
            val starter = Intent(context, ComposeNavV1::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            BottomNavPreview()
            ScreenContentPreview()
        }
    }

    /**
     * 底部导航栏
     */
    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun BottomNavPreview() {
        Column {
            SearchBar()
            AlignYourBodyElement(
                textStr = "R.string.ab1_inversions",
                drawable = R.drawable.ic_search,
                modifier = Modifier.padding(8.dp)
            )
            FavoriteCollectionCard(
                text = "fc2_nature_meditations",
                drawable = R.drawable.ic_search,
                modifier = Modifier.padding(8.dp)
            )
            HomeSectionPreview() //  AlignYourBodyRow()
            FavoriteCollectionsGrid()
//            SootheBottomNavigation()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SearchBar(
        modifier: Modifier = Modifier
    ) {
        TextField(value = "", onValueChange = {}, leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, contentDescription = null
            )
        }, colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.surface
        ), placeholder = {
            Text(stringResource(R.string.placeholder_search))
        }, modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
        )
    }


    @Composable
    fun AlignYourBodyElement(
        @DrawableRes drawable: Int, textStr: String, modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
            )
            Text(
                text = textStr,

                modifier = Modifier.paddingFromBaseline(
                    top = 24.dp, bottom = 8.dp
                )
            )
        }
    }

    @Composable
    fun HomeSection(
        @StringRes title: Int, modifier: Modifier = Modifier, content: @Composable () -> Unit
    ) {
        Column(modifier) {
            Text(
                text = stringResource(title).uppercase(Locale.getDefault()),
                modifier = Modifier
                    .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
                    .padding(horizontal = 16.dp)
            )
            content()
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun HomeSectionPreview() {
        HomeSection(R.string.align_your_body) {
            AlignYourBodyRow()
        }
    }


    /**
     * 屏幕滚动
     */
    @Composable
    fun HomeScreen(modifier: Modifier = Modifier) {
        Column(
            modifier
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp)
        ) {
            SearchBar(Modifier.padding(horizontal = 16.dp))
            HomeSection(title = R.string.align_your_body) {
                AlignYourBodyRow()
            }
            HomeSection(title = R.string.favorite_collections) {
                FavoriteCollectionsGrid()
            }
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2, heightDp = 180)
    @Composable
    fun ScreenContentPreview() {
//        MySootheApp()// HomeScreen()
    }

    @Composable
    fun FavoriteCollectionCard(
        @DrawableRes drawable: Int, text: String, modifier: Modifier = Modifier
    ) {
        Surface(
            shape = MaterialTheme.shapes.small, modifier = modifier
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.width(192.dp)
            ) {
                Image(
                    painter = painterResource(drawable),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(56.dp)
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }

    fun getBodyList(): MutableList<AlignYourBodyData> {
        var messageList = mutableListOf<AlignYourBodyData>()

        for (i in 0..10) {
            messageList.add(AlignYourBodyData(R.drawable.ic_grain, "body:$i"))
        }

        return messageList;
    }

    @Composable
    fun AlignYourBodyRow(
        modifier: Modifier = Modifier
    ) {
        val bodyList = remember {
            getBodyList()
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier
        ) {

            items(bodyList) { item ->
                AlignYourBodyElement(item.drawable, item.text)
            }
            items(count = bodyList.size, key = { index -> bodyList[index].drawable }) {}

        }
    }


    @Composable
    fun FavoriteCollectionsGrid(
        modifier: Modifier = Modifier
    ) {

        val bodyList = remember {
            getBodyList()
        }

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.height(120.dp)
        ) {
            items(bodyList) { item ->
                FavoriteCollectionCard(
                    drawable = item.drawable, text = item.text, modifier = Modifier.height(56.dp)
                )
            }
        }
    }

//    @Composable
//    private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
//        BottomNavigation(
//            backgroundColor = MaterialTheme.colors.background, modifier = modifier
//        ) {
//            BottomNavigationItem(icon = {
//                Icon(
//                    imageVector = Icons.Default.Spa, contentDescription = null
//                )
//            }, label = {
//                Text(stringResource(R.string.bottom_navigation_home))
//            }, selected = true, onClick = {})
//            BottomNavigationItem(icon = {
//                Icon(
//                    imageVector = Icons.Default.AccountCircle, contentDescription = null
//                )
//            }, label = {
//                Text(stringResource(R.string.bottom_navigation_profile))
//            }, selected = false, onClick = {})
//        }
//    }

//    @Composable
//    fun MySootheApp() {
//        Scaffold(bottomBar = { SootheBottomNavigation() }) { padding ->
//            HomeScreen(Modifier.padding(padding))
//        }
//    }
}

