package com.stan.checker.ui.components.typography.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged

@Composable
fun OutlinedTextInput(
    initialValue: String,
    labelText: String,
    isExpandable: Boolean = false,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit = {},
    shouldRequestFocus: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    var value by rememberSaveable { mutableStateOf(initialValue) }
    var isFocused by rememberSaveable { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Column {
        OutlinedTextField(
            leadingIcon = leadingIcon,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            isError = errorMessage != null,
            value = value,
            label = {
                OutlinedTextInputLabel(
                    text = labelText,
                    isError = errorMessage != null,
                    isFocused = isFocused,
                    isPopulated = value.isNotEmpty()
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge
                .copy(color = MaterialTheme.colorScheme.onSurface),
            onValueChange = {
                value = it
                onValueChange.invoke(it)
            },
            supportingText = {
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            singleLine = !isExpandable
        )
        if (shouldRequestFocus) {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        }
    }
}

@Composable
private fun OutlinedTextInputLabel(
    text: String,
    isError: Boolean,
    isFocused: Boolean,
    isPopulated: Boolean
) {
    val style = if (isPopulated || isFocused) {
        MaterialTheme.typography.bodySmall
    } else {
        MaterialTheme.typography.bodyLarge
    }

    val color = when {
        isError -> MaterialTheme.colorScheme.error
        isFocused -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }

    Text(text = text, style = style, color = color)
}