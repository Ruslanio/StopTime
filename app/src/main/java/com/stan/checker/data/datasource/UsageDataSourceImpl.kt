package com.stan.checker.data.datasource

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.app.usage.UsageStatsManager.INTERVAL_DAILY
import com.stan.checker.data.model.UsageStatEntity

class UsageDataSourceImpl(
    private val usageStatsManager: UsageStatsManager
) : UsageDataSource {

    override fun queryDailyUsageStats(beginTime: Long, endTime: Long): List<UsageStatEntity> {
        return usageStatsManager.queryUsageStats(INTERVAL_DAILY, beginTime, endTime)
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