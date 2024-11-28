package com.stan.checker.domain.usecases

import com.stan.checker.data.repository.TaskRepository
import com.stan.checker.domain.mappers.TaskEntityToTaskMapper
import com.stan.checker.domain.model.Task
import com.stan.checker.domain.model.TaskType
import javax.inject.Inject

class GetRelevantTasksUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskMapper: TaskEntityToTaskMapper
) {

    suspend operator fun invoke(): List<Task> {
        return taskRepository.getAllTasks()
            .map(taskMapper::map)
            .filter { it.taskType == TaskType.TODAY }
    }
}