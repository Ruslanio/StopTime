package com.stan.checker.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.stan.checker.presentation.app.FabViewModel
import com.stan.checker.presentation.home.homeGraph
import com.stan.checker.presentation.home.navigateToHome
import com.stan.checker.presentation.permission.ROUTE_PERMISSION
import com.stan.checker.presentation.permission.permissionScreen
import com.stan.checker.presentation.task.list.taskListGraph
import com.stan.checker.presentation.usage.usageScreen

@Composable
fun CheckerNavHost(
    navController: NavHostController,
    fabViewModel: FabViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = ROUTE_PERMISSION
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        permissionScreen(
            navigateToHome = {
                navController.navigateToHome()
            }
        )

        homeGraph {
            usageScreen()
            taskListGraph(
                navigateToTaskCreation = {},
                navigateToTaskEdit = {},
                nestedGraph = {}
            )
        }
    }
}