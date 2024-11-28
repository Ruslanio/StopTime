package com.stan.checker.core.ext

fun Long.toMinutesMillis(): Long {
    return this * 60 * 1000
}