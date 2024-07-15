package com.stan.checker.data.repository

import android.app.usage.UsageStatsManager
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.stan.checker.usage.DateManager
import com.stan.checker.usage.NamingHelper
import com.stan.checker.usage.model.Usage

class UsageRepositoryImpl(
    private val usageManager: UsageStatsManager,
    private val packageManager: PackageManager,
    private val dateManager: DateManager,
    private val namingHelper: NamingHelper
) : UsageRepository {

    override fun getAllUsageForToday(): List<Usage> {
        val stats = usageManager.queryAndAggregateUsageStats(
            dateManager.getStartOfDayTimeMillis(),
            dateManager.getCurrentTimeMillis()
        ).values.toList()
            .sortedByDescending { it.totalTimeInForeground }
            .filter { it.totalTimeInForeground / 1000 != 0L }

        return stats.map {
            Usage(
                icon = getIconDrawable(it.packageName),
                name = namingHelper.resolvePackageName(it.packageName) ?: it.packageName,
                packageName = it.packageName,
                timeInUse = it.totalTimeInForeground
            )
        }
    }

    private fun getIconDrawable(packageName: String): Drawable? {
        val result = runCatching { packageManager.getApplicationIcon(packageName) }
        return result.getOrNull()
    }

}