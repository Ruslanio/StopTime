package com.stan.checker.util

interface DateManager {
    fun getStartOfDayTimeMillis(): Long
    fun getCurrentTimeMillis(): Long
}