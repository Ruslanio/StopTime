package com.stan.checker.presentation.task.list

import androidx.annotation.StringRes
import com.stan.checker.R
import com.stan.checker.core.mvvm.BaseState
import com.stan.checker.ui.BaseListItem

sealed class TaskListState : BaseState {

    fun isTaskCreationPossible() =
        this is HasTasksState || this is NoTasks

    data object Loading : TaskListState()

    data object NoTasks : TaskListState()

    data class HasTasksState(
        val taskList: List<BaseListItem> = emptyList()
    ) : TaskListState()

    data class Error(
        @StringRes val errorMessageId: Int = R.string.error_generic_message
    ) : TaskListState()
}

