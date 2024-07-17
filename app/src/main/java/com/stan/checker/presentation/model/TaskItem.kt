package com.stan.checker.presentation.model

import com.stan.checker.ui.BaseListItem
import java.time.LocalDate
import java.time.LocalTime

data class TaskItem(
    val id: Int,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val date: LocalDate?,
    val time: LocalTime?,
    val dateStatus: DateStatus
) : BaseListItem

sealed class DateStatus {
    data object Perpetual : DateStatus()
    data class Expired(val date: String, val time: String?) : DateStatus()
    data class Regular(val date: String, val time: String?) : DateStatus()
}
