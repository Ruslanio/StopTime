package com.stan.checker.core.di

import android.content.Context
import androidx.work.WorkManager
import com.stan.checker.core.workmanager.WorkManagerHelper
import com.stan.checker.core.workmanager.WorkManagerHelperImpl
import com.stan.checker.util.date.DateManager
import com.stan.checker.util.date.DateManagerImpl
import com.stan.checker.util.resourse.ResourceProvider
import com.stan.checker.util.resourse.ResourceProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object UtilModule {

    @Provides
    @Singleton
    fun provideResourceProvider(
        @ApplicationContext context: Context
    ): ResourceProvider {
        return ResourceProviderImpl(context)
    }

    @Provides
    fun provideDateManager(
        resourceProvider: ResourceProvider
    ): DateManager {
        return DateManagerImpl(resourceProvider)
    }

    @Provides
    fun provideWorkManagerHelper(
        workManager: WorkManager
    ): WorkManagerHelper {
        return WorkManagerHelperImpl(workManager)
    }
}