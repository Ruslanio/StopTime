package com.stan.checker.usage.model

import android.graphics.drawable.Drawable

data class Usage(
    val icon: Drawable?,
    val name: String,
    val packageName: String,
    val timeInUse: Long
)