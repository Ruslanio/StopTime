package com.stan.checker.data.datasource

import com.stan.checker.data.model.UsageStatEntity

interface UsageDataSource {
    fun queryUsageStats(beginTime: Long, endTime: Long): List<UsageStatEntity>
}