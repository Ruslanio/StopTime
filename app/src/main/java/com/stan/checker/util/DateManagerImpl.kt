package com.stan.checker.util

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class DateManagerImpl : DateManager {

    override fun getStartOfDayTimeMillis(): Long {
        return LocalDate.now()
            .atStartOfDay(ZoneId.systemDefault())
            .toEpochSecond()
            .toMillis()
    }

    override fun getCurrentTimeMillis(): Long {
        return ZonedDateTime.now().toEpochSecond().toMillis()
    }

    private fun Long.toMillis() = this * 1000
}