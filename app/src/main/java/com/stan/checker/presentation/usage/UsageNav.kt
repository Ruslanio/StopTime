package com.stan.checker.presentation.usage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val ROUTE_USAGE = "ROUTE_TODAY"

fun NavController.navigateToUsage(navOptions: NavOptions? = null) {
    this.navigate(ROUTE_USAGE, navOptions)
}

fun NavGraphBuilder.usageScreen() {
    composable(route = ROUTE_USAGE) {
        UsageScreen()
    }
}