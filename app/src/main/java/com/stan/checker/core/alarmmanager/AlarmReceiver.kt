package com.stan.checker.core.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.stan.checker.domain.usecases.CheckCurrentAppIsOverLimitUsaCase
import com.stan.checker.domain.usecases.CreateExceededLimitNotificationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var checkCurrentAppIsOverLimitUsaCase: CheckCurrentAppIsOverLimitUsaCase

    @Inject
    lateinit var createExceededLimitNotificationUseCase: CreateExceededLimitNotificationUseCase

    @Inject
    lateinit var alarmHelper: AlarmHelper

    override fun onReceive(context: Context?, intent: Intent?) {
        val exceededApp = checkCurrentAppIsOverLimitUsaCase.invoke()

        exceededApp?.let {
            goAsync {
                createExceededLimitNotificationUseCase.invoke(it)
            }
        }
        alarmHelper.setAlarm()
    }
}

private fun BroadcastReceiver.goAsync(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit
) {
    val pendingResult = goAsync()
    @OptIn(DelicateCoroutinesApi::class) // Must run globally; there's no teardown callback.
    GlobalScope.launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}