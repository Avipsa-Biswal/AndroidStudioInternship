package com.example.Androidintern345.Model.API

import com.example.Androidintern345.Model.Data.Product
import com.example.Androidintern345.Model.Data.User
import retrofit2.http.GET

interface  ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("users")
    suspend fun getUsers(): List<User>
}