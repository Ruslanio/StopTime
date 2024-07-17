package com.stan.checker.util.testdata

import com.stan.checker.core.database.entities.TaskEntity
import com.stan.checker.presentation.model.DateStatus
import com.stan.checker.presentation.model.TaskItem
import com.stan.checker.ui.BaseListItem
import com.stan.checker.ui.components.list.headlines.HeadlineItem
import com.stan.checker.ui.components.list.headlines.SmallHeadlineItem
import java.time.LocalDate
import java.time.LocalTime

object TaskTestData {

    fun getCompletedTask() =
        TaskItem(
            id = 1,
            "Do dishes",
            "You know what to do",
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Perpetual
        )

    fun getCompletedTaskNoDescription() =
        TaskItem(
            id = 1,
            "Do dishes",
            null,
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Perpetual
        )

    fun getUncompletedTask() =
        TaskItem(
            id = 1,
            "Do dishes",
            "You know what to do",
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Regular("22.05.2022", "03:32")
        )

    fun getUncompletedTaskNoDescription() =
        TaskItem(
            id = 1,
            "Do dishes",
            null,
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Regular("22.05.2022", "03:32")
        )

    fun getExpiredTaskNoDescription() =
        TaskItem(
            id = 1,
            "Do dishes",
            null,
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Expired("22.05.2022", "03:32")
        )

    fun getExpiredTaskWithDescription() =
        TaskItem(
            id = 1,
            "Do dishes",
            "You know what to do",
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Expired("22.05.2022", "03:32")
        )


    fun getItems() = listOf(
        HeadlineItem("Today"),
        TaskItem(
            id = 1,
            "Do dishes",
            "You know what to do",
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Regular("22.05.2022", "03:32")
        ),
        TaskItem(
            id = 2,
            "Buy a house",
            "Better get that money, and fast",
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Regular("22.05.2022", "03:32")
        ),
        TaskItem(
            id = 3,
            "Be awesome",
            "Not that hard, ay?",
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Regular("22.05.2022", "03:32")
        ),
        HeadlineItem("Tomorrow"),
        TaskItem(
            id = 4,
            "Be awesome",
            "Not that hard, ay?",
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Perpetual
        ),
        HeadlineItem("Nearest Future"),
        SmallHeadlineItem("11 february"),
        TaskItem(
            id = 5,
            "Be awesome",
            "Not that hard, ay?",
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Regular("22.05.2022", "03:32")
        ),
        SmallHeadlineItem("1 april"),
        TaskItem(
            id = 6,
            "Don't be a fool",
            "You knew that day would come",
            true,
            LocalDate.now(),
            LocalTime.now(),
            DateStatus.Regular("22.05.2022", "03:32")
        )
    )

    fun getEntities() = listOf(
        TaskEntity(
            id = 1,
            "Be awesome",
            "Not that hard, ay?",
            LocalDate.now(),
            LocalTime.now(),
            false
        ),
        TaskEntity(
            id = 2,
            "Buy a house",
            "Better get that money, and fast",
            LocalDate.now(),
            null,
            true
        ),
        TaskEntity(
            id = 3,
            "Be awesome",
            "Not that hard, ay?",
            LocalDate.now(),
            null,
            false
        ),
        TaskEntity(
            id = 4,
            "Be awesome",
            "Not that hard, ay?",
            LocalDate.now().plusDays(1),
            LocalTime.now(),
            false
        ),
        TaskEntity(
            id = 5,
            "Be awesome",
            "Not that hard, ay?",
            LocalDate.now().plusMonths(1),
            null,
            true
        ),
        TaskEntity(
            id = 6,
            "Don't be a fool",
            "You knew that day would come",
            LocalDate.now().plusMonths(1).plusDays(3),
            LocalTime.now(),
            false
        ),
    )

    fun getEmptyList() = listOf<BaseListItem>()
}