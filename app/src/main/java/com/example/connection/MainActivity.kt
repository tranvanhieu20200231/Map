package com.example.connection

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById<ListView>(R.id.listView)
        fetchData()
    }
    private fun fetchData() {
        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://lebavui.github.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        val service = retrofit.create(ApiService::class.java)
        val call = service.getUsers()

        call.enqueue(object : retrofit2.Callback<List<User>> {
            override fun onResponse(call: retrofit2.Call<List<User>>, response: retrofit2.Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null) {
                        displayData(users)
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<List<User>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun displayData(users: List<User>) {
        val userAdapter = UserAdapter(this, R.layout.list_item, users)
        listView.adapter = userAdapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val user = users[position]
            val locationUri = Uri.parse("geo:${user.address.geo.lat},${user.address.geo.lng}?q=${user.address.street}+${user.address.city}")
            val mapIntent = Intent(Intent.ACTION_VIEW, locationUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }
}



data class User(val name: String, val email: String, val address: Address, val thumbnail: String)

data class Address(val street: String, val city: String, val geo: Geo)

data class Geo(val lat: String, val lng: String)