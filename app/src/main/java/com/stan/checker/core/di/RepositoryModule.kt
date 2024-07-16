package com.stan.checker.core.di

import com.stan.checker.data.datasource.AppInfoDataSource
import com.stan.checker.data.datasource.UsageDataSource
import com.stan.checker.data.repository.UsageRepository
import com.stan.checker.data.repository.UsageRepositoryImpl
import com.stan.checker.usage.DateManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideUsageRepository(
        usageDataSource: UsageDataSource,
        dateManager: DateManager,
        appInfoDataSource: AppInfoDataSource
    ): UsageRepository {
        return UsageRepositoryImpl(
            usageDataSource = usageDataSource,
            dateManager = dateManager,
            appInfoDataSource = appInfoDataSource
        )
    }
}