package com.stan.checker.core.di

import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
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
    fun provideNotificationManager(@ApplicationContext context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    @Provides
    fun provideAlarmManager(@ApplicationContext context: Context): AlarmManager {
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Provides
    fun providePackageManager(@ApplicationContext context: Context): PackageManager {
        return context.packageManager
    }
}