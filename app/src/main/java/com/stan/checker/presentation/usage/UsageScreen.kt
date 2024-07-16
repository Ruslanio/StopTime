package com.stan.checker.presentation.usage

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.stan.checker.R
import com.stan.checker.ext.checkPackagesPermission
import com.stan.checker.ui.components.card.CheckerCard
import com.stan.checker.ui.components.permission.RequestPermission
import com.stan.checker.ui.components.typography.CheckerText
import com.stan.checker.ui.components.typography.TextStyle
import com.stan.checker.usage.model.Usage

@Composable
fun UsageScreen(
    viewModel: UsageViewModel = hiltViewModel()
) {
    val usageState: UsageState by viewModel.uiState.collectAsStateWithLifecycle()

    UsageScreenContent(state = usageState, fetchForUsage = viewModel::getUsage)
}

@Composable
private fun UsageScreenContent(
    state: UsageState,
    fetchForUsage: () -> Unit
) {
    when (state) {
        UsageState.Loading -> Loading(fetchForUsage)
        is UsageState.UsageLoaded -> UsageList(state = state)
    }
}

@Composable
private fun UsageList(
    state: UsageState.UsageLoaded
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(horizontal = 8.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
            }

            items(state.usageList.size) {
                UsageItem(info = state.usageList[it])
            }

            item {
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun Loading(
    fetchForUsage: () -> Unit
) {
    Column {
        Text(text = "Loading")

        val isPermissionProvided = LocalContext.current.checkPackagesPermission()
        if (isPermissionProvided.not()) {
            RequestPermission(
                permissionIntent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS),
                onResult = {
                    fetchForUsage.invoke()
                }
            )
        } else {
            fetchForUsage.invoke()
        }
    }
}

@Composable
private fun UsageItem(
    modifier: Modifier = Modifier,
    info: Usage
) {
    CheckerCard(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            val painter = if (info.icon != null) {
                rememberDrawablePainter(drawable = info.icon)
            } else {
                painterResource(id = R.drawable.icon_placeholder)
            }

            Image(
                modifier = Modifier.size(45.dp),
                painter = painter,
                contentDescription = ""
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                CheckerText(text = info.name, style = TextStyle.Body())
                Spacer(modifier = Modifier.height(2.dp))
                CheckerText(text = "${info.timeInUse / 1000} sec.", style = TextStyle.Body())
            }
        }
    }
}