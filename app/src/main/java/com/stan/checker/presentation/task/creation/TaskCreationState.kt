package com.stan.checker.presentation.task.creation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.stan.checker.core.mvvm.BaseState
import java.time.LocalDate
import java.time.LocalTime

sealed class TaskCreationState : BaseState {
    var isLoading: Boolean by mutableStateOf(false)
    var nameFieldError: String? by mutableStateOf(null)
    var descriptionFieldError: String? by mutableStateOf(null)

    object SuccessfulCreation : TaskCreationState()

    data class CreationInProgress(
        val taskName: String,
        val taskDescription: String,
        val taskDate: LocalDate?,
        val taskTime: LocalTime?
    ) : TaskCreationState()
}