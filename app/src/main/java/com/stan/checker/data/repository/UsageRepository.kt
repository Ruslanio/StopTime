package com.stan.checker.data.repository

import com.stan.checker.presentation.model.UsageItem

interface UsageRepository {

    fun getAllUsageForToday(): List<UsageItem>
    fun getCurrentForeground(): UsageItem
}