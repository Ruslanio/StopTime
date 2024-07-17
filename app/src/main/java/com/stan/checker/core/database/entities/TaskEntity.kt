package com.stan.checker.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "date")
    val date: LocalDate?,
    @ColumnInfo(name = "time")
    val time: LocalTime?,
    @ColumnInfo(name = "isCompleted")
    val isCompleted: Boolean
)