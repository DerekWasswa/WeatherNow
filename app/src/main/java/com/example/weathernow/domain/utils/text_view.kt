package com.example.weathernow.domain.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter

object TextViewBindings{

    @BindingAdapter(value = ["date", "format","default", "prepend", "append"], requireAll = false)
    @JvmStatic
    fun TextView.setDate(date: String?, format: String?, default:String?, prepend: String?, append: String?) {
        val zonedDateTime = date?.getZonedDateTime()
        text = zonedDateTime?.let { "${prepend?.let { p -> "$p " } ?: ""}${it.formatTo(format ?: "dd MM yyyy")}${append?.let { p -> "$p " } ?: ""}" }
            ?: default?:""
    }

    @BindingAdapter(value = ["dtx"], requireAll = false)
    @JvmStatic
    fun TextView.setDtx(date: String) {
        text = date.getDateTimeFromString()
    }

    @BindingAdapter(value = ["wind"], requireAll = false)
    @JvmStatic
    fun TextView.setWind(wind: Double) {
        val value = "$wind km/h"
        text = value
    }

    @BindingAdapter(value = ["humidity"], requireAll = false)
    @JvmStatic
    fun TextView.setHumidity(humidity: Long) {
        val value = "$humidity %"
        text = value
    }

    @BindingAdapter(value = ["pressure"], requireAll = false)
    @JvmStatic
    fun TextView.setPressure(pressure: Long) {
        val value = "${pressure}mb"
        text = value
    }

}