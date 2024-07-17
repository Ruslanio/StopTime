package com.stan.checker.domain.model

data class TaskListSortingResult(
    val completedTasks: List<Task>,
    val perpetualTasks: List<Task>,
    val expiredTasks: List<Task>,
    val todayTasks: List<Task>,
    val tomorrowTasks: List<Task>,
    val nearFutureTasks: Map<String, List<Task>>
)