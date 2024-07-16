package com.stan.checker.data.repository

import com.stan.checker.presentation.model.Usage

interface UsageRepository {

    fun getAllUsageForToday(): List<Usage>
}