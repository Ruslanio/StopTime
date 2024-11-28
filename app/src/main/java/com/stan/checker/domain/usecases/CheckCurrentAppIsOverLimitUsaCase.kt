package com.stan.checker.domain.usecases

import com.stan.checker.data.repository.UsageRepository
import com.stan.checker.presentation.model.UsageItem
import javax.inject.Inject

class CheckCurrentAppIsOverLimitUsaCase @Inject constructor(
    private val usageRepository: UsageRepository,
    private val getAppsOverLimitUseCase: GetAppsOverLimitUseCase
) {
    private val blackList = listOf(
        "com.stan.checker",
        "com.huawei.android.launcher",
        "com.android.systemui"
    )

    operator fun invoke(): UsageItem? {
        val currentApp = usageRepository.getCurrentForeground()
        return getAppsOverLimitUseCase.invoke()
            .filter { blackList.contains(it.packageName).not() }
            .find { it.packageName == currentApp.packageName }
    }
}