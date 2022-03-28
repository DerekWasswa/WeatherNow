package com.example.weathernow.domain.data.api

import android.content.Context

abstract class BaseRetrofitModule constructor(var context: Context) {
    abstract fun getApiServiceService() : ApiService
}