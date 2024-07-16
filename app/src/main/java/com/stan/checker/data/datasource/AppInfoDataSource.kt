package com.stan.checker.data.datasource

import android.graphics.drawable.Drawable

interface AppInfoDataSource {
    fun getApplicationIcon(packageName: String): Drawable?
    fun getApplicationLabel(packageName: String): String
}