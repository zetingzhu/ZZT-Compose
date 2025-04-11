package com.zzt.compose.act

import android.content.Context
import android.content.Intent
import android.content.pm.ModuleInfo
import android.os.Bundle
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.zzt.compose.R

class ComposeActText : ComponentActivity() {
    val TAG = ComposeActText::class.java.simpleName

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ComposeActText::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposablePreview()
        }
    }

    @Preview
    @Composable
    fun ComposablePreview() {
//        (modifier = Modifier.padding(top = 20.dp, bottom = 30.dp))
        Column(
            Modifier
                .background(Color.LightGray)
                .padding(top = 20.dp, bottom = 30.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            SimpleComposable()
            MessageCard(name = "传入字符串")
            StringResourceText()
            BlueText()
            BigText()
            ItalicText()
            BoldText()
            CenterText()
            TextShadow()
            DifferentFonts()
            MultipleStylesInText()
            ParagraphStyle()
            LongText()
            OverflowedText()
            AlignedText()
            SelectableText()
            PartiallySelectableText()
            SimpleClickableText()
            AnnotatedClickableText()
            SimpleFilledTextFieldSample(it = "输入修改文字")
            SimpleOutlinedTextFieldSample(it = "输入修改文字")
            StyledTextField(it = "输入修改文字")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun StyledTextField(it: String) {
        var value by remember { mutableStateOf("Hello\nWorld\nInvisible") }

        TextField(
            value = value,
            onValueChange = { value = it },
            label = { Text("Enter text") },
            maxLines = 2,
            textStyle = TextStyle(color = Color.Blue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SimpleOutlinedTextFieldSample(it: String) {
        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Label") }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SimpleFilledTextFieldSample(it: String) {
        var text by remember { mutableStateOf(value = "Hello") }

        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "Label") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        var text2 by rememberSaveable { mutableStateOf("这个就是输入框呀, 上面那个remember 居然是键盘，这鬼东西还要分开") }

        /**CompositionLocalProvider 这个是设置输入文字选中的那两个东西的,就是光标下面的大头**/
        val customTextSelectionColors = TextSelectionColors(
            handleColor = Color.Blue,
            backgroundColor = Color.Green.copy(alpha = 0.4f)
        )
        CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
            TextField(
                value = text2,
                onValueChange = { text2 = it },
                colors = TextFieldDefaults.textFieldColors(
                    // 光标
                    cursorColor = Color.Yellow,
                    // 文字颜色
                    textColor = Color.Red,
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        var text3 by remember { mutableStateOf("") }

        TextField(
            value = text3,
            onValueChange = { text3 = it },
            label = { Text("密码键盘是这样来的") },
            modifier = Modifier.background(color = Color.Red),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }

    @Composable
    fun AnnotatedClickableText() {
        val annotatedText = buildAnnotatedString {
            append("Click ")
            // We attach this *URL* annotation to the following content
            // until `pop()` is called

            pushStringAnnotation(
                tag = "URL",
                annotation = "https://developer.android.com"
            )
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("here")
            }
            pop()
            append(", ")
            pushStringAnnotation(
                tag = "baidu",
                annotation = "https://www.baidu.com"
            )
            withStyle(
                style = SpanStyle(
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("here")
            }

            pop()
            append(" -- ")
            pushStringAnnotation(
                tag = "++",
                annotation = " ++ "
            )
            withStyle(
                style = SpanStyle(
                    color = Color.Green,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("再加一个啥")
            }

            pop()
            append("， 原来上面那个无聊的功能是为了这个超链接准备的")
        }

        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                // We check if there is an *URL* annotation attached to the text
                // at the clicked position
                annotatedText.getStringAnnotations(
                    tag = "URL", start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    // If yes, we log its value
                    Log.d("Clicked URL", annotation.item)
                }

                annotatedText.getStringAnnotations(
                    tag = "baidu", start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    // If yes, we log its value
                    Log.d("Clicked URL", annotation.item)
                }
                annotatedText.getStringAnnotations(
                    tag = "++", start = offset,
                    end = offset
                ).firstOrNull()?.let { annotation ->
                    // If yes, we log its value
                    Log.d("Clicked URL", annotation.item)
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    @Composable
    fun SimpleClickableText() {
        ClickableText(
            text = AnnotatedString("Click Me ,无聊的功能，获取到的点击位置，"),
            onClick = { offset ->
                Log.d("ClickableText", "$offset -th character is clicked.")
            }
        )
    }

    @Composable
    fun PartiallySelectableText() {
        SelectionContainer {
            Column {
                Text("This text is selectable")
                Text("This one too")
                Text("This one as well")
                DisableSelection {
                    Text("But not this one")
                    Text("被选中文本中间可以跳过不被选中、")
                }
                Text("But again, you can select this one")
                Text("And this one too")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }

    @Composable
    fun SelectableText() {
        SelectionContainer {
            Text("设置当前文本可以被选中交互，你长按点点看。")
        }
    }

    @Composable
    fun AlignedText() {
        Column(modifier = Modifier.background(color = Color.White)) {
            Text(
                text = "来个长文本看看来个长文本看看来个长文本看看来个长文本看看来个长文本看看来个长文本看看来个长文本看看来个长文本看看来个长文本看看，" +
                        "\n和文本间距有关系？",
                style = LocalTextStyle.current.merge(
                    TextStyle(
                        lineHeight = 2.5.em,
                        lineHeightStyle = LineHeightStyle(
                            alignment = LineHeightStyle.Alignment.Center,
                            trim = LineHeightStyle.Trim.None
                        )
                    )
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.background(color = Color.Cyan)) {
                Text(
                    text = "第二个文本\n和间距有关系？lineHeightStyle 差别都看不出来\nabcdefg hijklmn",
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            lineHeight = 2.em,
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.LastLineBottom
                            )
                        )
                    )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.background(color = Color.Cyan)) {
                Text(
                    text = "第三个文本\n和间距有关系？lineHeightStyle 差别都看不出来\nabcdefg hijklmn",
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            lineHeight = 2.em,
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            )
                        )
                    )
                )
            }
        }

    }

    @Composable
    fun OverflowedText() {
        Text(
            "行数上限 2 截断方式。。。》".repeat(50), maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(8.dp))
    }

    @Composable
    fun LongText() {
        Text("行数上限 2 ".repeat(50), maxLines = 2)
        Spacer(modifier = Modifier.height(8.dp))
    }

    @Composable
    fun ParagraphStyle() {
        Text(
            buildAnnotatedString {
                append("Span 字体样式分开处理  >  ")
                withStyle(style = SpanStyle(fontSize = 30.sp)) {
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append("Hello\n")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Red,
                            fontSize = 15.sp
                        )
                    ) {
                        append("World\n")
                    }
                    append("Compose")
                }
                append("Span end >")
            }
        )
    }

    @Composable
    fun MultipleStylesInText() {
        Text(
            buildAnnotatedString {
                append("Span 字体样式分开处理  >  ")
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("H")
                }
                append("ello ")

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
                    append("W")
                }
                append("orld")
            }
        )
    }

    @Composable
    fun DifferentFonts() {
        Column {
            Text("Hello World  系统字体（Serif） ", fontFamily = FontFamily.Serif)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Hello World  系统字体（SansSerif）", fontFamily = FontFamily.SansSerif)
        }
    }

    @Composable
    fun TextShadow() {
        val offset = Offset(5.0f, 10.0f)
        Text(
            text = "给文字来个阴影",
            style = TextStyle(
                fontSize = 24.sp,
                shadow = Shadow(
                    color = Color.Blue,
                    offset = offset,
                    blurRadius = 3f
                )
            )
        )
    }

    @Composable
    fun CenterText() {
        Text(
            "文字居中对齐", textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Cyan)
        )
    }

    @Composable
    fun BoldText() {
        Text("设置粗体", fontWeight = FontWeight.Bold)
    }

    @Composable
    fun ItalicText() {
        Text("设置斜体", fontStyle = FontStyle.Italic)
    }

    @Composable
    fun BlueText() {
        Text("文字设置颜色", color = Color.Blue)
    }

    @Composable
    fun BigText() {
        Text("文字可以设置大小", fontSize = 30.sp)
    }

    @Composable
    fun StringResourceText() {
        Text(stringResource(R.string.hello_world))
    }

    @Composable
    fun SimpleComposable() {
        Text("普通的文班")
    }

    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name!")
    }

}