package com.example.Androidintern345

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.Androidintern345.Model.Data.Product
import com.example.Androidintern345.ViewModel.ProductViewModel
import com.example.Androidintern345.ViewModel.UserViewModel
import com.example.Androidintern345.Views.BottomBar
import com.example.Androidintern345.Views.ProductScreen
import com.example.Androidintern345.Views.UserScreen
import com.example.Androidintern345.ui.theme.MyApplicationTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import com.google.firebase.FirebaseApp


class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // Ensure Firebase is initialized
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "login") {
                    composable("login") { LoginScreen(navController) }
                    composable("register") { RegisterScreen(navController) }
                    composable("home") { HomeScreen() }
                }
            }
        }
    }

    @Composable
    fun LoginScreen(navController: NavHostController) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(50.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(text = "Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                                 )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = "Password") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { signIn(email, password, navController, setError = { errorMessage = it }) }) {
                            Text(text = "Login")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = { navController.navigate("register") }) {
                            Text(text = "Register")
                        }
                        errorMessage?.let {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = it)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun RegisterScreen(navController: NavHostController) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        Scaffold {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(50.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text(text = "Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = "Password") },
                            modifier = Modifier
                                .fillMaxWidth(),
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { signUp(email, password, navController, setError = { errorMessage = it }) }) {
                            Text(text = "Register")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = { navController.navigate("login") }) {
                            Text(text = "Login")
                        }
                        errorMessage?.let {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = it)
                        }
                    }
                }
            }
        }
    }
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun HomeScreen() {
        Scaffold {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Welcome to the Home Screen!", fontSize = 24.sp)
            }
        }
    }


    private fun signIn(email: String, password: String, navController: NavHostController, setError: (String?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
            .addOnFailureListener {
                setError(it.message)
            }
    }

    private fun signUp(email: String, password: String, navController: NavHostController, setError: (String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                navController.navigate("home") {
                    popUpTo("register") { inclusive = true }
                }
            }
            .addOnFailureListener {
                setError(it.message)
            }
    }
}



