package com.example.Androidintern345

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
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
import androidx.core.content.OnTrimMemoryProvider
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
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private var storedVerificationId: String? = null
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            MyApplicationTheme {
                LoginScreen()
            }
        }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted:$credential")
            // signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.w(TAG, "onVerificationFailed", e)
            when (e) {
                is FirebaseAuthInvalidCredentialsException -> {
                    // Invalid request
                }
                is FirebaseTooManyRequestsException -> {
                    // SMS quota exceeded
                }
                is FirebaseAuthMissingActivityForRecaptchaException -> {
                    // reCAPTCHA verification attempted with null Activity
                }
                else -> {
                    // Handle other errors
                }
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            Log.d(TAG, "onCodeSent:$verificationId")
            storedVerificationId = verificationId
            resendToken = token
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyOTP(otp: String) {
        storedVerificationId?.let {
            val credential = PhoneAuthProvider.getCredential(it, otp)
            signInWithPhoneAuthCredential(credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = task.result?.user
                    // Update UI with user info
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // Invalid verification code
                    }
                    // Update UI
                }
            }
    }

    @Composable
    fun LoginScreen() {
        var phoneNumber by remember { mutableStateOf("") }
        var otp by remember { mutableStateOf("") }

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
                            value = phoneNumber,
                            onValueChange = { phoneNumber = it },
                            label = { Text(text = "Enter phone number") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { startPhoneNumberVerification(phoneNumber) }) {
                            Text(text = "Send OTP")
                        }

                        OutlinedTextField(
                            value = otp,
                            onValueChange = { otp = it },
                            label = { Text(text = "Enter OTP") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { verifyOTP(otp) }) {
                            Text(text = "Validate OTP")
                        }
                    }
                }
            }
        }
    }
}






