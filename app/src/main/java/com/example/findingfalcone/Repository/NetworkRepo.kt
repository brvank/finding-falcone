package com.example.findingfalcone.Repository

import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class NetworkRepo {

    private val client: OkHttpClient = OkHttpClient()

    fun get(api: String, callback: Callback) {
        val request: Request = Request.Builder().url(api).build()

        client.newCall(request).enqueue(callback)
    }

    fun post(api: String, data: String, callback: Callback){
        val request: Request = Request.Builder().addHeader("Accept", "application/json").url(api).post(data.toRequestBody("application/json; charset=utf-8".toMediaType())).build()

        client.newCall(request).enqueue(callback)
    }

    fun post(api: String, callback: Callback){
        val request: Request = Request.Builder().addHeader("Accept", "application/json").url(api).post("".toRequestBody("application/json; charset=utf-8".toMediaType())).build()

        client.newCall(request).enqueue(callback)
    }
}