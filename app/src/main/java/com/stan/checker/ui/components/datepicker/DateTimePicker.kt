package com.stan.checker.ui.components.datepicker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.stan.checker.R
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun DateTimePicker(
    initialDate: LocalDate? = null,
    initialTime: LocalTime? = null,
    title: String = stringResource(id = R.string.dialog_date_picker_title),
    dateFormatter: DateTimeFormatter,
    onDateSelected: (LocalDate, LocalTime?) -> Unit,
    onDismissRequest: () -> Unit
) {
    var selectedDate by remember { mutableStateOf(initialDate ?: LocalDate.now()) }
    var selectedTime: LocalTime? by remember { mutableStateOf(initialTime) }

    var shouldShowTimePicker by remember { mutableStateOf(false) }
    var isTimePicked by remember { mutableStateOf(initialTime != null) }

    if (shouldShowTimePicker) {
        TimePickerDialog(
            onConfirm = {
                selectedTime = it
                shouldShowTimePicker = false
                isTimePicked = true
            },
            onDismiss = { shouldShowTimePicker = false }
        )
    }

    DatePickerModal(
        initialDate = initialDate,
        onDismissRequest = onDismissRequest,
        selectedTime = selectedTime,
        dateFormatter = dateFormatter,
        onShowTimePickerClick = { shouldShowTimePicker = true }
    ) {
        selectedDate = it
        onDateSelected(selectedDate, selectedTime)
    }
}