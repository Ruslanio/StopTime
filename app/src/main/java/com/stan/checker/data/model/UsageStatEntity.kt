package com.stan.checker.data.model

data class UsageStatEntity(
    val packageName: String,
    val usageTimestamp: Long,
    val lastUsedTimestamp: Long,
)