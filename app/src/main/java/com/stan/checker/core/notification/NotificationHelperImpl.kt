package com.stan.checker.core.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.toBitmap
import com.stan.checker.R
import com.stan.checker.util.resourse.ResourceProvider

private const val CHANNEL_ID = "supa_dupa_channel_id"

class NotificationHelperImpl(
    private val context: Context,
    private val notificationManager: NotificationManager,
    private val resourceProvider: ResourceProvider
) : NotificationHelper {

    override fun init() {
        createNotificationChannel(CHANNEL_ID, R.string.channel_name, R.string.channel_description)
    }

    override fun sendNotification(
        tag: String,
        title: String,
        subTitle: String?,
        bodyText: String?,
        image: Drawable?,
    ) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_notification)
            setContentTitle(title)
            subTitle?.let {
                setContentText(it)
            }
            image?.let {
                setLargeIcon(it.toBitmap())
            }
            bodyText?.let {
                setStyle(NotificationCompat.BigTextStyle().bigText(bodyText))
            }
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }

        Log.d("!!!", "sendNotification: ${builder.build().channelId}")

        notificationManager.notify("$title-$subTitle", 1, builder.build())
    }

    private fun createNotificationChannel(
        channelId: String,
        @StringRes channelNameResId: Int,
        @StringRes channelDescriptionResId: Int
    ) {
        val name = resourceProvider.getString(channelNameResId)
        val descriptionText = resourceProvider.getString(channelDescriptionResId)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }
}