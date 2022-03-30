package com.example.weathernow.domain.utils

import android.view.View

fun View.gone(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}