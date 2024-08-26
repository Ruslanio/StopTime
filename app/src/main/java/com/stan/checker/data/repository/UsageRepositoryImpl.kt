package com.stan.checker.data.repository

import com.stan.checker.data.datasource.AppInfoDataSource
import com.stan.checker.data.datasource.UsageDataSource
import com.stan.checker.presentation.model.UsageItem
import com.stan.checker.util.date.DateManager

class UsageRepositoryImpl(
    private val usageDataSource: UsageDataSource,
    private val appInfoDataSource: AppInfoDataSource,
    private val dateManager: DateManager,
) : UsageRepository {

    override fun getAllUsageForToday(): List<UsageItem> {
        val stats = usageDataSource.queryDailyUsageStats(
            dateManager.getStartOfDayTimeMillis(),
            dateManager.getCurrentTimeMillis()
        ).sortedByDescending { it.usageTimestamp }

        return stats.map {
            UsageItem(
                icon = appInfoDataSource.getApplicationIcon(it.packageName),
                name = appInfoDataSource.getApplicationLabel(it.packageName),
                packageName = it.packageName,
                timeInUse = dateManager.mapTimestampsToPrettyString(it.usageTimestamp)
            )
        }
    }
}