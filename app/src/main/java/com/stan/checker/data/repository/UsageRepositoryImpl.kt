package com.stan.checker.data.repository

import android.util.Log
import com.stan.checker.data.datasource.AppInfoDataSource
import com.stan.checker.data.datasource.UsageDataSource
import com.stan.checker.data.model.UsageStatEntity
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

        return stats.map { it.toUsageItem() }
    }

    override fun getCurrentForeground(): UsageItem {
        val end = dateManager.getCurrentTimeMillis()
        val stats = usageDataSource.queryDailyUsageStats(
            dateManager.getStartOfDayTimeMillis(),
            end
        ).sortedByDescending { it.lastUsedTimestamp }
        Log.d("!!!", "getCurrentForeground: end $end")
        val res = stats.first()
        Log.d("!!!", "getCurrentForeground: ${res.packageName}, time: ${res.lastUsedTimestamp}")
        return stats.first().toUsageItem()
    }

    private fun UsageStatEntity.toUsageItem(): UsageItem {
        return UsageItem(
            icon = appInfoDataSource.getApplicationIcon(packageName),
            //TODO: handle missing name properly
            name = appInfoDataSource.getApplicationLabel(packageName) ?: "app was deleted",
            packageName = packageName,
            timeStamp = usageTimestamp,
            timeInUse = dateManager.mapTimestampsToPrettyString(usageTimestamp)
        )
    }
}