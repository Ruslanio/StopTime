package com.stan.checker.presentation.task.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stan.checker.R
import com.stan.checker.presentation.app.FabViewModel
import com.stan.checker.presentation.app.fabViewModel
import com.stan.checker.ui.components.chips.ActionChip
import com.stan.checker.ui.components.datepicker.DatePicker
import com.stan.checker.ui.components.typography.input.OutlinedTextInput
import com.stan.checker.util.date.Formatters
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TaskCreationScreen(
    viewModel: TaskCreationViewModel = hiltViewModel(),
    fabViewModel: FabViewModel = fabViewModel(),
    onBackClick: () -> Unit
) {
    fabViewModel.clearFabInfo()

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    TaskCreationContent(
        taskCreationState = state.value,
        onBackClick = onBackClick,
        taskCreationActions = viewModel
    )
}

@Composable
private fun TaskCreationContent(
    taskCreationState: TaskCreationState,
    onBackClick: () -> Unit,
    taskCreationActions: TaskCreationActions
) {

    when (taskCreationState) {
        is TaskCreationState.CreationInProgress ->
            TaskCreationContent(
                taskCreationActions = taskCreationActions,
                state = taskCreationState
            )

        is TaskCreationState.SuccessfulCreation -> onBackClick.invoke()
    }
}

@Composable
private fun TaskCreationContent(
    state: TaskCreationState.CreationInProgress,
    taskCreationActions: TaskCreationActions
) {
    Scaffold(
        modifier = Modifier.background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) { padding ->
        var shouldShowDatePicker by remember { mutableStateOf(false) }
        var isDescriptionFieldShown by remember { mutableStateOf(false) }

        Column(Modifier.padding(16.dp)) {
            if (shouldShowDatePicker) {
                DatePicker(
                    dateFormatter = Formatters.Date.uiFriendly,
                    onDateSelected = taskCreationActions::onChangeDate,
                    onDismissRequest = { shouldShowDatePicker = false }
                )
            }

            OutlinedTextInput(
                initialValue = state.taskName,
                onValueChange = {
                    taskCreationActions.onNameChanged(it)
                    state.nameFieldError = null
                },
                errorMessage = state.nameFieldError,
                labelText = stringResource(id = R.string.task_creation_field_name_hint),
                shouldRequestFocus = true
            )

            if (isDescriptionFieldShown) {
                OutlinedTextInput(
                    initialValue = state.taskDescription,
                    onValueChange = {
                        taskCreationActions.onDescriptionChange(it)
                        state.descriptionFieldError = null
                    },
                    errorMessage = state.descriptionFieldError,
                    isExpandable = true,
                    labelText = stringResource(id = R.string.task_creation_field_description_hint)
                )
            }

            Spacer(modifier = Modifier.size(10.dp))

            TaskDateChip(
                taskDate = state.taskDate,
                taskTime = state.taskTime,
                onClearDate = taskCreationActions::onClearDate
            )

            Spacer(modifier = Modifier.size(12.dp))

            Divider(modifier = Modifier.fillMaxWidth())

            TaskCreationButtonsRow(
                isLoading = state.isLoading,
                onShowDescriptionClick = {
                    isDescriptionFieldShown = isDescriptionFieldShown.not()
                },
                onShowDatePickerClick = { shouldShowDatePicker = true },
                onSaveClicked = taskCreationActions::onSaveClicked
            )
        }
    }
}

@Composable
private fun TaskDateChip(
    taskDate: LocalDate?,
    taskTime: LocalTime?,
    onClearDate: () -> Unit
) {

    taskDate?.let { pickedDate ->

        val dateText = pickedDate.format(Formatters.Date.uiFriendly)
        val timeText = taskTime?.format(Formatters.Time.uiFriendly)
        ActionChip(
            text = "$dateText ${timeText.orEmpty()}",
            onActionClick = onClearDate
        )
    }
}

@Composable
private fun TaskCreationButtonsRow(
    isLoading: Boolean,
    onShowDescriptionClick: () -> Unit,
    onShowDatePickerClick: () -> Unit,
    onSaveClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        IconButton(onClick = onShowDescriptionClick, enabled = isLoading.not()) {
            Icon(
                imageVector = Icons.Rounded.List,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        IconButton(onClick = onShowDatePickerClick, enabled = isLoading.not()) {
            Icon(
                imageVector = Icons.Rounded.DateRange,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(
            Modifier
                .weight(1f)
        )

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            IconButton(
                onClick = { onSaveClicked() }
            ) {
                Icon(imageVector = Icons.Rounded.KeyboardArrowRight, contentDescription = null)
            }
        }
    }
}