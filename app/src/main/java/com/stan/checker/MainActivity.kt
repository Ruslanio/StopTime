package com.stan.checker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.stan.checker.ext.checkPackagesPermission
import com.stan.checker.presentation.CheckerApp
import com.stan.checker.ui.theme.CheckerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()
        setContent {
            CheckerTheme {
                CheckerApp(isPermissionProvided = checkPackagesPermission())
            }
        }
    }
}

