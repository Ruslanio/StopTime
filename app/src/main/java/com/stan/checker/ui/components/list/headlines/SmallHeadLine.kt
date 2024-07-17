package com.stan.checker.ui.components.list.headlines

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stan.checker.ui.components.typography.CheckerText
import com.stan.checker.ui.components.typography.TextStyle

@Composable
fun SmallHeadLine(
    modifier: Modifier = Modifier,
    item: SmallHeadlineItem
) {
    CheckerText(
        modifier = modifier,
        text = item.title,
        style = TextStyle.HeadingSmall()
    )
}