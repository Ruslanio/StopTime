package com.stan.checker.ui.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@VisibleForTesting
val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = Purple90,
    onPrimaryContainer = Purple10,
    secondary = Orange40,
    onSecondary = Color.White,
    secondaryContainer = Orange90,
    onSecondaryContainer = Orange10,
    tertiary = Blue40,
    onTertiary = Color.White,
    tertiaryContainer = Blue90,
    onTertiaryContainer = Blue10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = DarkPurpleGray99,
    onBackground = DarkPurpleGray10,
    surface = DarkPurpleGray99,
    onSurface = DarkPurpleGray10,
    surfaceVariant = PurpleGray90,
    onSurfaceVariant = PurpleGray30,
    outline = PurpleGray50
)

@Composable
fun CheckerTheme(
    content: @Composable () -> Unit
) = CheckerTheme(
    content = content,
    disableDynamicTheming = true
)

@Composable
private fun CheckerTheme(
    content: @Composable () -> Unit,
    disableDynamicTheming: Boolean
) {
    val colorScheme = if (!disableDynamicTheming && supportsDynamicTheming()) {
        dynamicDarkColorScheme(LocalContext.current)
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CheckerTypography,
        content = content
    )
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S