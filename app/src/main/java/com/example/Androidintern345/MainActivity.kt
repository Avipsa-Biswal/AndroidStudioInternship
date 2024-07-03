package com.example.Androidintern345

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.Androidintern345.ui.theme.MyApplicationTheme

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
                    //Change text color,size,weight
                    MyText4()
                    //Change different sections with different font color and design
                    MyText3()
                    //Use a brush for text styling
                    MyText2()
                    //Opacity in a span of text
                    MyText1()
                    //shadow effect
                    MyText0()

                }

            }
        }

    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface {
            content()
        }
    }
}

@Composable
fun MyText4(){
    Text(
        text = "hello silicon students!",
        color = Color.Cyan,
        fontSize = 30.sp,
        fontWeight = FontWeight.ExtraBold
    )

}

@Composable
fun MyText3()
{
    Text(text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.Green,
                fontSize = 30.sp ,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        ){
            append("Hello fellow developers, ")
        }
        append("this is our Android development Internship. ")
        withStyle(
            style = SpanStyle(
                color = Color.Blue,
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.SansSerif
            )
        ){
            append("let's learn something new! ")
        }
        append(" okay! ")
    },
        color = Color.Red ,
        fontSize = 30.sp ,
        fontWeight = FontWeight.ExtraBold,
        fontStyle = FontStyle.Normal,
        fontFamily = FontFamily.Monospace,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline)

}

@Composable
fun MyText2()
{
    val gradientColors = listOf(Color.Cyan, Color.Blue, Color.Green)
    Text(
        text = "You are not limited to this particular color scheme " +
                "or style of coloring. " +
                "While we have provided a simple example to highlight, " +
                "use any of the built-in brushes " +
                "or even just a SolidColor to enhance your text",
        style = TextStyle(
            brush = Brush.linearGradient(
                colors = gradientColors
            ),
            fontSize = 25.sp
        )
    )
}

@Composable
fun MyText1() {
    val rainbowColors = listOf(
        Color.Red, Color.Magenta, Color.Green, Color.Blue
    )

    val brush = Brush.linearGradient(colors = rainbowColors)

    val annotatedString = buildAnnotatedString {
        withStyle(
            SpanStyle(
                brush = brush, alpha = .5f, fontSize = 24.sp
            )
        ) {
            append("Text in ")
        }
        withStyle(
            SpanStyle(
                brush = brush, alpha = 1f, fontSize = 24.sp
            )
        ) {
            append("Compose ❤️")
        }
    }
    Text(text = annotatedString)
}
@Composable
fun MyText0() {
    val offset = Offset(5.0f, 10.0f)
    Text(
        text = "BYE! See you tomorrow",
        style = TextStyle(
            fontSize = 35.sp,
            shadow = Shadow(
                color = Color.Blue, offset = offset, blurRadius = 3f
            )
        )
    )
}

