@file:OptIn(ExperimentalMaterial3Api::class)

package com.stan.checker.ui.components.datepicker

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import java.time.LocalTime
import java.util.Calendar

@Composable
fun TimePickerDialog(
    onConfirm: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    TimePickerDialogWrapper(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState.toLocalTime()) }
    ) {
        TimePicker(
            state = timePickerState,
        )
    }
}

@Composable
private fun TimePickerDialogWrapper(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}

private fun TimePickerState.toLocalTime() =
    LocalTime.of(hour,minute)