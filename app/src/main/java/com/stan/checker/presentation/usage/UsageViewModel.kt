package com.stan.checker.presentation.usage

import com.stan.checker.core.mvvm.BaseViewModel
import com.stan.checker.usage.UsageProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UsageViewModel @Inject constructor(
    private val usageProvider: UsageProvider
) : BaseViewModel<UsageState>() {

    override fun getInitialState() = UsageState.Loading

    fun getUsage() {
        val usage = usageProvider.getAllUsageForToday()
        println(usage)
        mutableUiState.update { UsageState.UsageLoaded(usage) }
    }
}