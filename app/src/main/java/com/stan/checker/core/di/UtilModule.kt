package com.stan.checker.core.di

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import com.stan.checker.core.alarmmanager.AlarmHelper
import com.stan.checker.core.alarmmanager.AlarmHelperImpl
import com.stan.checker.core.notification.NotificationHelper
import com.stan.checker.core.notification.NotificationHelperImpl
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
    fun providesAlarmHelper(
        @ApplicationContext context: Context,
        alarmManager: AlarmManager
    ): AlarmHelper {
        return AlarmHelperImpl(
            context = context,
            alarmManager = alarmManager
        )
    }

    @Provides
    fun provideNotificationHelper(
        @ApplicationContext context: Context,
        notificationManager: NotificationManager,
        resourceProvider: ResourceProvider
    ): NotificationHelper {
        return NotificationHelperImpl(
            context = context,
            notificationManager = notificationManager,
            resourceProvider = resourceProvider
        )
    }
}