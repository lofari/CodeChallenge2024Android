package com.example.codechallenge.api

import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiService {

    @GET("/")
    suspend fun fetchAbout(): ResponseBody
}
