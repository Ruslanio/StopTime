package com.stan.checker.ui.components.datepicker

import android.widget.CalendarView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.stan.checker.R
import com.stan.checker.util.date.Formatters
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/**
 * A Jetpack Compose compatible Date Picker.
 * @param onDateSelected Will get called when a date gets picked.
 * @param onDismissRequest Will get called when the user requests to close the dialog.
 */

private val CORNER_RADIUS = 10.dp

@Composable
fun DatePicker(
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

    }

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = CORNER_RADIUS)
                )
        ) {
            DatePickerHeader(
                title = title,
                selectedDate = selectedDate,
                formatter = dateFormatter
            )

            CustomCalendarView(
                initialDate = initialDate,
                onDateSelected = { pickedDate ->
                    selectedDate = pickedDate
                }
            )

            Spacer(modifier = Modifier.size(8.dp))

            DatePickerControls(
                isTimePicked = isTimePicked,
                selectedDate = selectedDate,
                selectedTime = selectedTime,
                onDateSelected = onDateSelected,
                onShowTimePickerClick = { shouldShowTimePicker = true },
                onDismissRequest = onDismissRequest
            )
        }
    }
}

@Composable
private fun DatePickerHeader(
    title: String,
    selectedDate: LocalDate,
    formatter: DateTimeFormatter
) {
    Column(
        Modifier
            .defaultMinSize(minHeight = 72.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(topStart = CORNER_RADIUS, topEnd = CORNER_RADIUS)
            )
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.size(12.dp))

        Text(
            text = selectedDate.format(formatter),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Composable
private fun DatePickerControls(
    selectedDate: LocalDate,
    selectedTime: LocalTime?,
    isTimePicked: Boolean,
    onDateSelected: (LocalDate, LocalTime?) -> Unit,
    onShowTimePickerClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    onShowTimePickerClick.invoke()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_time),
                tint = MaterialTheme.colorScheme.secondary,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(8.dp)
            ) {
                val text = if (isTimePicked) {
                    selectedTime?.format(Formatters.Time.uiFriendly).orEmpty()
                } else {
                    stringResource(id = R.string.dialog_date_picker_time_action)
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

        }

        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(bottom = 16.dp, end = 16.dp)
        ) {
            Button(
                onClick = onDismissRequest,
                colors = ButtonDefaults.textButtonColors(),
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_generic_cancel),
                )
            }

            Button(
                onClick = {
                    onDateSelected(selectedDate, selectedTime)
                    onDismissRequest()
                },
                colors = ButtonDefaults.textButtonColors(),
            ) {
                Text(
                    text = stringResource(id = R.string.dialog_generic_ok),
                )
            }
        }
    }
}

/**
 * Used at [DatePicker] to create the calendar picker.
 * @author Arnau Mora, Joao Gavazzi
 * @param minDate The minimum date allowed to be picked.
 * @param maxDate The maximum date allowed to be picked.
 * @param onDateSelected Will get called when a date is selected.
 */
@Composable
private fun CustomCalendarView(
    initialDate: LocalDate? = null,
    minDate: Long? = null,
    maxDate: Long? = null,
    onDateSelected: (LocalDate) -> Unit
) {
    // Adds view to Compose
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            CalendarView(context)
        },
        update = { view ->
            if (minDate != null)
                view.minDate = minDate
            if (maxDate != null)
                view.maxDate = maxDate

            initialDate?.let {
                view.date = it.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
            }

            view.setOnDateChangeListener { _, year, month, dayOfMonth ->
                // We have to add 1 to month here, because CalendarView counts months from 0
                onDateSelected(
                    LocalDate.of(year, month + 1, dayOfMonth)
                )
            }
        }
    )
}