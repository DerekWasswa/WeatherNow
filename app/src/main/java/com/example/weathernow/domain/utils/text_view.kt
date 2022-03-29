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

}