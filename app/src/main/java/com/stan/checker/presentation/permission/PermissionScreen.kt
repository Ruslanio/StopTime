package com.stan.checker.presentation.permission

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.stan.checker.ext.checkPackagesPermission
import com.stan.checker.ui.components.permission.PermissionResult
import com.stan.checker.ui.components.permission.RequestPermission
import com.stan.checker.ui.components.typography.CheckerText
import com.stan.checker.ui.components.typography.TextStyle

@Composable
fun PermissionScreen(
    navigateToHome: () -> Unit
) {
    val isPermissionProvided = LocalContext.current.checkPackagesPermission()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        if (lifecycleState == Lifecycle.State.RESUMED && isPermissionProvided) {
            navigateToHome.invoke()
        }
    }

    var shouldGoToSettings by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CheckerText(
                text = "To use this application properly, it is necessary to provide permission in the Settings screen",
                style = TextStyle.Body(alignment = TextAlign.Center)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { shouldGoToSettings = true }
            ) {
                Text(text = "Go to Settings")
            }

            if (shouldGoToSettings) {
                Requester(navigateToHome)
                shouldGoToSettings = false
            }
        }
    }
}

@Composable
private fun Requester(
    navigateToHome: () -> Unit
) {
    RequestPermission(permissionIntent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)) {
        if (it == PermissionResult.OK) {
            navigateToHome.invoke()
        }
    }
}