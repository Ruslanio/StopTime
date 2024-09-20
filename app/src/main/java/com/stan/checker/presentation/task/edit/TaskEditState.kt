package com.stan.checker.presentation.task.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.stan.checker.core.mvvm.BaseState
import com.stan.checker.presentation.model.TaskItem

sealed class TaskEditState : BaseState {

    object Loading : TaskEditState()

    object TaskNotFound : TaskEditState()

    data class InitialState(val taskItem: TaskItem) : TaskEditState() {
        var nameValidationError: String? by mutableStateOf(null)
        var descriptionValidationError: String? by mutableStateOf(null)
    }
}