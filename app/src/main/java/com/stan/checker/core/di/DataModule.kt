package com.stan.checker.core.di

import android.app.usage.UsageStatsManager
import android.content.pm.PackageManager
import com.stan.checker.data.repository.UsageRepository
import com.stan.checker.data.repository.UsageRepositoryImpl
import com.stan.checker.usage.DateManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideUsageRepository(
        usageStatsManager: UsageStatsManager,
        dateManager: DateManager,
        packageManager: PackageManager
    ): UsageRepository {
        return UsageRepositoryImpl(
            usageManager = usageStatsManager,
            dateManager = dateManager,
            packageManager = packageManager
        )
    }
}