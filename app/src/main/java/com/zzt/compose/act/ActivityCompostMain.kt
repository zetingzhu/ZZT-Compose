package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.zzt.ssssssssssssssss.ui.theme.SampleCompostTheme
import com.zzt.compose.R
import com.zzt.compose.act.ActivityCompostMain.Companion.TAG

class ActivityCompostMain : ComponentActivity() {
    companion object {
        val TAG = ActivityCompostMain::class.java.name
        fun startActivity(con: Context) {
            var intent = Intent(con, ActivityCompostMain::class.java)
            var bundle = Bundle()
            intent.putExtras(bundle)
            con.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = intent.extras
        when (extras?.getString("type")) {
            "1" -> {
                setContent {
                    SampleCompostTheme {
                        // A surface container using the 'background' color from the theme
                        Surface(color = MaterialTheme.colorScheme.background) {
                            Greeting("Android")
                        }
                    }
                }
            }

            "2" -> {
                setContent(content = {

                })
            }

            "3" -> {
                setContent {
                    ArtistCard()
                }
            }

            "4" -> {
                setContent {
                    ArtistCard2()
                }
            }

            "5" -> {
                setContent {
                    ArtistCard3(Artist("上面文案 , 这个比较长", "下面文案"))
                }
            }

            "6" -> {
                setContent {
                    ArtistCard4(Artist("上面文案 , 这个比较长", "下面文案"), onClick = {
                        Log.w(TAG, "这个是点击事件")
                    })
                }
            }

            "7" -> {
                setContent {
                    ArtistCard5(Artist("上面文案 , 这个比较长", "下面文案"), onClick = {
                        Log.w(TAG, "这个是点击事件")
                    })
                }
            }

            "8" -> {
                setContent {
                    createCardBox()
                }
            }

            "9" -> {
                setContent {
                    createCardWeight()
                }
            }

            "10" -> {
                setContent {
                    createCardClick()
                }
            }

            "11" -> setContent {
                ConstraintLayoutContent()
            }

        }
    }
}


@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Create references for the composables to constrain
        val (button, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
        })
    }
}


@Composable
private fun createCardClick() {
    val count = remember { mutableStateOf(0) }
    // content that you want to make clickable
    Column() {
        Text(
            text = count.value.toString(),
            modifier = Modifier
                .size(50.dp)
                .background(Color.DarkGray)
                .clickable { count.value += 1 },
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(20.dp))
        Text(
            text = count.value.toString(),
            Modifier
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {/* Called when the gesture starts */ },
                        onDoubleTap = { /* Called on Double Tap */ },
                        onLongPress = { /* Called on Long Press */ },
                        onTap = { /* Called on Tap */ }
                    )
                }
                .size(50.dp)
                .background(Color.Magenta)
                .clickable { count.value += 1 }
        )
    }


}


@Composable
fun createCardWeight() {
    Row(Modifier.width(210.dp)) {
        Box(
            Modifier
                .weight(2f)
                .height(50.dp)
                .background(Color.Blue)
        )
        Box(
            Modifier
                .weight(1f)
                .height(50.dp)
                .background(Color.Red)
        )
    }

}

@Composable
fun createCardBox() {
    Column() {
        Box(
            Modifier
                .size(100.dp, 100.dp)
                .background(Color.Black)
        )

        Spacer(modifier = Modifier.size(10.dp))
        // requiredSize 优选级比size高
        Box(
            Modifier
                .size(90.dp, 150.dp)
                .background(Color.Green)
        ) {
            Box(
                Modifier
                    .requiredSize(100.dp, 100.dp)
                    .background(Color.Red)
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        // fillMaxSize 使用
        Box(
            Modifier
                .background(Color.Green)
                .size(50.dp)
                .padding(10.dp)
        ) {
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        // fillMaxHeight fillMaxWidth 使用
        Box(
            Modifier
                .background(Color.Green)
                .size(60.dp), contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .background(Color.Blue)
                    .fillMaxHeight()
                    .fillMaxWidth(0.3f)
            )
        }
        Spacer(modifier = Modifier.size(10.dp))
        Box {
            Spacer(
                Modifier
                    .matchParentSize()
                    .background(Color.Green)
            )
            Text("Hello World")
        }
        Spacer(modifier = Modifier.size(10.dp))
        Box(Modifier.background(Color.Yellow)) {
            Text("Hi there!", Modifier.paddingFromBaseline(top = 32.dp))
        }
        Spacer(modifier = Modifier.size(10.dp))

        Box(
            Modifier
                .background(Color.Yellow)
                .size(width = 150.dp, height = 70.dp)
        ) {
            Text(
                "偏移量使用",
                Modifier.offset(x = 15.dp, y = 20.dp)
            )
        }

    }
}

@Composable
fun ArtistCard5(
    artist: Artist,
    onClick: () -> Unit
) {
    val padding = 16.dp
    var textModifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
    val CELL_SIZE = 48.dp
    Column(
        Modifier
            .clickable(onClick = onClick)
            .padding(padding)
            .fillMaxWidth()
    ) {
        Text("*这个内容展示一")
        Text(
            "*展示二",
            modifier = Modifier
                .background(
                    color = Color(0xFFC0F7B4),
                    shape = MaterialTheme.shapes.small
                )
                .padding(10.dp, 20.dp, 30.dp, 40.dp)
        )
        Spacer(
            modifier = Modifier
                .size(50.dp, 50.dp)
                .background(
                    color = Color(443322),
                )
        ) //   Spacer(Modifier.preferredSize(padding))
        Text(
            "*展示第三行内容",
            Modifier
                .background(
                    color = Color(0xFFC650E4),
                    shape = MaterialTheme.shapes.medium
                )
                .fillMaxWidth()
        )
        Spacer(Modifier.height(50.dp)) //   Spacer(Modifier.preferredSize(padding))
        Card(elevation = CardDefaults.cardElevation()) {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.text_v1),
                modifier = Modifier
                    .size(90.dp, 90.dp)
            )
        }
        Spacer(Modifier.height(20.dp))
        Card(elevation = CardDefaults.cardElevation()) {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.text_v1),
                modifier = Modifier
                    .size(120.dp, 100.dp)
                    .padding(20.dp)
                    .clickable {
                        Log.w(TAG, "点击这个小箭头，小箭头")
                    }
                    .alpha(0.8F)
                    .background(
                        color = Color(444444),
                        shape = MaterialTheme.shapes.small
                    )
                    .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(16.dp))
                    .weight(1f)
                    .heightIn(max = CELL_SIZE)
                    .then(textModifier)
            )
        }
        Spacer(Modifier.height(20.dp))
        Image(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = stringResource(R.string.text_v1),
            modifier = Modifier
                .size(90.dp, 90.dp)
        )
        Text(
            "*展示四",
            modifier = Modifier
                .background(
                    color = Color(0xFFF75868),
                    shape = MaterialTheme.shapes.small
                )
                .padding(10.dp, 10.dp, 10.dp, 10.dp)
        )
    }
}

@Composable
fun ArtistCard4(
    artist: Artist,
    onClick: () -> Unit
) {
    val padding = 16.dp
    Column(
        Modifier
            .clickable(onClick = onClick)
            .padding(padding)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(Color(0xFFCFDEF7))
        ) {
            Text("这个内容展示一\n 换行，看看对齐方式")
            Text("展示二", Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp))
        }
        Spacer(modifier = Modifier.size(10.dp))
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .background(Color(0xFFE7E7E7))

        ) {
            Text("这个内容展示一\n 换行，看看对齐方式")
            Text("展示二", Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp))
        }
        Spacer(Modifier.size(100.dp, 100.dp)) //   Spacer(Modifier.preferredSize(padding))
        Card(elevation = CardDefaults.cardElevation()) {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.text_v1),
            )
        }
    }
}

@Composable
fun ArtistCard() {
    Text("Alfred Sisley")
    Text("3 minutes ago")
}

@Composable
fun ArtistCard2() {
    Column() {
        Text("Alfred Sisley")
        Text("3 minutes ago")
    }
}

data class Artist(
    var name: String, var lastSeenOnline: String
)

@Composable
fun ArtistCard3(artist: Artist) {
    Box() {
        Row(verticalAlignment = Alignment.Top) {
            Image(
                painter = painterResource(R.drawable.ic_search),
                contentDescription = stringResource(R.string.text_v1),
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(artist.name)
                Text(artist.lastSeenOnline)
            }
        }
        Image(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = "这个是上面图片",
            alignment = Alignment.BottomCenter
        )
    }
}


@Composable
fun createTextV1(text: String) {
    Text(
        text = "这个是test",
        //`边框`
        modifier = Modifier.border(
            border = BorderStroke(
                width = 3.dp,
                color = Color(0xFF999999),
            ),
            shape = RoundedCornerShape(20f, 60f, 20f, 160f),
        ),
        //`颜色`
        color = Color.Green,
        //`字体大小`、
        fontSize = 20.sp,
        //字形的厚度
        fontWeight = FontWeight.Bold,
        //斜体
        fontStyle = FontStyle.Italic,
        fontFamily = FontFamily.Default,
        //字体水平间隔
        letterSpacing = 0.em,
        //在文本上方绘制一条水平线
        textDecoration = TextDecoration.LineThrough
    )
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SampleCompostTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun ComposablePreview() {
    Greeting("Android")
}

@Preview(widthDp = 50, heightDp = 50)
@Composable
fun SquareComposablePreview() {
    Box(Modifier.background(Color.Yellow)) {
        Text("Hello World")
    }
}

@Preview(locale = "fr-rFR")
@Composable
fun DifferentLocaleComposablePreview() {
    Text(text = stringResource(R.string.greetings))
}


@Preview(showSystemUi = true)
@Composable
fun DecoratedComposablePreview() {
    Text("Hello World")
}
