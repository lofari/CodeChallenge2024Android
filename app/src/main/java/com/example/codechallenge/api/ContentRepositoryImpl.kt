package com.example.codechallenge.api

import android.content.SharedPreferences
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val sharedPreferences: SharedPreferences

) : ContentRepository {

    override suspend fun fetchAbout(): String {
        val result = api.fetchAbout()
        return result.string()
    }

    override fun saveData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun clearData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun hasData(key: String): Boolean {
        return sharedPreferences.contains(key)
    }
}
