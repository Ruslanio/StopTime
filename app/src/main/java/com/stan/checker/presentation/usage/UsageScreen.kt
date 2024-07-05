package com.stan.checker.presentation.usage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stan.checker.ui.components.card.CheckerCard
import com.stan.checker.ui.components.typography.CheckerText
import com.stan.checker.ui.components.typography.TextStyle
import com.stan.checker.usage.model.Usage

@Composable
fun UsageScreen(
    viewModel: UsageViewModel = hiltViewModel()
) {
    val usageState: UsageState by viewModel.uiState.collectAsStateWithLifecycle()

    UsageScreenContent(state = usageState)
}

@Composable
private fun UsageScreenContent(
    state: UsageState
) {
    when (state) {
        UsageState.Loading -> Loading()
        is UsageState.UsageLoaded -> UsageList(state = state)
    }
}

@Composable
private fun UsageList(
    state: UsageState.UsageLoaded
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {

            Spacer(modifier = Modifier.height(2.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(state.usageList.size) {
                    UsageItem(info = state.usageList[it])
                }

                item {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
private fun Loading() {
    Text(text = "Loading")
}

@Composable
private fun UsageItem(
    modifier: Modifier = Modifier,
    info: Usage
) {
    CheckerCard(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            CheckerText(text = "name = ${info.name ?: "Unresolved"}", style = TextStyle.Body())
            CheckerText(text = "package = ${info.packageName}", style = TextStyle.Body())
            Spacer(modifier = Modifier.height(2.dp))
            CheckerText(text = "time = ${info.timeInUse / 1000} sec.", style = TextStyle.Body())
        }
    }
}