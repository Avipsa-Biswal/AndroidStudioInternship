package com.example.Androidintern345

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.Androidintern345.Model.Data.User
import com.example.Androidintern345.ui.theme.MyApplicationTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val userList = remember {
                mutableStateOf<List<Users>>(emptyList())
            }
            LaunchedEffect(Unit) {
                fetchStudents {users ->
                    userList.value = users
                }
            }
            MyApplicationTheme {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ){
                    items(userList.value){user ->
                        Text(text = "Name:${user.name},Age:${user.age}, SIC:${user.sic}" )
                    }
                }
                //addUserScreen()
            }
        }
    }

    val db = Firebase.firestore

    fun addUser(name: String, age: Int, sic: String) {
        val user = Users(name, age, sic)
        db.collection("New")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
            /*.document(sic)
            .set(user)*/
        }

    fun fetchStudents(onResult: (List<Users>) -> Unit){
        db.collection("New")
            .get()
            .addOnSuccessListener {result->
                val userList = result.map{ document -> document.toObject(Users::class.java)}
                onResult(userList)
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error getting document", e)
        }
    }

    @Composable
    fun addUserScreen() {
        var name by remember { mutableStateOf("") }
        var age by remember { mutableStateOf("") }
        var sic by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = " Enter Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))


            TextField(
                value = age,
                onValueChange = { age = it },
                label = { Text(text = " Enter Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = sic,
                onValueChange = { sic = it },
                label = { Text(text = " Enter SIC") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { addUser(name, age.toInt(), sic) }) {
                Text(text = "Add User")
            }
        }
    }
}

//DataClass

data class Users(
    val name: String = "",
    val age: Int = 0,
    val sic: String
)


