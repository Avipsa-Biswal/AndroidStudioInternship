package com.example.Androidintern345.Model.Repository

import com.example.Androidintern345.Model.API.ApiService
import com.example.Androidintern345.Model.Data.Product


class ProductRepository(private val apiService: ApiService) {
    suspend fun getProducts(): List<Product> {
        return  apiService.getProducts()
    }
}