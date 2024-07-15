package com.stan.checker.data.repository

import com.stan.checker.usage.model.Usage

interface UsageRepository {

    fun getAllUsageForToday(): List<Usage>
}