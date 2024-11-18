package com.example.yoga.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitClient {
    val BASE_URL = "http://mskko2021.mad.hakta.pro/api/"
        val retrofit = Retrofit.Builder()
         .baseUrl(BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
    fun getMadInstance() = retrofit.build().create<MadApi>()
}