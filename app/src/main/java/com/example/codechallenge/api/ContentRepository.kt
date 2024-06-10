package com.example.codechallenge.api

interface ContentRepository {

    suspend fun fetchAbout(): String

    fun saveData(key: String, value: String)

    fun getData(key: String): String?

    fun clearData(key: String)

    fun hasData(key: String): Boolean
//    suspend fun
}
