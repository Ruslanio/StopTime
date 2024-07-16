package com.stan.checker.core.di

import com.stan.checker.util.DateManager
import com.stan.checker.util.DateManagerImpl
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