package com.stan.checker.core.di

import com.stan.checker.usage.DateManager
import com.stan.checker.usage.DateManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UtilModule {

    @Provides
    fun provideDateManager(): DateManager {
        return DateManagerImpl()
    }
}