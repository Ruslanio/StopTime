package com.stan.checker.presentation.task.list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.stan.checker.core.navigation.composableNoAnim
import com.stan.checker.presentation.task.list.ui.TaskListScreen

const val GRAPH_ROUTE_TASK_LIST = "GRAPH_ROUTE_TASK_LIST"
const val ROUTE_TASK_LIST = "ROUTE_TASK_LIST"

fun NavController.navigateToTaskList(navOptions: NavOptions? = null) {
    this.navigate(GRAPH_ROUTE_TASK_LIST, navOptions)
}

fun NavGraphBuilder.taskListGraph(
    navigateToTaskCreation: () -> Unit,
    navigateToTaskEdit: (taskId: Int) -> Unit,
    nestedGraph: NavGraphBuilder.() -> Unit
) {
    navigation(
        route = GRAPH_ROUTE_TASK_LIST,
        startDestination = ROUTE_TASK_LIST
    ) {
        composableNoAnim(route = ROUTE_TASK_LIST) {
            TaskListScreen(
                navigateToTaskCreation = navigateToTaskCreation,
                navigateToTaskEdit = navigateToTaskEdit
            )
        }
        nestedGraph()
    }
}
