package com.stan.checker.presentation.task.list.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stan.checker.R
import com.stan.checker.presentation.app.FabViewModel
import com.stan.checker.presentation.model.TaskItem
import com.stan.checker.presentation.task.list.TaskListState
import com.stan.checker.presentation.task.list.TaskListViewModel
import com.stan.checker.ui.BaseListItem
import com.stan.checker.ui.components.list.headlines.HeadLine
import com.stan.checker.ui.components.list.headlines.HeadlineItem
import com.stan.checker.ui.components.list.headlines.SmallHeadLine
import com.stan.checker.ui.components.list.headlines.SmallHeadlineItem
import com.stan.checker.ui.ext.isScrollingUp

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel(),
    fabViewModel: FabViewModel,
    navigateToTaskCreation: () -> Unit,
    navigateToTaskEdit: (taskId: Int) -> Unit
) {
    val taskListState: TaskListState by viewModel.uiState.collectAsStateWithLifecycle()

    TaskListContent(
        taskListState = taskListState,
        fabViewModel = fabViewModel,
        tryToRefresh = viewModel::tryToRefresh,
        navigateToTaskCreation = navigateToTaskCreation,
        onTaskCompletionStateChange = viewModel::onTaskCompletionStatusChange,
        onTaskEditClick = navigateToTaskEdit,
        onDeleteTask = viewModel::onDeleteTask
    )
}

@Composable
private fun TaskListContent(
    taskListState: TaskListState,
    fabViewModel: FabViewModel,
    tryToRefresh: () -> Unit,
    onDeleteTask: (taskId: Int) -> Unit,
    onTaskEditClick: (taskId: Int) -> Unit,
    onTaskCompletionStateChange: (taskId: Int, isCompleted: Boolean) -> Unit,
    navigateToTaskCreation: () -> Unit
) {
    val listState = rememberLazyListState()

    fabViewModel.isVisible.value = listState.isScrollingUp().value
    fabViewModel.onClickAction.value = {
        //TODO go to task creation
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        when (taskListState) {
            is TaskListState.Loading -> Loading()
            is TaskListState.HasTasksState -> TasksList(
                listState = listState,
                taskList = taskListState.taskList,
                onTaskCompletionStateChange = onTaskCompletionStateChange,
                onTaskEditClick = onTaskEditClick,
                onDeleteTask = onDeleteTask
            )

            is TaskListState.NoTasks -> NoTasks()
            is TaskListState.Error -> TasksError(taskListState.errorMessageId, tryToRefresh)
        }
    }
}

@Composable
fun TasksList(
    listState: LazyListState,
    taskList: List<BaseListItem>,
    onDeleteTask: (taskId: Int) -> Unit,
    onTaskCompletionStateChange: (taskId: Int, isCompleted: Boolean) -> Unit,
    onTaskEditClick: (taskId: Int) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.Start
    ) {
        items(items = taskList, key = ::extractKey) { item ->
            when (item) {
                is HeadlineItem -> HeadLine(item = item)
                is SmallHeadlineItem -> SmallHeadLine(item = item)
                is TaskItem -> TaskListElement(
                    taskItem = item,
                    onDeleteTask = onDeleteTask,
                    onTaskEditClick = onTaskEditClick,
                    onTaskCompletedChange = onTaskCompletionStateChange
                )
            }
        }
    }
}

private fun extractKey(item: BaseListItem) = when (item) {
    is HeadlineItem -> item.title
    is SmallHeadlineItem -> item.title
    is TaskItem -> item.id
    else -> ""
}

@Composable
private fun NoTasks() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_no_tasks),
            contentDescription = null
        )
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.task_list_empty_warning)
        )
    }
}

@Composable
private fun TasksError(
    @StringRes errorMessageId: Int,
    tryToRefresh: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null
        )
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = errorMessageId)
        )
        OutlinedButton(onClick = { tryToRefresh.invoke() }) {
            Text(text = stringResource(id = R.string.error_generic_action_title))
        }
    }
}

@Composable
private fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}