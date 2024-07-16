package com.stan.checker.data.datasource

import android.content.pm.PackageManager
import android.graphics.drawable.Drawable

class AppInfoDataSourceImpl(
    private val packageManager: PackageManager
) : AppInfoDataSource {

    override fun getApplicationIcon(packageName: String): Drawable? {
        val result = runCatching { packageManager.getApplicationIcon(packageName) }
        return result.getOrNull()
    }

    override fun getApplicationLabel(packageName: String): String {
        return packageManager.getApplicationLabel(
            packageManager.getApplicationInfo(packageName, 0)
        ).toString()
    }
}