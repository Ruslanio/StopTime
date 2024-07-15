package com.stan.checker.presentation.todo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val ROUTE_TODO = "ROUTE_TODO"

fun NavController.navigateToTodo(navOptions: NavOptions? = null) {
    this.navigate(ROUTE_TODO, navOptions)
}

fun NavGraphBuilder.todoScreen() {
    composable(ROUTE_TODO) {
        TodoScreen()
    }
}