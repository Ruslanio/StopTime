package com.stan.checker.core.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UsageWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val message = "bum ${System.currentTimeMillis()}"
        Log.v("WORK!!!", message)
        return Result.success()
    }

}