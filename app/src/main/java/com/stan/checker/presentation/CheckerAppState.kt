package com.stan.checker.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.stan.checker.core.navigation.BottomBarScreen
import com.stan.checker.core.navigation.getHomeDestinations
import com.stan.checker.presentation.usage.ROUTE_USAGE
import com.stan.checker.presentation.usage.navigateToUsage

@Composable
fun rememberCheckerState(
    navController: NavHostController = rememberNavController()
) : CheckerAppState {
     return remember(navController) {
         CheckerAppState(navController)
     }
}

@Stable
class CheckerAppState(
    val navController: NavHostController
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentBottomBarDestination: BottomBarScreen?
        @Composable get() = when (currentDestination?.route) {
            ROUTE_USAGE -> BottomBarScreen.Usage
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
            // TODO change to todo
            BottomBarScreen.TodoList -> navController.navigateToUsage(bottomBarScreenNavOptions)
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}