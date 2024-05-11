package com.yonjar.futbolapp.leagues.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject


class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val builder: Request.Builder = originalRequest.newBuilder()
            .header("Authorization", "P9zHt7ALes21uL7Yo4Il0a2O4Qpt800xwEXDf6tAXgosPYx2u17DiQanDhqs")

        val newRequest: Request = builder.build()
        val response = chain.proceed(newRequest)

        Log.i("Code response", response.code.toString())
        Log.i("Code message", response.message)
        Log.i("Code body", response.body.toString())

        return response
    }
}