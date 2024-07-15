package com.stan.checker.ui.components.permission

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermission(
    permissionName: String,
    onGranted: () -> Unit,
) {
    val permission = rememberPermissionState(permission = permissionName)

    if (permission.status == PermissionStatus.Granted) {
        onGranted.invoke()
    } else {
        SideEffect {
            permission.launchPermissionRequest()
        }
    }
}

@Composable
fun RequestPermission(
    permissionIntent: Intent,
    onResult: (result: PermissionResult) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val result = when (it.resultCode) {
            Activity.RESULT_OK -> PermissionResult.OK
            Activity.RESULT_CANCELED -> PermissionResult.CANCELLED
            else -> PermissionResult.CANCELLED
        }
        onResult.invoke(result)
    }

    LaunchedEffect(Unit) {
        launcher.launch(permissionIntent)
    }
}

enum class PermissionResult {
    OK, CANCELLED
}