package com.stan.checker.presentation.permission

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val ROUTE_PERMISSION = "ROUTE_PERMISSION"

fun NavController.navigateToPermission(navOptions: NavOptions? = null) {
    this.navigate(ROUTE_PERMISSION, navOptions)
}

fun NavGraphBuilder.permissionScreen(
    navigateToHome: () -> Unit
) {
    composable(ROUTE_PERMISSION) {
        PermissionScreen(
            navigateToHome = navigateToHome
        )
    }
}