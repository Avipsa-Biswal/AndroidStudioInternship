package com.example.Androidintern345.Model.Repository

import com.example.Androidintern345.Model.API.ApiService
import com.example.Androidintern345.Model.Data.Product
import com.example.Androidintern345.Model.Data.User


class UserRepository(private val apiService: ApiService) {
    suspend fun getUsers(): List<User> {
        return  apiService.getUsers()
    }
}