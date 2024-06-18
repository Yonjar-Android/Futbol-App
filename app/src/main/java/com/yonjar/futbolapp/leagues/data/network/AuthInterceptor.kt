package com.yonjar.futbolapp.leagues.data.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject


class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val builder: Request.Builder = originalRequest.newBuilder()
            .header("Authorization", "P9zHt7ALes21uL7Yo4Il0a2O4Qpt800xwEXDf6tAXgosPYx2u17DiQanDhqs")

        val newRequest: Request = builder.build()
        val response = chain.proceed(newRequest)

        //Log.i("Code response", response.code.toString())
        //Log.i("Code message", response.message)

        // Obteniendo el cuerpo de la respuesta como una cadena
        val responseBodyString = response.body?.string()
        //Log.i("Code body", responseBodyString ?: "Empty body")

        // Reconstruir el cuerpo de la respuesta, ya que al llamar a `string()`, el cuerpo de la respuesta se consume y no se puede leer nuevamente.
        val newResponseBody = responseBodyString?.toResponseBody(response.body?.contentType())

        // Construir una nueva respuesta con el cuerpo reconstruido
        val newResponse = response.newBuilder().body(newResponseBody).build()

        return newResponse
    }

}