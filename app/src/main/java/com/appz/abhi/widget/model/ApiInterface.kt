package com.appz.abhi.widget.model

import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {


    @GET("year?json")
    fun fetchRate(): Call<JsonObject>

    companion object {
        fun create(): ApiInterface {

            val client = OkHttpClient.Builder().build()

            return Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://numbersapi.com/random/")
                .build().create(ApiInterface::class.java)
        }
    }
}
