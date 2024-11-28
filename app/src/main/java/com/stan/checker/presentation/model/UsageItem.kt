package com.stan.checker.presentation.model

import android.graphics.drawable.Drawable

data class UsageItem(
    val icon: Drawable?,
    val name: String,
    val packageName: String,
    val timeStamp: Long,
    val timeInUse: String
)