package com.example.Androidintern345

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.Androidintern345.ui.theme.MyApplicationTheme
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.em

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    //Set text alignment
                    CenterText()
                    //Add multiple styles in a paragraph
                    ParagraphStyle1()
                    //Adjust line height and padding
                    HeightPadding()
                    //Insert line breaks
                    LineBreaks1()
                    //Hyphenate text split across lines
                    HyphenateText()
                    //limit visible lines
                    LongText()
                    //Indicate overflow in text
                    OverflowedText()


                    }

            }
        }

    }
}


@Composable
fun CenterText() {
    Text("Hello Everyone!", textAlign = TextAlign.Center, modifier = Modifier.width(150.dp)
    )
}

@Composable
fun ParagraphStyle1() {
    Text(
        buildAnnotatedString {
            withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                withStyle(style = SpanStyle(color = Color.Blue)) {
                    append("Hello\n")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold, color = Color.Red
                    )
                ) {
                    append("World\n")
                }
                append("Compose")
            }
        }
    )
}

@Composable
fun HeightPadding() {
    Text(
        text = "Hello world!\nIts a wonderful\n day",
        style = LocalTextStyle.current.merge(
            TextStyle(
                lineHeight = 2.5.em,
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                ),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.FirstLineTop
                )
            )
        )
    )
}

@Composable
fun TextSample(samples: Map<String, @Composable () -> Unit>) {
    Column {
        samples.forEach { (label, content) ->
            Text(text = label, fontSize = 16.sp, modifier = Modifier.padding(8.dp))
            content()
        }
    }
}

@Composable
fun LineBreaks1() {
    TextSample(
        samples = mapOf(
            "Simple" to {
                Text(
                    text = "Jetpack Compose is Android's modern toolkit for building native Ul. It simpli- fies and accelerates Ul development on Android bringing your apps to life with less code, powerful tools, and intuitive Kotlin APIs. ",
                    modifier = Modifier
                        .width(180.dp)
                        .border(BorderStroke(1.dp, Color.Gray))
                        .padding(8.dp),
                    fontSize = 14.sp,
                    style = TextStyle.Default
                )
            },
            "Paragraph" to {
                Text(
                    text = "Jetpack Compose is Android's modern toolkit for building native Ul. It simpli- fies and accelerates Ul development on Android bringing your apps to life with less code, powerful tools, and intuitive Kotlin APIs. ",
                    modifier = Modifier
                        .width(180.dp)
                        .border(BorderStroke(1.dp, Color.Gray))
                        .padding(8.dp),
                    fontSize = 14.sp,
                    style = TextStyle.Default
                )
            }
        )
    )
}

@Composable
fun HyphenateText() {
    TextSample(
        samples = mapOf(
            "Hyphens - None" to {
                Text(
                    text = "Jetpack Compose is Android's modern toolkit for building native Ul. It simpli- fies and accelerates Ul development on Android bringing your apps to life with less code, powerful tools, and intuitive Kotlin APIs. It makes building Android UI faster and easier.",
                    modifier = Modifier
                        .width(200.dp)
                        .border(BorderStroke(1.dp, Color.Gray)),
                    fontSize = 14.sp,
                    style = TextStyle.Default.copy(
                        lineBreak = LineBreak.Paragraph,
                        hyphens = Hyphens.None
                    )
                )
            },
            "Hyphens - Auto" to {
                Text(
                    text = "Jetpack Compose is Android's modern toolkit for building native Ul. It simpli- fies and accelerates Ul development on Android bringing your apps to life with less code, powerful tools, and intuitive Kotlin APIs. It makes building Android UI faster and easier.",
                    modifier = Modifier
                        .width(200.dp)
                        .border(BorderStroke(1.dp, Color.Gray)),
                    fontSize = 14.sp,
                    style = TextStyle.Default.copy(
                        lineBreak = LineBreak.Paragraph,
                        hyphens = Hyphens.Auto
                    )
                )
            }
        )
    )
}

@Composable
fun LongText() {
    Text("hello ".repeat(50), maxLines = 2)
}

@Composable
fun OverflowedText() {
    Text("Hello Compose ".repeat(50), maxLines = 2, overflow = TextOverflow.Ellipsis)
}