package com.example.Androidintern345

import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.Androidintern345.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                //Function to understand State Management
                CounterScreen()
                //Function to understand Navigation Controller and implementing lazy vertical grid in screen 5
                AppNavigation()

            }
        }
    }
}

@Composable
fun CounterScreen(){
    var count: Int by remember {
        mutableStateOf( 0)
    }
    var text: String by remember {
        mutableStateOf( " ")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Text(text="The count value is $count")
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = { count++ }) {
            Text(text = "Increment")
        }
        Button(onClick = { count-- }) {
            Text(text = "Decrement")
        }
        TextField(
            value = text ,
            onValueChange = { text=it },
            label = { Text ("enter password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(navController = navController,
            startDestination = "Screen1") {
        composable("Screen1") { Screen1(navController) }
        composable("Screen2") { Screen2(navController) }
        composable("Screen3") { Screen3(navController) }
        composable("Screen4/{data}",
            arguments = listOf(navArgument("data") { type = NavType.StringType })
        ) { backStackEntry ->
            Screen4(navController, backStackEntry.arguments?.getString("data") ?: "")
        }
        composable("Screen5") { Screen5(navController) }
    }
}


@Composable
fun Screen1(navController: NavHostController) {
    Column {
        Text(
            text = "This is Screen1... Welcome!!!",
            fontSize = 30.sp,
            color = Color.Red
        )
        Button(onClick = { navController.navigate("Screen2") }) {
            Text(text = "Go to Screen2")
        }
    }
}

@Composable
fun Screen2(navController: NavHostController) {
    Column {
        Text(
            text = "This is Screen2... Welcome!!!",
            fontSize = 30.sp,
            color = Color.Red
        )
        Button(onClick = { navController.navigate("Screen3") }) {
            Text(text = "Go to Screen3")
        }
    }
}

@Composable
fun Screen3(navController: NavHostController) {
    var text by remember { mutableStateOf("") }
    Column {
        Text(
            text = "This is Screen3... Welcome!!!",
            fontSize = 30.sp,
            color = Color.Red
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it }
        )
        Button(onClick = {
            if (text.isNotBlank()) {
                navController.navigate("Screen4/${text.trim()}")
            }
        }) {
            Text(text = "Go to Screen4")
        }
    }
}
@Composable
fun Screen4(navController: NavHostController,data:String) {
    Column {
        Text(
            text = "this is screen4...welcome!!! data is $data",
            fontSize = 30.sp,
            color=Color.Red
        )
        Button(onClick = { navController.navigate("screen5") }) {
            Text(text = "go to screen5")
        }
    }
}
@Composable
fun Screen5(navController: NavHostController) {
    val itemsList: List<String> = List(100){"items = $it"}
    LazyVerticalGrid(columns = GridCells.Adaptive(128.dp)) {
        items(itemsList){
            Card {
            Text(text = it)
        }
    }
  }
}