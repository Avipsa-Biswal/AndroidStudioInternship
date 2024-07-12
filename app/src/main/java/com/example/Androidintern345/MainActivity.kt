//Make Sure you add dependencies in build.gradle file for Retrofit and Gson
// implementation(libs.retrofit)
// implementation(libs.converter.gson)
// Add Permission for Internet in Android Manifest file
//<uses-permission android:name="android.permission.INTERNET"/>

package com.example.Androidintern345

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Androidintern345.Model.Data.Product
import com.example.Androidintern345.ViewModel.ProductViewModel
import com.example.Androidintern345.ViewModel.UserViewModel
import com.example.Androidintern345.Views.BottomBar
import com.example.Androidintern345.Views.ProductScreen
import com.example.Androidintern345.Views.UserScreen
import com.example.Androidintern345.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            var value by remember { mutableFloatStateOf(0f) }
            var checked by remember { mutableStateOf(true) }
            val productVM: ProductViewModel by viewModels()
            val userVM: UserViewModel by viewModels()
            MyApplicationTheme {
                UserScreen(userVm = userVM)
                /*Box(modifier = Modifier.fillMaxSize()) {
                    ProductScreen(productVM, Modifier.padding(bottom = 56.dp))
                    BottomBar(modifier = Modifier.align(Alignment.BottomCenter))
                }*/
            }
        }
            /*Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        productVM.fetchProducts()
                    }) {
                        Text(text = "Click to make an API Call")
                    }*/
    }
}

