package com.stan.checker.util.date

import com.stan.checker.presentation.model.DateStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

interface DateManager {
    fun getStartOfDayTimeMillis(): Long
    fun getCurrentTimeMillis(): Long
    fun parseToString(date: LocalTime, formatter: DateTimeFormatter): String
    fun parseToTime(string: String, formatter: DateTimeFormatter): LocalTime
    fun parseToString(date: LocalDate, formatter: DateTimeFormatter): String
    fun parseToDate(string: String, formatter: DateTimeFormatter): LocalDate

    fun concatDateAndTime(date: LocalDate?, time: LocalTime?): LocalDateTime?
    fun getTaskDateStatus(date: LocalDate?, time: LocalTime?): DateStatus
    fun isExpired(date: LocalDate?, time: LocalTime?): Boolean
    fun isToday(date: LocalDate?): Boolean
    fun isTomorrow(date: LocalDate?): Boolean
}