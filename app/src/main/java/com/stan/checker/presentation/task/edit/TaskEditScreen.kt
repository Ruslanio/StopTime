package com.stan.checker.presentation.task.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stan.checker.R
import com.stan.checker.ext.appendIfNotNull
import com.stan.checker.presentation.app.FabViewModel
import com.stan.checker.presentation.app.fabViewModel
import com.stan.checker.presentation.model.DateStatus
import com.stan.checker.presentation.model.TaskItem
import com.stan.checker.ui.components.checkbox.CheckerCheckBox
import com.stan.checker.ui.components.chips.ActionChip
import com.stan.checker.ui.components.datepicker.DatePicker
import com.stan.checker.ui.components.typography.input.OutlinedTextInput
import com.stan.checker.util.date.Formatters

@Composable
fun TaskEditScreen(
    onBackClick: () -> Unit,
    viewModel: TaskEditViewModel = hiltViewModel(),
    fabViewModel: FabViewModel = fabViewModel()
) {
    fabViewModel.clearFabInfo()

    val taskEditState: TaskEditState by viewModel.uiState.collectAsStateWithLifecycle()
    TaskEditContent(
        taskEditState = taskEditState,
        taskEditingActions = viewModel
    )
}

@Composable
private fun TaskEditContent(
    taskEditState: TaskEditState,
    taskEditingActions: TaskEditingActions
) {
    when (taskEditState) {
        is TaskEditState.InitialState -> MainInfo(
            taskItem = taskEditState.taskItem,
            nameValidationErrorMessage = taskEditState.nameValidationError,
            descriptionValidationErrorMessage = taskEditState.descriptionValidationError,
            taskEditingActions = taskEditingActions
        )

        TaskEditState.Loading -> Loading()
        TaskEditState.TaskNotFound -> TaskNotFound()
    }
}

@Composable
private fun Loading() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun TaskNotFound() {
    Text("NOT FOUND!!!")
}


@Composable
private fun MainInfo(
    taskItem: TaskItem,
    taskEditingActions: TaskEditingActions,
    nameValidationErrorMessage: String? = null,
    descriptionValidationErrorMessage: String? = null
) {
    Column (
        modifier = Modifier.background(MaterialTheme.colorScheme.surface)
    ) {

        var shouldShowDatePicker by remember { mutableStateOf(false) }
        var isShowDescription by remember { mutableStateOf(false) }

        if (shouldShowDatePicker) {
            // TODO use actual compose date picker when it's available
            DatePicker(
                initialDate = taskItem.date,
                initialTime = taskItem.time,
                title = stringResource(id = R.string.task_creation_date_dialog_title),
                dateFormatter = Formatters.Date.uiFriendly,
                onDateSelected = { date, time ->
                    taskEditingActions.onNewDatePicked(date)
                    taskEditingActions.onNewTimePicked(time)
                },
                onDismissRequest = {
                    shouldShowDatePicker = false
                })
        }

        Column(modifier = Modifier.padding(16.dp)) {

            OutlinedTextInput(
                initialValue = taskItem.title,
                onValueChange = {
                    taskEditingActions.onNameChanged(it)
                },
                errorMessage = nameValidationErrorMessage,
                labelText = stringResource(id = R.string.task_edit_field_name_hint)
            )

            TaskDescription(
                isDescriptionShown = isShowDescription,
                taskDescription = taskItem.description,
                onDescriptionChange = taskEditingActions::onDescriptionChanged,
                errorMessage = descriptionValidationErrorMessage,
                onAddDescriptionClick = { isShowDescription = true }
            )

            TaskDate(
                taskStatus = taskItem.dateStatus,
                onChangeDateClick = {
                    shouldShowDatePicker = shouldShowDatePicker.not()
                },
                onRemoveDateClick = taskEditingActions::onClearDateFromTaskClick
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        CompletionContainer(
            isCompleted = taskItem.isCompleted,
            onCompletedClick = taskEditingActions::onIsCompletedChanged
        )
    }
}

@Composable
private fun TaskDescription(
    isDescriptionShown: Boolean,
    taskDescription: String?,
    errorMessage: String?,
    onDescriptionChange: (String) -> Unit,
    onAddDescriptionClick: () -> Unit
) {
    if (!taskDescription.isNullOrEmpty() || isDescriptionShown) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextInput(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Rounded.List,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                initialValue = taskDescription.orEmpty(),
                onValueChange = onDescriptionChange,
                errorMessage = errorMessage,
                isExpandable = true,
                shouldRequestFocus = taskDescription.isNullOrEmpty() && isDescriptionShown,
                labelText = stringResource(id = R.string.task_edit_field_description_hint)
            )
        }
    } else {
        FilledTonalButton(onClick = onAddDescriptionClick) {
            Icon(imageVector = Icons.Rounded.List, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = stringResource(id = R.string.task_edit_action_add_description))
        }
    }
}

@Composable
private fun TaskDate(
    taskStatus: DateStatus,
    onChangeDateClick: () -> Unit,
    onRemoveDateClick: () -> Unit
) {
    when (taskStatus) {
        is DateStatus.Regular -> DateChip(
            date = taskStatus.date,
            time = taskStatus.time,
            onActionClick = onRemoveDateClick,
            onChangeDateClick = onChangeDateClick
        )

        is DateStatus.Expired -> DateChip(
            date = taskStatus.date,
            time = taskStatus.time,
            isExpired = true,
            onActionClick = onRemoveDateClick,
            onChangeDateClick = onChangeDateClick
        )

        DateStatus.Perpetual -> {
            FilledTonalButton(onClick = onChangeDateClick) {
                Icon(imageVector = Icons.Rounded.List, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(id = R.string.task_edit_action_add_date))
            }
        }
    }
}

@Composable
private fun DateChip(
    date: String,
    time: String?,
    isExpired: Boolean = false,
    onActionClick: () -> Unit,
    onChangeDateClick: () -> Unit
) {
    ActionChip(
        text = date.appendIfNotNull(time),
        isError = isExpired,
        onActionClick = onActionClick,
        onChipClick = onChangeDateClick
    )
}

@Composable
private fun CompletionContainer(
    isCompleted: Boolean,
    onCompletedClick: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 42.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(
                id = if (isCompleted) {
                    R.string.task_edit_completed
                } else {
                    R.string.task_edit_not_completed
                }
            ),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.width(8.dp))

        CheckerCheckBox(
            isChecked = isCompleted,
            onCheckedChanged = onCompletedClick
        )
    }
}