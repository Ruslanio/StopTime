package com.stan.checker.core.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import com.stan.checker.core.ext.toMinutesMillis

class AlarmHelperImpl(
    private val context: Context,
    private val alarmManager: AlarmManager
) : AlarmHelper {

    override fun setAlarm() {
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val delayMinutes = 5L
        alarmManager.setExact(
            AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + delayMinutes.toMinutesMillis(),
            pendingIntent
        )
    }
}