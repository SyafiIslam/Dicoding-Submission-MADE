package com.syafi.core.data.remote.network

import com.syafi.core.util.Constants
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", Constants.API_KEY)
            .build()
        chain.proceed(requestHeaders)
    }

    private val certificatePinner = CertificatePinner.Builder()
        .add(Constants.HOSTNAME, "sha256/GyhWVHsOXNZc6tGTNd15kXF9YD0kEZaGxYn6MUva5jY=")
        .add(Constants.HOSTNAME, "sha256/Wec45nQiFwKvHtuHxSAMGkt19k+uPSw9JlEkxhvYPHk=")
        .add(Constants.HOSTNAME, "sha256/lmo8/KPXoMsxI+J9hY+ibNm2r0IYChmOsF9BxD74PVc=")
        .add(Constants.HOSTNAME, "sha256/6YBE8kK4d5J1qu1wEjyoKqzEIvyRY5HyM/NB2wKdcZo=")
        .add(Constants.HOSTNAME, "sha256/ICGRfpgmOUXIWcQ/HXPLQTkFPEFPoDyjvH7ohhQpjzs=")
        .build()

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .certificatePinner(certificatePinner)
        .build()

    private val retrofit=
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}