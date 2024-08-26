package com.stan.checker.presentation.usage

import com.stan.checker.core.mvvm.BaseViewModel
import com.stan.checker.data.repository.UsageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UsageViewModel @Inject constructor(
    private val usageRepository: UsageRepository
) : BaseViewModel<UsageState>() {

    override fun getInitialState() = UsageState.Loading

    fun getUsage() {
        val usage = usageRepository.getAllUsageForToday()
        mutableUiState.update { UsageState.UsageLoaded(usage) }
    }
}