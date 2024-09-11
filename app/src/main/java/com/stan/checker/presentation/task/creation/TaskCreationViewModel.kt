package com.stan.checker.presentation.task.creation

import androidx.lifecycle.viewModelScope
import com.stan.checker.core.mvvm.BaseViewModel
import com.stan.checker.data.repository.TaskRepository
import com.stan.checker.presentation.task.validation.TaskDescriptionValidator
import com.stan.checker.presentation.task.validation.TaskTitleValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

interface TaskCreationActions {
    fun onNameChanged(name: String)
    fun onDescriptionChange(description: String)
    fun onClearDate()
    fun onChangeDate(date: LocalDate, time: LocalTime?)
    fun onSaveClicked()
}

@HiltViewModel
class TaskCreationViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskTitleValidator: TaskTitleValidator,
    private val taskDescriptionValidator: TaskDescriptionValidator
) : BaseViewModel<TaskCreationState>(), TaskCreationActions {

    override fun getInitialState() = TaskCreationState.CreationInProgress(
        taskName = "",
        taskDescription = "",
        taskDate = null,
        taskTime = null
    )

    override fun onNameChanged(name: String) {
        updateStateAs<TaskCreationState.CreationInProgress> { current ->
            current.copy(
                taskName = name
            )
        }
    }

    override fun onDescriptionChange(description: String) {
        updateStateAs<TaskCreationState.CreationInProgress> { current ->
            current.copy(
                taskDescription = description
            )
        }
    }

    override fun onClearDate() {
        updateStateAs<TaskCreationState.CreationInProgress> { current ->
            current.copy(
                taskDate = null,
                taskTime = null
            )
        }
    }

    override fun onChangeDate(date: LocalDate, time: LocalTime?) {
        updateStateAs<TaskCreationState.CreationInProgress> { current ->
            current.copy(
                taskDate = date,
                taskTime = time
            )
        }
    }

    override fun onSaveClicked() {
        val currentState = mutableUiState.value
        if (currentState is TaskCreationState.CreationInProgress) {
            with(currentState) {
                if (validateTaskInfo(taskName, taskDescription)) {
                    saveTask(taskName, taskDescription, taskDate, taskTime)
                }
            }
        }
    }

    private fun saveTask(name: String, description: String, date: LocalDate?, time: LocalTime?) {
        viewModelScope.launch {
            mutableUiState.update {
                it.also { it.isLoading = true }
            }
            taskRepository.createTask(
                name,
                description,
                date,
                time
            )
            mutableUiState.update { TaskCreationState.SuccessfulCreation }
        }
    }

    private fun validateTaskInfo(name: String, description: String): Boolean {
        val nameResult = taskTitleValidator.validate(name)
        val descriptionResult = taskDescriptionValidator.validate(description)
        return when {
            nameResult.isSuccess() && descriptionResult.isSuccess() -> true
            else -> {
                mutableUiState.update {
                    it.also {
                        it.nameFieldError = nameResult.getErrorMessage()
                        it.descriptionFieldError = descriptionResult.getErrorMessage()
                    }
                }
                false
            }
        }
    }
}