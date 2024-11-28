package com.stan.checker.core.notification

import android.graphics.drawable.Drawable

private const val DEFAULT_TAG = "checker_notification_tag"

interface NotificationHelper {
    fun init()
    fun sendNotification(
        tag: String = DEFAULT_TAG,
        title: String,
        subTitle: String? = null,
        bodyText: String? = null,
        image: Drawable? = null
    )
}