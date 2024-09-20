package com.stan.checker.presentation.task.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.stan.checker.core.mvvm.BaseViewModel
import com.stan.checker.data.repository.TaskRepository
import com.stan.checker.domain.mappers.TaskEntityToTaskMapper
import com.stan.checker.presentation.task.list.mappers.TaskToTaskItemMapper
import com.stan.checker.presentation.task.validation.TaskDescriptionValidator
import com.stan.checker.presentation.task.validation.TaskTitleValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

interface TaskEditingActions {
    fun onNameChanged(newName: String)
    fun onDescriptionChanged(description: String)
    fun onClearDateFromTaskClick()
    fun onNewDatePicked(newDate: LocalDate)
    fun onNewTimePicked(newTime: LocalTime?)
    fun onTimeCleared()
    fun onIsCompletedChanged(isCompleted: Boolean)
}

@HiltViewModel
class TaskEditViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskItemMapper: TaskToTaskItemMapper,
    private val taskEntityToTaskMapper: TaskEntityToTaskMapper,
    private val taskTitleValidator: TaskTitleValidator,
    private val taskDescriptionValidator: TaskDescriptionValidator,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<TaskEditState>(), TaskEditingActions {

    private val taskEditArgs = TaskEditArguments(savedStateHandle)

    override fun getInitialState() = TaskEditState.Loading

    private var initialTaskName: String? = null
    private var initialTaskDescription: String? = null

    init {
        viewModelScope.launch {
            taskRepository.getTaskById(taskEditArgs.taskId)
                // TODO figure out model levels and get rid of this
                .map { taskEntityToTaskMapper.map(it) }
                .map { taskItemMapper.map(it) }
                .collect { taskItem ->
                    mutableUiState.update {
                        TaskEditState.InitialState(taskItem)
                    }
                    when {
                        initialTaskName == null -> initialTaskName = taskItem.title
                        initialTaskDescription == null -> initialTaskDescription =
                            taskItem.description
                        else -> Unit
                    }
                }
        }
    }

    override fun onNameChanged(newName: String) {
        if (validateTaskName(newName)) {
            viewModelScope.launch {
                taskRepository.updateTaskNameById(taskEditArgs.taskId, newName)
            }
        } else {
            initialTaskName?.let {
                viewModelScope.launch {
                    taskRepository.updateTaskNameById(taskEditArgs.taskId, it)
                }
            }
        }
    }

    override fun onDescriptionChanged(description: String) {
        if (validateTaskDescription(description)) {
            viewModelScope.launch {
                taskRepository.updateDescriptionNameById(taskEditArgs.taskId, description)
            }
        } else {
            initialTaskDescription?.let {
                viewModelScope.launch {
                    taskRepository.updateTaskNameById(taskEditArgs.taskId, it)
                }
            }
        }
    }

    override fun onClearDateFromTaskClick() {
        viewModelScope.launch {
            taskRepository.clearDateById(taskEditArgs.taskId)
        }
    }

    override fun onNewDatePicked(newDate: LocalDate) {
        viewModelScope.launch {
            taskRepository.updateDateById(taskEditArgs.taskId, newDate)
        }
    }

    override fun onNewTimePicked(newTime: LocalTime?) {
        newTime?.let {
            viewModelScope.launch {
                taskRepository.updateTimeById(taskEditArgs.taskId, it)
            }
        }
    }

    override fun onTimeCleared() {
        viewModelScope.launch {
            taskRepository.clearTimeById(taskEditArgs.taskId)
        }
    }

    override fun onIsCompletedChanged(isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.updateTaskStatusById(taskEditArgs.taskId, isCompleted)
        }
    }

    private fun validateTaskDescription(description: String): Boolean {
        val result = taskDescriptionValidator.validate(description)
        mutableUiState.update {
            (it as TaskEditState.InitialState).apply {
                descriptionValidationError = result.getErrorMessage()
            }
        }
        return result.isSuccess()
    }

    private fun validateTaskName(name: String): Boolean {
        val result = taskTitleValidator.validate(name)
        mutableUiState.update {
            (it as TaskEditState.InitialState).apply {
                nameValidationError = result.getErrorMessage()
            }
        }
        return result.isSuccess()
    }
}