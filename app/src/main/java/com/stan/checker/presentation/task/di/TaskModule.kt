package com.stan.checker.presentation.task.di

import com.stan.checker.presentation.task.list.mappers.TaskToTaskItemMapper
import com.stan.checker.presentation.task.validation.TaskDescriptionValidator
import com.stan.checker.presentation.task.validation.TaskDescriptionValidatorImpl
import com.stan.checker.presentation.task.validation.TaskTitleValidator
import com.stan.checker.presentation.task.validation.TaskTitleValidatorImpl
import com.stan.checker.util.date.DateManager
import com.stan.checker.util.resourse.ResourceProvider
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
    fun provideTaskNameValidator(
        resourceProvider: ResourceProvider
    ): TaskTitleValidator {
        return TaskTitleValidatorImpl(resourceProvider)
    }

    @Provides
    @ViewModelScoped
    fun provideTaskDescriptionValidator(
        resourceProvider: ResourceProvider
    ): TaskDescriptionValidator {
        return TaskDescriptionValidatorImpl(resourceProvider)
    }
}