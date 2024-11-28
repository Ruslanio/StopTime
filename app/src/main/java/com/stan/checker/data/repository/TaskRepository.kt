package com.stan.checker.data.repository

import com.stan.checker.core.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface TaskRepository {

    fun getAllTasksFlow(): Flow<List<TaskEntity>>

    fun getTaskById(taskId: Int): Flow<TaskEntity>

    suspend fun createTask(name: String, description: String, date: LocalDate?, time: LocalTime?)

    suspend fun updateTaskStatusById(taskId: Int, isCompleted: Boolean)

    suspend fun updateTaskNameById(taskId: Int, name: String)

    suspend fun updateDescriptionNameById(taskId: Int, description: String)

    suspend fun clearDateById(taskId: Int)

    suspend fun updateDateById(taskId: Int, date: LocalDate)

    suspend fun updateTimeById(taskId: Int, time: LocalTime)

    suspend fun clearTimeById(taskId: Int)

    suspend fun deleteTaskById(taskId: Int)
    suspend fun getAllTasks(): List<TaskEntity>
}