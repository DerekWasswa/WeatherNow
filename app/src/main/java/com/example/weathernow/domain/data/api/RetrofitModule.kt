package com.example.weathernow.domain.data.api

import android.content.Context
import com.example.weathernow.BuildConfig
import com.example.weathernow.R
import com.example.weathernow.domain.utils.Constants
import com.example.weathernow.domain.utils.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@ExperimentalSerializationApi
class RetrofitModule(context: Context) : BaseRetrofitModule(context) {

    private lateinit var retrofitModule : Retrofit

    override fun getApiServiceService() : ApiService {
        val contentType = "application/json".toMediaType()
        val converterFactory = Json.asConverterFactory(contentType)
        val gson : Gson = GsonBuilder().setLenient().create()
        val clientBuilder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)
        clientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        clientBuilder.readTimeout(60, TimeUnit.SECONDS)

        clientBuilder.addInterceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header("User-Agent", context.getString(R.string.app_name))
            requestBuilder.header("appid", BuildConfig.OPEN_WEATHER_APIKEY)
            chain.proceed(requestBuilder.build())
        }

        clientBuilder.build()

        retrofitModule = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(converterFactory)
            .build()


        return retrofitModule.create(ApiService::class.java)
    }

}