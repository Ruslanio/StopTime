package com.stan.checker.ui.components.typography

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun CheckerText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle,
    overflow: TextOverflow = TextOverflow.Clip,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        modifier = modifier,
        text = text,
        overflow = overflow,
        maxLines = maxLines,
        fontSize = style.fontSize,
        textAlign = style.alignment,
        fontWeight = style.weight.value,
        color = style.color,
        textDecoration = style.textDecoration
    )
}