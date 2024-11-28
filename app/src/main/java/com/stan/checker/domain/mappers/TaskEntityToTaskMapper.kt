package com.stan.checker.domain.mappers

import com.stan.checker.core.database.entities.TaskEntity
import com.stan.checker.domain.model.Task
import com.stan.checker.domain.model.TaskType
import com.stan.checker.util.date.DateManager
import javax.inject.Inject

class TaskEntityToTaskMapper @Inject constructor(
    private val dateManager: DateManager
) {

    fun map(item: TaskEntity) = with(item) {
        Task(
            id = id,
            description = description,
            title = title,
            date = date,
            time = time,
            taskType = defineTaskType(item)
        )
    }

    private fun defineTaskType(item: TaskEntity): TaskType =
        when {
            item.isCompleted -> TaskType.COMPLETED
            item.date == null -> TaskType.PERPETUAL
            dateManager.isExpired(item.date, item.time) -> TaskType.EXPIRED
            dateManager.isToday(item.date) -> TaskType.TODAY
            dateManager.isTomorrow(item.date) -> TaskType.TOMORROW
            else -> TaskType.NEAR_FUTURE
        }
}