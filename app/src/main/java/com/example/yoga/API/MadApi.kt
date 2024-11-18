package com.example.yoga.API

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MadApi {

    @GET("quotes")
    suspend fun getQuotes(): Response<QuoteResponse>

    @GET("feelings")
    suspend fun getFeelings(): Response<FeelingsResponse>

    @POST("user/login")
    suspend fun postUser(@Body loginRequest: LoginRequest): Response<User>
}