package com.stan.checker.ext

fun String.appendIfNotNull(string: String?): String {
    return if (string != null) {
        "$this $string"
    } else {
        this
    }
}