package com.stan.checker.core.di

import android.app.usage.UsageStatsManager
import com.stan.checker.usage.DateManager
import com.stan.checker.usage.NamingHelper
import com.stan.checker.usage.NamingHelperImpl
import com.stan.checker.usage.UsageProvider
import com.stan.checker.usage.UsageProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UsageModule {

    @Provides
    fun provideNamingHelper(): NamingHelper {
        return NamingHelperImpl()
    }

    @Provides
    fun provideUsageProvider(
        usageStatsManager: UsageStatsManager,
        namingHelper: NamingHelper,
        dateManager: DateManager
    ): UsageProvider {
        return UsageProviderImpl(
            usageManager = usageStatsManager,
            namingHelper = namingHelper,
            dateManager = dateManager
        )
    }
}