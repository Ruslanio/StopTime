package com.stan.checker.ui.ext

import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

internal fun Dp.toSp(density: Density): TextUnit = with(density) { this@toSp.toSp() }

internal fun Boolean.crossIf(): TextDecoration {
    return if (this) TextDecoration.LineThrough else TextDecoration.None
}
