package com.stan.checker.data.repository

import com.stan.checker.data.datasource.AppInfoDataSource
import com.stan.checker.data.datasource.UsageDataSource
import com.stan.checker.usage.DateManager
import com.stan.checker.usage.model.Usage

class UsageRepositoryImpl(
    private val usageDataSource: UsageDataSource,
    private val appInfoDataSource: AppInfoDataSource,
    private val dateManager: DateManager,
) : UsageRepository {

    override fun getAllUsageForToday(): List<Usage> {
        val stats = usageDataSource.queryUsageStats(
            dateManager.getStartOfDayTimeMillis(),
            dateManager.getCurrentTimeMillis()
        ).sortedByDescending { it.usageTimestamp }

        return stats.map {
            Usage(
                icon = appInfoDataSource.getApplicationIcon(it.packageName),
                name = appInfoDataSource.getApplicationLabel(it.packageName),
                packageName = it.packageName,
                timeInUse = it.usageTimestamp
            )
        }
    }
}