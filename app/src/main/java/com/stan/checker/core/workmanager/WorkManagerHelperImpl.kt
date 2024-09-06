package com.stan.checker.core.workmanager

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

private const val KEY_UNIQUE_USAGE_CHECK = "key_unique_usage_change"

class WorkManagerHelperImpl(
    private val workManager: WorkManager
) : WorkManagerHelper {

    override fun startUsageMonitoring() {
        val request = PeriodicWorkRequestBuilder<UsageWorker>(
            repeatInterval = 15.minutes.toJavaDuration(),
            flexTimeInterval = 5.minutes.toJavaDuration()
        ).build()

        workManager.enqueueUniquePeriodicWork(
            KEY_UNIQUE_USAGE_CHECK,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            request
        )
    }
}