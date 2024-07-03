package com.stan.checker.core.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.ui.graphics.vector.ImageVector
import com.stan.checker.R
import com.stan.checker.presentation.usage.ROUTE_USAGE

private const val ROUTE_IN_PROGRESS = "IN_PROGRESS"

sealed class BottomBarScreen(
    val route: String,
    val icon: ImageVector,
    @StringRes val labelRes: Int
) {
    data object Usage : BottomBarScreen(
        ROUTE_USAGE,
        Icons.Rounded.Notifications,
        R.string.category_title_usage
    )

    data object TodoList : BottomBarScreen(
        ROUTE_IN_PROGRESS,
        Icons.Rounded.List,
        R.string.category_title_todo
    )
}

fun getHomeDestinations(): List<BottomBarScreen> {
    return listOf(
        BottomBarScreen.Usage,
        BottomBarScreen.TodoList,
    )
}