package com.stan.checker.presentation.task.creation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

const val ROUTE_TASK_CREATION = "ROUTE_TASK_CREATION"

fun NavController.navigateToTaskCreation(navOptions: NavOptions? = null) {
    this.navigate(ROUTE_TASK_CREATION, navOptions)
}

@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.taskCreation(
    onBackClick: () -> Unit
) {
    bottomSheet(
        route = ROUTE_TASK_CREATION
    ) {
        TaskCreationScreen(onBackClick = onBackClick)
    }
}
