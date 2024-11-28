package com.stan.checker.domain.usecases

import com.stan.checker.data.repository.UsageRepository
import com.stan.checker.presentation.model.UsageItem
import javax.inject.Inject

class GetAppsOverLimitUseCase @Inject constructor(
    private val usageRepository: UsageRepository,
    private val isAppOverTheLimitUseCase: IsAppOverTheLimitUseCase,
) {

    operator fun invoke(): List<UsageItem> {
        return usageRepository.getAllUsageForToday().filter {
            isAppOverTheLimitUseCase(it)
        }
    }
}