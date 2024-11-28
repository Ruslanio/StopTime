package com.stan.checker.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Task(
    val id: Int,
    val title: String,
    val description: String?,
    val date: LocalDate?,
    val time: LocalTime?,
    val taskType: TaskType
) {
    fun isCompleted() = taskType == TaskType.COMPLETED
}

enum class TaskType {
    TODAY, TOMORROW, EXPIRED, COMPLETED, PERPETUAL, NEAR_FUTURE
}