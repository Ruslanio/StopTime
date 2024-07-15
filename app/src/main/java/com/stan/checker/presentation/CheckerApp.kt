package com.stan.checker.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stan.checker.core.navigation.CheckerNavHost
import com.stan.checker.presentation.home.GRAPH_HOME
import com.stan.checker.presentation.permission.ROUTE_PERMISSION
import com.stan.checker.ui.components.bottomnavigation.CheckerBottomBar

@Composable
fun CheckerApp(
    isPermissionProvided: Boolean,
    appState: CheckerAppState = rememberCheckerState()
) {
    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                CheckerBottomBar(
                    destinations = appState.bottomBarScreens,
                    onNavigateToDestination = appState::navigateToBottomBarScreen,
                    currentDestination = appState.currentDestination
                )
            }
        }
    ) { innerPadding ->
        val startDestination = if (isPermissionProvided) {
            GRAPH_HOME
        } else {
            ROUTE_PERMISSION
        }
        CheckerNavHost(
            navController = appState.navController,
            onBackClick = appState::onBackClick,
            modifier = Modifier
                .padding(innerPadding),
            startDestination = startDestination
        )
    }
}