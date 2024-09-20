package com.stan.checker.presentation.task.edit

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@VisibleForTesting
const val ARG_TASK_ID = "taskId"

class TaskEditArguments(savedStateHandle: SavedStateHandle) {
    // TODO remove this "?.toInt()" workaround when NavType will actually work
    val taskId: Int = checkNotNull(savedStateHandle.get<String>(ARG_TASK_ID)?.toInt())
}

private const val ROUTE_TASK_EDIT = "ROUTE_TASK_EDIT"

fun NavController.navigateToTaskEdit(
    taskId: Int,
    navOptions: NavOptions? = null
) {
    this.navigate("$ROUTE_TASK_EDIT/$taskId", navOptions)
}


fun NavGraphBuilder.taskEdit(
    onBackClick: () -> Unit
) {
    composable(
        route = "$ROUTE_TASK_EDIT/{$ARG_TASK_ID}",
        arguments = listOf(
            navArgument(ARG_TASK_ID) { NavType.IntType }
        )
    ) {
        TaskEditScreen(
            onBackClick = onBackClick
        )
    }
}
