package com.example.Androidintern345.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Androidintern345.Model.API.RetrofitClient
import com.example.Androidintern345.Model.Data.User
import com.example.Androidintern345.Model.Repository.ProductRepository
import com.example.Androidintern345.Model.Repository.UserRepository
import kotlinx.coroutines.launch


class UserViewModel: ViewModel() {
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val repository = UserRepository(RetrofitClient.apiService)

    init {
        fetchUsers()
    }
    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val userList = repository.getUsers()
                _users.postValue(userList)
                println("API DATA CALLED: $userList")
            } catch (e: Exception) {

            }
        }
    }
}