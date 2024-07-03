package com.stan.checker

import android.app.usage.UsageStatsManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.stan.checker.presentation.CheckerApp
import com.stan.checker.ui.theme.CheckerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        startActivityForResult(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 1)

        enableEdgeToEdge()
        setContent {
            CheckerTheme {
                CheckerApp()
            }
        }
    }

    private fun testUsage() {
        val now = System.currentTimeMillis()
        val usageManager = this.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        val stats = usageManager.queryAndAggregateUsageStats(
            now - 60000,
            now
        ).values.toList()
            .sortedByDescending { it.totalTimeInForeground }
            .filter { it.totalTimeInForeground / 1000 != 0L }

        stats.size
    }
}

