package com.stan.checker.usage

import com.stan.checker.usage.model.Usage

interface UsageProvider {

    fun getAllUsageForToday(): List<Usage>
}