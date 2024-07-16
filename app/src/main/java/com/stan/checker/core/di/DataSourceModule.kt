package com.stan.checker.core.di

import android.app.usage.UsageStatsManager
import android.content.pm.PackageManager
import com.stan.checker.data.datasource.AppInfoDataSource
import com.stan.checker.data.datasource.AppInfoDataSourceImpl
import com.stan.checker.data.datasource.UsageDataSource
import com.stan.checker.data.datasource.UsageDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideUsageDataSource(
        usageStatsManager: UsageStatsManager
    ): UsageDataSource {
        return UsageDataSourceImpl(
            usageStatsManager = usageStatsManager
        )
    }

    @Provides
    fun provideAppInfoDataSource(
        packageManager: PackageManager
    ): AppInfoDataSource {
        return AppInfoDataSourceImpl(
            packageManager = packageManager
        )
    }
}