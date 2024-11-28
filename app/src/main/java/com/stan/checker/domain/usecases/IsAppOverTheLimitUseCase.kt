package com.stan.checker.domain.usecases

import com.stan.checker.presentation.model.UsageItem
import javax.inject.Inject

private const val HOUR = 1000 * 60 * 60L
private const val DEFAULT_LIMIT = 1

//TODO add user customizable limits
class IsAppOverTheLimitUseCase @Inject constructor() {

    operator fun invoke(usageItem: UsageItem): Boolean {
        return (usageItem.timeStamp / HOUR) % 24 >= DEFAULT_LIMIT
    }
}