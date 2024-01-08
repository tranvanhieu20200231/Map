package com.example.connection

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("jsons/users.json")
    fun getUsers(): Call<List<User>>
}