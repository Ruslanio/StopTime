package com.stan.checker.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.stan.checker.presentation.usage.ROUTE_USAGE
import com.stan.checker.presentation.usage.usageScreen

@Composable
fun CheckerNavHost(
    navController: NavHostController,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = ROUTE_USAGE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        usageScreen()
    }
}