package com.stan.checker.presentation.usage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stan.checker.ui.theme.CheckerTheme
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
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column {

            Greeting(
                name = "Today's usage",
                modifier = Modifier.padding(innerPadding)
            )
            Spacer(modifier = Modifier.height(2.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.usageList.size) {
                    Item(info = state.usageList[it])
                }
            }
        }
    }
}

@Composable
private fun Loading() {
    Greeting(name = "Loading")
}

@Composable
private fun Item(info: Usage) {
    Column {
        Text(text = "name = ${info.name ?: "Unresolved"}")
        Text(text = "package = ${info.packageName}")
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = "time = ${info.timeInUse / 1000} sec.")
    }
}

@Composable
private fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CheckerTheme {
        Greeting("Android")
    }
}