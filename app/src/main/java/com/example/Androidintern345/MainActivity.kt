package com.example.Androidintern345

import com.example.Androidintern345.ui.theme.MyApplicationTheme
import android.net.Uri
import android.os.Bundle
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import java.util.UUID

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge()
        setContent {
//            val userList = remember {
//                mutableStateOf<List<User>>(emptyList())
//            }
//            LaunchedEffect(Unit) {
//                fetchStudents { users ->
//                    userList.value = users
//                }
//            }
            MyApplicationTheme {
                // AddUserScreen()
//                LazyColumn(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(20.dp)
//                ) {
//                    items(userList.value) { user ->
//                        Text(text = "NAME: ${user.name}, Age: ${user.age}")
//                    }
//                }
                ImageUploadScreen()
            }
        }
    }

    val db = Firebase.firestore
    fun addUser(name: String, age: Int) {
        val user = User(
            name,
            age
        )
        db.collection("users")
            .add(user)
            .addOnSuccessListener { docRef ->
                Log.d(TAG, "DOCUMENT SNAPSHOT ADDED WITH ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error Adding Document")

            }
    }

    fun fetchStudents(onResult: (List<User>) -> Unit) {
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val userList = result.map { document -> document.toObject(User::class.java) }
                onResult(userList)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error getting document", e)
            }
    }

    val storage = Firebase.storage
    val storageRef = storage.reference
    fun uploadImage(uri: Uri, context: android.content.Context) {
        val fileName = "Images/${UUID.randomUUID()}.jpg"
        val imageRef = storageRef.child(fileName)

        imageRef.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    Toast.makeText(context, "Image Uploaded Successfully: $uri", Toast.LENGTH_LONG)
                        .show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Image Uploading Failed: ${e.message}", Toast.LENGTH_SHORT)
                    .show()

            }
    }

    @Composable
    fun ImageUploadScreen() {
        val context = LocalContext.current
        val imageUri = remember { mutableStateOf<Uri?>(null) }
        val launcher =
            rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri.value = uri
                uri?.let { uploadImage(uri, context) }
            }
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { launcher.launch("image/*") }) {
                Text("SELECT IMAGE")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        imageUri.value?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }
    }

    @Composable
    fun AddUserScreen() {
        var name by remember { mutableStateOf("") }
        var age by remember {
            mutableStateOf("")
        }
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(text = "ENTER NAME") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = age,
                onValueChange = { age = it },
                label = { Text(text = "ENTER AGE") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                addUser(name, age.toInt())
            }) {
                Text(text = "Add User")
            }
        }
    }


    // DATA CLASS
    data class User(
        val name: String = "",
        val age: Int = 0
    )
}