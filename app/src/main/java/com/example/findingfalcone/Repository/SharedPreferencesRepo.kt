package com.example.findingfalcone.Repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class SharedPreferencesRepo(context: Context) {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

    init {
        sharedPreferences = context.getSharedPreferences("finding_falcone", MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun saveToken(key: String = "token", value: String){
        editor.putString(key, value)
        editor.commit()
    }

    fun getToken() : String {
        return sharedPreferences.getString("token", "")!!
    }
}