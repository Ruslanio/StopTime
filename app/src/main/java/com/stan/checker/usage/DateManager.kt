package com.stan.checker.usage

interface DateManager {
    fun getStartOfDayTimeMillis(): Long
    fun getCurrentTimeMillis(): Long
}