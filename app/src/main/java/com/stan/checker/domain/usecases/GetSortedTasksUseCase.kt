package com.stan.checker.domain.usecases


import com.stan.checker.data.repository.TaskRepository
import com.stan.checker.domain.mappers.TaskEntityToTaskMapper
import com.stan.checker.domain.model.Task
import com.stan.checker.domain.model.TaskListSortingResult
import com.stan.checker.domain.model.TaskType
import com.stan.checker.util.date.DateManager
import com.stan.checker.util.date.Formatters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class GetSortedTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskMapper: TaskEntityToTaskMapper,
    private val dateManager: DateManager
) {

    operator fun invoke(): Flow<TaskListSortingResult> {
        return taskRepository.getAllTasksFlow()
            .map { it.map(taskMapper::map) }
            .map { sort(it) }
    }

    private fun sort(tasks: List<Task>): TaskListSortingResult {
        val todayTasks = mutableListOf<Task>()
        val tomorrowTasks = mutableListOf<Task>()
        val expiredTasks = mutableListOf<Task>()
        val perpetualTasks = mutableListOf<Task>()
        val completedTasks = mutableListOf<Task>()
        val nearFutureTasks = mutableMapOf<String, MutableList<Task>>()

        var dateKey: String
        tasks.sortById().forEach { task ->
            when (task.taskType) {
                TaskType.COMPLETED -> completedTasks.add(task)
                TaskType.PERPETUAL -> perpetualTasks.add(task)
                TaskType.EXPIRED -> expiredTasks.add(task)
                TaskType.TODAY -> todayTasks.add(task)
                TaskType.TOMORROW -> tomorrowTasks.add(task)
                TaskType.NEAR_FUTURE -> {
                    dateKey = getDateKey(task.date!!)
                    if (nearFutureTasks[dateKey] != null) {
                        nearFutureTasks[dateKey]?.add(task)
                    } else {
                        nearFutureTasks[dateKey] = mutableListOf(task)
                    }
                }
            }
        }
        return TaskListSortingResult(
            perpetualTasks = perpetualTasks,
            expiredTasks = expiredTasks,
            todayTasks = todayTasks,
            tomorrowTasks = tomorrowTasks,
            completedTasks = completedTasks,
            nearFutureTasks = nearFutureTasks
        )
    }

    private fun getDateKey(date: LocalDate): String {
        return dateManager.parseToString(date, Formatters.Date.uiFriendly)
    }

    private fun List<Task>.sortById() = this.sortedWith { first, second ->
        when {
            first.id > second.id -> 1
            first.id < second.id -> -1
            else -> 0
        }

    }
}