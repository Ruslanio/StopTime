@file:OptIn(ExperimentalMaterialNavigationApi::class)

package com.stan.checker.presentation.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.stan.checker.core.navigation.CheckerNavHost
import com.stan.checker.presentation.home.GRAPH_HOME
import com.stan.checker.presentation.permission.ROUTE_PERMISSION
import com.stan.checker.ui.components.bottomnavigation.CheckerBottomBar

@Composable
fun CheckerApp(
    isPermissionProvided: Boolean,
    appState: CheckerAppState = rememberCheckerState(),
    fabViewModel: FabViewModel = hiltViewModel()
) {
    val isVisible by fabViewModel.isVisible

    Scaffold(
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                CheckerBottomBar(
                    destinations = appState.bottomBarScreens,
                    onNavigateToDestination = appState::navigateToBottomBarScreen,
                    currentDestination = appState.currentDestination
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally { it * 2 },
                exit = slideOutHorizontally { it * 2 }
            ) {
                FloatingActionButton(
                    onClick = fabViewModel.onClickAction.value,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = fabViewModel.iconRes.value,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = null,
                    )
                }
            }
        }
    ) { innerPadding ->
        val startDestination = if (isPermissionProvided) {
            GRAPH_HOME
        } else {
            ROUTE_PERMISSION
        }
        CheckerNavHost(
            fabViewModel = fabViewModel,
            navController = appState.navController,
            bottomSheetNavigator = appState.bottomSheetNavigator,
            onBackClick = appState::onBackClick,
            modifier = Modifier
                .padding(innerPadding),
            startDestination = startDestination
        )
    }
}