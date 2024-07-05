package com.stan.checker.ui.components.typography

import androidx.annotation.DimenRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.stan.checker.ui.ext.toSp

sealed class TextStyle(
    val weight: TextWeight,
    val alignment: TextAlign,
    val textDecoration: TextDecoration,
) {
    abstract val fontSize: TextUnit @Composable get
    abstract val color: Color @Composable get

    class Body(
        weight: TextWeight = TextWeight.NORMAL,
        alignment: TextAlign = TextAlign.Start,
        textDecoration: TextDecoration = TextDecoration.None
    ) : TextStyle(weight, alignment, textDecoration) {
        override val fontSize @Composable get() = 14.sp

        override val color @Composable get() = MaterialTheme.colorScheme.onSurface
    }
}

enum class TextWeight(val value: FontWeight) {
    NORMAL(FontWeight.Normal),
    MEDIUM(FontWeight.Medium),
    BOLD(FontWeight.Bold)
}

@Composable
private fun resolveResource(@DimenRes id: Int) =
    dimensionResource(id = id).toSp(LocalDensity.current)