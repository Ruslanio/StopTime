package com.stan.checker.core.di

import android.app.usage.UsageStatsManager
import android.content.pm.PackageManager
import com.stan.checker.data.repository.UsageRepository
import com.stan.checker.data.repository.UsageRepositoryImpl
import com.stan.checker.usage.DateManager
import com.stan.checker.usage.NamingHelper
import com.stan.checker.usage.NamingHelperImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideNamingHelper(): NamingHelper {
        return NamingHelperImpl()
    }

    @Provides
    fun provideUsageRepository(
        usageStatsManager: UsageStatsManager,
        namingHelper: NamingHelper,
        dateManager: DateManager,
        packageManager: PackageManager
    ): UsageRepository {
        return UsageRepositoryImpl(
            usageManager = usageStatsManager,
            namingHelper = namingHelper,
            dateManager = dateManager,
            packageManager = packageManager
        )
    }
}