package com.stan.checker.core.navigation

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.stan.checker.presentation.home.homeGraph
import com.stan.checker.presentation.home.navigateToHome
import com.stan.checker.presentation.permission.ROUTE_PERMISSION
import com.stan.checker.presentation.permission.permissionScreen
import com.stan.checker.presentation.task.creation.navigateToTaskCreation
import com.stan.checker.presentation.task.creation.taskCreation
import com.stan.checker.presentation.task.edit.navigateToTaskEdit
import com.stan.checker.presentation.task.edit.taskEdit
import com.stan.checker.presentation.task.list.taskListGraph
import com.stan.checker.presentation.usage.usageScreen

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun CheckerNavHost(
    navController: NavHostController,
    bottomSheetNavigator: BottomSheetNavigator,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = ROUTE_PERMISSION
) {
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(8.dp, 8.dp),
        bottomSheetNavigator = bottomSheetNavigator
    ) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier,
            enterTransition = { slideInVertically() },
            exitTransition = { slideOutVertically() },
            popEnterTransition = { slideInVertically() },
            popExitTransition = { slideOutVertically() }
        ) {
            permissionScreen(
                navigateToHome = {
                    navController.navigateToHome()
                }
            )

            homeGraph {
                usageScreen()
                taskListGraph(
                    navigateToTaskCreation = {
                        navController.navigateToTaskCreation()
                    },
                    navigateToTaskEdit = {
                        navController.navigateToTaskEdit(it)
                    },
                    nestedGraph = {
                        taskCreation(onBackClick)
                        taskEdit(onBackClick)
                    }
                )
            }
        }
    }
}