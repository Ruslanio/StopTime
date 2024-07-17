package com.stan.checker.presentation.task.list

import androidx.lifecycle.viewModelScope
import com.stan.checker.R
import com.stan.checker.core.mvvm.BaseViewModel
import com.stan.checker.data.repository.TaskRepository
import com.stan.checker.domain.model.Task
import com.stan.checker.domain.model.TaskListSortingResult
import com.stan.checker.domain.usecases.GetSortedTasksUseCase
import com.stan.checker.presentation.task.list.mappers.TaskToTaskItemMapper
import com.stan.checker.ui.BaseListItem
import com.stan.checker.ui.components.list.headlines.HeadlineItem
import com.stan.checker.ui.components.list.headlines.SmallHeadlineItem
import com.stan.checker.util.resourse.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskToTaskItemMapper: TaskToTaskItemMapper,
    private val resourceProvider: ResourceProvider,
    private val getSortedTasksUseCase: GetSortedTasksUseCase
) : BaseViewModel<TaskListState>() {

    override fun getInitialState() = TaskListState.Loading

    init {
        viewModelScope.launch {
            getSortedTasksUseCase()
                .map(::createUiModelList)
                .catch { exception ->
                    exception.printStackTrace()
                    mutableUiState.update { TaskListState.Error() }
                }
                .collect(::handleCollectedTasks)
        }
    }

    private fun handleCollectedTasks(sortedTasksList: List<BaseListItem>) {
        if (sortedTasksList.isNotEmpty()) {
            mutableUiState.update { TaskListState.HasTasksState(sortedTasksList) }
        } else {
            mutableUiState.update { TaskListState.NoTasks }
        }
    }

    fun tryToRefresh() {
        // TODO
    }

    fun onTaskCompletionStatusChange(taskId: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            taskRepository.updateTaskStatusById(taskId, isCompleted)
        }
    }

    fun onDeleteTask(taskId: Int) {
        viewModelScope.launch {
            taskRepository.deleteTaskById(taskId)
        }
    }

    private fun createUiModelList(
        taskListSortingResult: TaskListSortingResult
    ): List<BaseListItem> {

        return with(taskListSortingResult) {
            mutableListOf<BaseListItem>().apply {
                addHeadLinedPart(
                    resourceProvider.getString(R.string.task_list_completed),
                    completedTasks
                )

                addHeadLinedPart(
                    resourceProvider.getString(R.string.task_list_expired),
                    expiredTasks
                )

                addHeadLinedPart(
                    resourceProvider.getString(R.string.task_list_perpetual),
                    perpetualTasks
                )

                addHeadLinedPart(
                    resourceProvider.getString(R.string.task_list_today),
                    todayTasks
                )

                addHeadLinedPart(
                    resourceProvider.getString(R.string.task_list_tomorrow),
                    tomorrowTasks
                )

                addNearFuturePart(nearFutureTasks)
            }
        }
    }

    private fun MutableList<BaseListItem>.addHeadLinedPart(
        headlineTitle: String,
        tasks: List<Task>
    ) {
        if (tasks.isNotEmpty()) {
            addAll(
                listOf(
                    HeadlineItem(headlineTitle),
                ) + tasks.map(taskToTaskItemMapper::map)
            )
        }
    }

    private fun MutableList<BaseListItem>.addNearFuturePart(
        nearFutureTasks: Map<String, List<Task>>
    ) {
        if (nearFutureTasks.isNotEmpty()) {
            add(HeadlineItem(resourceProvider.getString(R.string.task_list_nearest_future)))
            nearFutureTasks.keys.forEach { date ->
                addAll(
                    listOf(
                        SmallHeadlineItem(date)
                    ) + nearFutureTasks[date]!!.map(taskToTaskItemMapper::map)
                )
            }
        }
    }
}