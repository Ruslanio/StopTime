package com.stan.checker.ui.components.bottomnavigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.stan.checker.core.navigation.BottomBarScreen


@Composable
fun CheckerBottomBar(
    destinations: List<BottomBarScreen>,
    onNavigateToDestination: (BottomBarScreen) -> Unit,
    currentDestination: NavDestination?
) {
    NavigationBar(
        contentColor = MaterialTheme.colorScheme.primaryContainer
    ) {
        var isSelected: Boolean
        destinations.forEach { screen ->
            isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                icon = {
                    CheckerNavbarItemIcon(screen.icon, isSelected)
                },
                label = { CheckerNavbarItemText(stringResource(screen.labelRes), isSelected) },
                selected = isSelected,
                onClick = { onNavigateToDestination(screen) }
            )
        }
    }
}

@Composable
private fun CheckerNavbarItemText(
    text: String,
    isSelected: Boolean
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = if (isSelected)
            MaterialTheme.colorScheme.onSurface
        else
            MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun CheckerNavbarItemIcon(
    icon: ImageVector,
    isSelected: Boolean
) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isSelected)
            MaterialTheme.colorScheme.onSecondaryContainer
        else
            MaterialTheme.colorScheme.onSurfaceVariant
    )
}