package com.stan.checker.data.datasource

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.os.Build
import com.stan.checker.data.model.UsageStatEntity

class UsageDataSourceImpl(
    private val usageStatsManager: UsageStatsManager
) : UsageDataSource {

    override fun queryDailyUsageStats(beginTime: Long, endTime: Long): List<UsageStatEntity> {

        val events = usageStatsManager.queryEvents(beginTime, endTime)
        var currentEvent: UsageEvents.Event
        val appEventMap = hashMapOf<String, MutableList<UsageEvents.Event>>()
        val resultList = mutableListOf<UsageStatEntity>()

        while (events.hasNextEvent()) {
            currentEvent = UsageEvents.Event()
            events.getNextEvent(currentEvent) // copying next event into this object

            val packageName = currentEvent.packageName
            if (isIndicator(currentEvent)) {
                appEventMap.putIfAbsent(packageName, mutableListOf())
                appEventMap[packageName]?.add(currentEvent)
            }
        }

        appEventMap.entries.forEach {
            var currentAppUsage = 0L

            if (it.value.size > 1) {
                for (i in 0 until it.value.lastIndex) {
                    val first = it.value[i]
                    val second = it.value[i + 1]

                    if (first.isAppStartIndicator() and second.isAppEndIndicator()) {
                        currentAppUsage += second.timeStamp - first.timeStamp
                    }
                }
            }

            if (it.value.first().isAppEndIndicator()) {
                currentAppUsage += it.value.first().timeStamp - beginTime
            }

            if (it.value.last().isAppStartIndicator()) {
                currentAppUsage += endTime - it.value.last().timeStamp
            }

            if (currentAppUsage / 1000 != 0L) {
                resultList.add(
                    UsageStatEntity(
                        packageName = it.key,
                        usageTimestamp = currentAppUsage,
                        lastUsedTimestamp = it.value.last().timeStamp
                    )
                )
            }
        }

        return resultList
    }

    private fun isIndicator(event: UsageEvents.Event) =
        event.isAppStartIndicator() or event.isAppEndIndicator()

    private fun UsageEvents.Event.isAppStartIndicator(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            eventType == UsageEvents.Event.ACTIVITY_RESUMED
        } else {
            eventType == UsageEvents.Event.MOVE_TO_FOREGROUND
        }
    }

    private fun UsageEvents.Event.isAppEndIndicator() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            eventType == UsageEvents.Event.ACTIVITY_PAUSED
        } else {
            eventType == UsageEvents.Event.MOVE_TO_BACKGROUND
        }
}