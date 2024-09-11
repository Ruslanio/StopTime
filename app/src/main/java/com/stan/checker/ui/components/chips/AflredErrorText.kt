package com.stan.checker.ui.components.chips

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun AflredErrorText(
    errorMessage: String
) {
    Text(
        text = errorMessage,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.error
    )
}