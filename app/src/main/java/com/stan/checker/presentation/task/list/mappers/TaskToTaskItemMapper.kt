package com.stan.checker.presentation.task.list.mappers

import com.stan.checker.domain.model.Task
import com.stan.checker.presentation.model.TaskItem
import com.stan.checker.util.date.DateManager
import javax.inject.Inject

class TaskToTaskItemMapper @Inject constructor(
    private val dateManager: DateManager
) {

    fun map(item: Task) = with(item) {
        TaskItem(
            id = id,
            title = title,
            description = description,
            isCompleted = isCompleted(),
            date = date,
            time = time,
            dateStatus = dateManager.getTaskDateStatus(
                item.date, item.time
            )
        )
    }
}