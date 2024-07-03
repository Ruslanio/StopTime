package com.stan.checker.usage

import android.app.usage.UsageStatsManager
import com.stan.checker.usage.model.Usage

class UsageProviderImpl(
    private val usageManager: UsageStatsManager,
    private val dateManager: DateManager,
    private val namingHelper: NamingHelper
) : UsageProvider {

    override fun getAllUsageForToday(): List<Usage> {
        val stats = usageManager.queryAndAggregateUsageStats(
            dateManager.getStartOfDayTimeMillis(),
            dateManager.getCurrentTimeMillis()
        ).values.toList()
            .sortedByDescending { it.totalTimeInForeground }
            .filter { it.totalTimeInForeground / 1000 != 0L }

        return stats.map {
            Usage(
                name = namingHelper.resolvePackageName(it.packageName) ?: it.packageName,
                packageName = it.packageName,
                timeInUse = it.totalTimeInForeground
            )
        }
    }

}