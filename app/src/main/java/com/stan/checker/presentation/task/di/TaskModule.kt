package com.stan.checker.presentation.task.di

import com.stan.checker.domain.mappers.TaskEntityToTaskMapper
import com.stan.checker.presentation.task.list.mappers.TaskToTaskItemMapper
import com.stan.checker.util.date.DateManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object TaskModule {

    @Provides
    @ViewModelScoped
    fun provideTaskItemMapper(
        dateManager: DateManager
    ): TaskToTaskItemMapper {
        return TaskToTaskItemMapper(dateManager)
    }

    @Provides
    @ViewModelScoped
    fun provideTaskEntityMapper(): TaskEntityToTaskMapper {
        return TaskEntityToTaskMapper()
    }
}