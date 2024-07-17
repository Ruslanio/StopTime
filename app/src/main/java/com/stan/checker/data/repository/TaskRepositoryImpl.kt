package com.stan.checker.data.repository

import com.stan.checker.core.database.dao.TaskDao
import com.stan.checker.core.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<TaskEntity>> {
        return taskDao.queryAllTasks()
    }

    override fun getTaskById(taskId: Int): Flow<TaskEntity> {
        return taskDao.queryById(taskId)
    }

    override suspend fun createTask(
        name: String,
        description: String,
        date: LocalDate?,
        time: LocalTime?
    ) {
        taskDao.insert(
            TaskEntity(
                title = name,
                description = description.ifEmpty { null },
                date = date,
                time = time,
                isCompleted = false
            )
        )
    }

    override suspend fun updateTaskStatusById(taskId: Int, isCompleted: Boolean) {
        taskDao.updateIsCompletedById(taskId, isCompleted)
    }

    override suspend fun updateDescriptionNameById(taskId: Int, description: String) {
        taskDao.updateDescriptionById(taskId, description)
    }

    override suspend fun updateTaskNameById(taskId: Int, name: String) {
        taskDao.updateTitleById(taskId, name)
    }

    override suspend fun updateDateById(taskId: Int, date: LocalDate) {
        taskDao.updateDateById(taskId, date)
    }

    override suspend fun clearDateById(taskId: Int) {
        taskDao.clearDateById(taskId)
    }

    override suspend fun updateTimeById(taskId: Int, time: LocalTime) {
        taskDao.updateTimeById(taskId, time)
    }

    override suspend fun clearTimeById(taskId: Int) {
        taskDao.clearTimeById(taskId)
    }

    override suspend fun deleteTaskById(taskId: Int) {
        taskDao.deleteById(taskId)
    }

}