package com.stan.checker.usage.model

data class Usage(
    val name: String?,
    val packageName: String,
    val timeInUse: Long
)