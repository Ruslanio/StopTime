package com.stan.checker.presentation.usage

import com.stan.checker.core.mvvm.BaseState
import com.stan.checker.presentation.model.UsageItem

sealed class UsageState : BaseState {

    data object Loading : UsageState()

    data class UsageLoaded(val usageList: List<UsageItem>) : UsageState()

}