package com.stan.checker.core.di

import android.content.Context
import androidx.room.Room
import com.stan.checker.core.database.CheckerDatabase
import com.stan.checker.core.database.converters.LocalDateConverter
import com.stan.checker.core.database.converters.LocalTimeConverter
import com.stan.checker.core.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        dateConverter: LocalDateConverter,
        timeConverter: LocalTimeConverter
    ): CheckerDatabase {
        return Room.databaseBuilder(
            context,
            CheckerDatabase::class.java,
            "checker.db"
        ).addTypeConverter(dateConverter)
            .addTypeConverter(timeConverter)
            .build()
    }

    @Provides
    @Singleton
    fun provideTasksDao(database: CheckerDatabase): TaskDao {
        return database.tasksDao()
    }

}