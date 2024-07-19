package com.stan.checker.presentation.usage

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import com.stan.checker.core.navigation.composableNoAnim
import com.stan.checker.presentation.app.FabViewModel

const val ROUTE_USAGE = "ROUTE_USAGE"

fun NavController.navigateToUsage(navOptions: NavOptions? = null) {
    this.navigate(ROUTE_USAGE, navOptions)
}

fun NavGraphBuilder.usageScreen(
    fabViewModel: FabViewModel
) {
    composableNoAnim(route = ROUTE_USAGE) {
        UsageScreen(fabViewModel)
    }
}