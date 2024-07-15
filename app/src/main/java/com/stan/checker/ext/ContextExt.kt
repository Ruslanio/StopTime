package com.stan.checker.ext

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

tailrec fun Context?.activity(): Activity? = this as? Activity
    ?: (this as? ContextWrapper)?.baseContext?.activity()

fun Context.requireActivity(): Activity {
    return this.activity()
        ?: throw IllegalArgumentException("This Context does not contain activity")
}

fun Context.checkPackagesPermission(): Boolean {
    val appOps = this.getSystemService(Context.APP_OPS_SERVICE) as android.app.AppOpsManager
    val mode = appOps.checkOpNoThrow(
        android.app.AppOpsManager.OPSTR_GET_USAGE_STATS,
        android.os.Process.myUid(), this.packageName
    )
    return mode == android.app.AppOpsManager.MODE_ALLOWED
}