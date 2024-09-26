@file:OptIn(ExperimentalMaterial3Api::class)

package com.stan.checker.ui.components.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.stan.checker.R
import com.stan.checker.util.date.Formatters
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private val CORNER_RADIUS = 10.dp

@Composable
fun DatePickerModal(
    initialDate: LocalDate?,
    selectedTime: LocalTime?,
    dateFormatter: DateTimeFormatter,
    onDismissRequest: () -> Unit,
    onShowTimePickerClick: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate?.toTimestamp() ?: LocalDate.now().toTimestamp()
    )

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = CORNER_RADIUS)
                )
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false,
            )

            DatePickerControlsV2(
                onConfirmClick = { onDateSelected(datePickerState.selectedDateMillis!!.toLocalDate()) },
                selectedTime = selectedTime,
                onShowTimePickerClick = onShowTimePickerClick,
                onDismissRequest = onDismissRequest
            )
        }
    }
}

@Composable
private fun DatePickerControlsV2(
    selectedTime: LocalTime?,
    onConfirmClick: () -> Unit,
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
                val text = if (selectedTime != null) {
                    selectedTime.format(Formatters.Time.uiFriendly).orEmpty()
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
                    onConfirmClick()
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


private fun LocalDate.toTimestamp() =
    atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()

private fun Long.toLocalDate() =
    Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()