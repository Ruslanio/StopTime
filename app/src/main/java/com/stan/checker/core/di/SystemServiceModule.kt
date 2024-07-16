package com.stan.checker.core.di

import android.app.Activity
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object SystemServiceModule {

    @Provides
    fun provideUsageStatsManager(@ApplicationContext context: Context): UsageStatsManager {
        return context.getSystemService(Activity.USAGE_STATS_SERVICE) as UsageStatsManager
    }

    @Provides
    fun providePackageManager(@ApplicationContext context: Context): PackageManager {
        return context.packageManager
    }
}