package com.stan.checker.core.di

import android.app.Activity
import android.app.usage.UsageStatsManager
import android.content.Context
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
}