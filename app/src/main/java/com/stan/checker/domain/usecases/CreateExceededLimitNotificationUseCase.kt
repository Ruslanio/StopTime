package com.stan.checker.domain.usecases

import com.stan.checker.R
import com.stan.checker.core.notification.NotificationHelper
import com.stan.checker.presentation.model.UsageItem
import com.stan.checker.util.resourse.ResourceProvider
import javax.inject.Inject

class CreateExceededLimitNotificationUseCase @Inject constructor(
    private val notificationHelper: NotificationHelper,
    private val getRelevantTasksUseCase: GetRelevantTasksUseCase,
    private val resourceProvider: ResourceProvider
) {

    suspend operator fun invoke(usageItem: UsageItem) {
        val title = resourceProvider.getString(R.string.notification_title_template, usageItem.name)
        val subTitle = resourceProvider.getString(R.string.notification_subtitle_template)

        notificationHelper.sendNotification(
            tag = usageItem.packageName,
            title = title,
            subTitle = subTitle,
            image = usageItem.icon,
            bodyText = getBodyText()
        )
    }

    private suspend fun getBodyText(): String {
        val tasks = getRelevantTasksUseCase.invoke()
        return if (tasks.isEmpty()) {
            val builder = StringBuilder()

            with(builder) {
                append(resourceProvider.getString(R.string.notification_body_tasks_title))
                append("\n")
                append("\n")

                tasks.forEach {
                    append(" - ")
                    append(it.title)
                    append("\n")
                }
            }
            builder.toString()
        } else {
            resourceProvider.getString(R.string.notification_body_no_current_tasks_title)
        }
    }
}