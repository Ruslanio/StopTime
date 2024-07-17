package com.stan.checker.ui.components.checkbox

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CheckerCheckBox(
    isChecked: Boolean,
    onCheckedChanged: (checkState: Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .size(25.dp)
            .background(Color.Transparent)
            .border(
                BorderStroke(
                    width = if (!isChecked) 1.5.dp else 0.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                ), shape = CircleShape
            )
            .clip(CircleShape)
            .clickable {
                onCheckedChanged.invoke(isChecked.not())
            },
        contentAlignment = Alignment.Center
    ) {

        if (isChecked) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = ""
                )
            }
        }
    }
}