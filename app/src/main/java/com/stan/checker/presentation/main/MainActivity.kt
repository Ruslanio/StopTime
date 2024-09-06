package com.stan.checker.presentation.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.stan.checker.core.workmanager.WorkManagerHelper
import com.stan.checker.ext.checkPackagesPermission
import com.stan.checker.presentation.app.CheckerApp
import com.stan.checker.presentation.app.LocalActivityStoreProvider
import com.stan.checker.ui.theme.CheckerTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var workManagerHelper: WorkManagerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        val sharedPref = getSharedPreferences("TEMP", Context.MODE_PRIVATE)

        val shouldAddDAta = sharedPref.getBoolean("SHOULD_ADD_TEMP_DATA", true)
        if (shouldAddDAta) {
            sharedPref.edit().putBoolean("SHOULD_ADD_TEMP_DATA", false).apply()
            viewModel.populateTempData()
        }

        workManagerHelper.startUsageMonitoring()

        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalActivityStoreProvider provides this) {
                CheckerTheme {
                    CheckerApp(isPermissionProvided = checkPackagesPermission())
                }
            }
        }
    }
}

