package com.example.Androidintern345.Model.Data

data class User(
    val id: String,
    val email:String,
    val username: String,
    val password: String,
    val name: Name
)

data class Name (
    val firstname: String,
    val lastname: String
)