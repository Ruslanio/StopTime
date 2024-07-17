package com.stan.checker.util.date

import com.stan.checker.presentation.model.DateStatus
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class DateManagerImpl : DateManager {

    override fun parseToDate(string: String, formatter: DateTimeFormatter): LocalDate {
        return LocalDate.parse(string, formatter)
    }

    override fun parseToString(date: LocalDate, formatter: DateTimeFormatter): String {
        return date.format(formatter)
    }

    override fun parseToTime(string: String, formatter: DateTimeFormatter): LocalTime {
        return LocalTime.parse(string, formatter)
    }

    override fun parseToString(date: LocalTime, formatter: DateTimeFormatter): String {
        return date.format(formatter)
    }

    override fun getStartOfDayTimeMillis(): Long {
        return LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toEpochSecond()
            .toMillis()
    }

    override fun getCurrentTimeMillis(): Long {
        return ZonedDateTime.now().toEpochSecond().toMillis()
    }

    override fun isExpired(date: LocalDate?, time: LocalTime?): Boolean {
        return date?.isExpired() == true || isToday(date) && time?.isExpired() == true
    }

    override fun isToday(date: LocalDate?): Boolean {
        val todayDate = LocalDate.now()
        return todayDate.year == date?.year && todayDate.dayOfYear == date.dayOfYear
    }

    override fun isTomorrow(date: LocalDate?): Boolean {
        val todayDate = LocalDate.now()
        return todayDate.plusDays(1).dayOfYear == date?.dayOfYear
    }

    override fun getTaskDateStatus(date: LocalDate?, time: LocalTime?): DateStatus {
        return when {
            date == null -> DateStatus.Perpetual
            isExpired(date, time) -> DateStatus.Expired(
                date = parseToString(date, Formatters.Date.uiFriendly),
                time = time?.let { parseToString(it, Formatters.Time.uiFriendly) }
            )

            else -> DateStatus.Regular(
                date = parseToString(date, Formatters.Date.uiFriendly),
                time = time?.let { parseToString(it, Formatters.Time.uiFriendly) }
            )
        }
    }

    override fun concatDateAndTime(date: LocalDate?, time: LocalTime?): LocalDateTime? {
        return when {
            date != null && time != null -> LocalDateTime.of(date, time)
            date != null && time == null -> date.atStartOfDay()
            else -> null
        }
    }

    private fun LocalDate.isExpired(): Boolean {
        val todayDate = LocalDate.now()
        return todayDate.isAfter(this)
    }

    private fun LocalTime.isExpired(): Boolean {
        val currentTime = LocalTime.now()
        return currentTime.isAfter(this)
    }

    private fun Long.toMillis() = this * 1000
}