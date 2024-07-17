package com.stan.checker.presentation.task.list.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stan.checker.ext.appendIfNotNull
import com.stan.checker.presentation.model.DateStatus
import com.stan.checker.presentation.model.TaskItem
import com.stan.checker.ui.components.card.CheckerCard
import com.stan.checker.ui.components.checkbox.CheckerCheckBox
import com.stan.checker.ui.components.typography.CheckerText
import com.stan.checker.ui.components.typography.TextStyle
import com.stan.checker.ui.ext.crossIf

@Composable
fun TaskListElement(
    taskItem: TaskItem,
    onDeleteTask: (taskId: Int) -> Unit,
    onTaskEditClick: (taskId: Int) -> Unit,
    onTaskCompletedChange: (taskId: Int, isCompleted: Boolean) -> Unit
) {

    CheckerCard {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            CheckerCheckBox(
                isChecked = taskItem.isCompleted,
                onCheckedChanged = {
                    onTaskCompletedChange(taskItem.id, it)
                }
            )

            Row(modifier = Modifier
                .weight(1f)
                .clickable {
                    onTaskEditClick.invoke(taskItem.id)
                }) {
                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    CheckerText(
                        text = taskItem.title,
                        style = TextStyle.Body(
                            textDecoration = taskItem.isCompleted.crossIf()
                        )
                    )
                    taskItem.description?.let {
                        Spacer(modifier = Modifier.height(4.dp))
                        CheckerText(
                            text = it,
                            style = TextStyle.Body(
                                textDecoration = taskItem.isCompleted.crossIf()
                            )
                        )
                    }

                    when (taskItem.dateStatus) {
                        is DateStatus.Expired -> {
                            Spacer(modifier = Modifier.height(4.dp))
                            CheckerText(
                                text = taskItem.dateStatus.date.appendIfNotNull(taskItem.dateStatus.time),
                                style = TextStyle.Error()
                            )
                        }

                        is DateStatus.Regular -> {
                            taskItem.dateStatus.time?.let { time ->
                                Spacer(modifier = Modifier.height(4.dp))
                                CheckerText(
                                    text = time,
                                    style = TextStyle.Body(
                                        textDecoration = taskItem.isCompleted.crossIf()
                                    )
                                )
                            }
                        }

                        else -> Unit
                    }
                }

            }
            if (taskItem.isCompleted) {
                IconButton(
                    modifier = Modifier.size(25.dp),
                    onClick = { onDeleteTask(taskItem.id) }) {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}