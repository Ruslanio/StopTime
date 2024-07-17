package com.stan.checker.domain.mappers

import com.stan.checker.core.database.entities.TaskEntity
import com.stan.checker.domain.model.Task
import javax.inject.Inject

class TaskEntityToTaskMapper @Inject constructor() {

    fun map(item: TaskEntity) = with(item) {
        Task(
            id = id,
            description = description,
            title = title,
            date = date,
            time = time,
            isCompleted = isCompleted
        )
    }

    fun unmap(item: Task) = with(item) {
        TaskEntity(
            id = id,
            description = description,
            title = title,
            date = date,
            time = time,
            isCompleted = isCompleted
        )
    }
}