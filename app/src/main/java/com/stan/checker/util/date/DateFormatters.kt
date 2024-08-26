package com.stan.checker.util.date

import java.time.format.DateTimeFormatter

object Formatters {
    object Date {
        val dbConverter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        val uiFriendly = DateTimeFormatter.ofPattern("dd MMMM yyyy")
    }

    object Time {
        val dbConverter = DateTimeFormatter.ofPattern("HH:mm")
        val uiFriendly = DateTimeFormatter.ofPattern("HH:mm") }
}

