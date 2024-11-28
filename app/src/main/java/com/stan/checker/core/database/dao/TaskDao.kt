package com.stan.checker.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stan.checker.core.database.entities.TaskEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: TaskEntity)

    @Update
    suspend fun update(task: TaskEntity)

    @Query("UPDATE tasks SET isCompleted =:isCompleted WHERE id =:taskId")
    suspend fun updateIsCompletedById(taskId: Int, isCompleted: Boolean)

    @Query("UPDATE tasks SET title =:title WHERE id =:taskId")
    suspend fun updateTitleById(taskId: Int, title: String)

    @Query("UPDATE tasks SET description =:description WHERE id =:taskId")
    suspend fun updateDescriptionById(taskId: Int, description: String)

    @Query("UPDATE tasks SET date = null, time = null WHERE id =:taskId")
    suspend fun clearDateById(taskId: Int)

    @Query("UPDATE tasks SET date = :date WHERE id =:taskId")
    suspend fun updateDateById(taskId: Int, date: LocalDate)

    @Query("UPDATE tasks SET time = :time WHERE id =:taskId")
    suspend fun updateTimeById(taskId: Int, time: LocalTime)

    @Query("UPDATE tasks SET time = null WHERE id =:taskId")
    suspend fun clearTimeById(taskId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg tasks: TaskEntity)

    @Delete
    suspend fun delete(task: TaskEntity)

    @Query("DELETE FROM tasks WHERE id =:taskId")
    suspend fun deleteById(taskId: Int)

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun queryById(taskId: Int): Flow<TaskEntity>

    @Query("SELECT * FROM tasks")
    fun queryAllTasksFlow(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks")
    suspend fun queryAllTasks(): List<TaskEntity>
}