package com.stan.checker.ui.components.chips

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.stan.checker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionChip(
    text: String,
    isError: Boolean = false,
    onChipClick: () -> Unit = {},
    onActionClick: () -> Unit
) {
    AssistChip(
        border = getAlfredChipBorder(isError),
        onClick = onChipClick,
        label = {
            if (isError) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        trailingIcon = {
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                IconButton(onClick = { onActionClick.invoke() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        tint = getActionIconColor(isError)
                    )
                }
            }
        }
    )
}

@Composable
private fun getActionIconColor(isError: Boolean): Color {
    return if (isError) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }
}

@Composable
private fun getAlfredChipBorder(isError: Boolean): BorderStroke {
    return if (isError) {
        AssistChipDefaults.assistChipBorder(
            enabled = true,
            borderColor = MaterialTheme.colorScheme.error
        )
    } else {
        AssistChipDefaults.assistChipBorder(enabled = true)
    }
}