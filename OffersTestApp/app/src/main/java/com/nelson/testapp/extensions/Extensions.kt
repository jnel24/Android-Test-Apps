package com.nelson.testapp.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View

fun View.getLayoutInflater(): LayoutInflater {
    return context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}