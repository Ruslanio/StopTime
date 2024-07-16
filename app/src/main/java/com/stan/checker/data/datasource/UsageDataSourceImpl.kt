package com.stan.checker.data.datasource

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import com.stan.checker.data.model.UsageStatEntity

class UsageDataSourceImpl(
    private val usageStatsManager: UsageStatsManager
) : UsageDataSource {

    override fun queryUsageStats(beginTime: Long, endTime: Long): List<UsageStatEntity> {
        return usageStatsManager.queryAndAggregateUsageStats(beginTime, endTime).values
            .filter { it.totalTimeInForeground / 1000 != 0L }
            .map { it.toEntity() }
    }

    private fun UsageStats.toEntity(): UsageStatEntity {
        return UsageStatEntity(
            packageName = packageName,
            usageTimestamp = totalTimeInForeground
        )
    }
}