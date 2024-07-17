package com.stan.checker.presentation.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation
import com.stan.checker.presentation.task.list.taskListGraph
import com.stan.checker.presentation.usage.ROUTE_USAGE
import com.stan.checker.presentation.usage.usageScreen

const val GRAPH_HOME = "GRAPH_HOME"

fun NavController.navigateToHome() {
    this.navigate(GRAPH_HOME) { popUpTo(0) }
}

fun NavGraphBuilder.homeGraph() {
    navigation(
        route = GRAPH_HOME,
        startDestination = ROUTE_USAGE
    ) {
        usageScreen()
        taskListGraph({}, {}, {})
    }
}