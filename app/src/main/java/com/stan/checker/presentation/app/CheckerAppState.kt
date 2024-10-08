@file:OptIn(ExperimentalMaterialNavigationApi::class)

package com.stan.checker.presentation.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.plusAssign
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.stan.checker.core.navigation.BottomBarScreen
import com.stan.checker.core.navigation.getHomeDestinations
import com.stan.checker.presentation.task.list.ROUTE_TASK_LIST
import com.stan.checker.presentation.task.list.navigateToTaskList
import com.stan.checker.presentation.usage.ROUTE_USAGE
import com.stan.checker.presentation.usage.navigateToUsage

@Composable
fun rememberCheckerState(
    navController: NavHostController = rememberNavController(),
    bottomSheetNavigator: BottomSheetNavigator = rememberBottomSheetNavigator()
): CheckerAppState {
    navController.navigatorProvider += bottomSheetNavigator
    return remember(navController) {
        CheckerAppState(navController, bottomSheetNavigator)
    }
}

@Stable
class CheckerAppState(
    val navController: NavHostController,
    val bottomSheetNavigator: BottomSheetNavigator,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentBottomBarDestination: BottomBarScreen?
        @Composable get() = when (currentDestination?.route) {
            ROUTE_USAGE -> BottomBarScreen.Usage
            ROUTE_TASK_LIST -> BottomBarScreen.TodoList
            else -> null
        }

    val shouldShowBottomBar: Boolean
        @Composable get() = currentBottomBarDestination != null

    val bottomBarScreens: List<BottomBarScreen> = getHomeDestinations()

    fun navigateToBottomBarScreen(bottomBarScreen: BottomBarScreen) {
        val bottomBarScreenNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (bottomBarScreen) {
            BottomBarScreen.Usage -> navController.navigateToUsage(bottomBarScreenNavOptions)
            BottomBarScreen.TodoList -> navController.navigateToTaskList(bottomBarScreenNavOptions)
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}