package com.stan.checker.core.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<S : BaseState> : ViewModel() {

    protected val mutableUiState by lazy { MutableStateFlow(getInitialState()) }
    val uiState: StateFlow<S> = mutableUiState
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            mutableUiState.value
        )

    @Suppress("UNCHECKED_CAST")
    protected fun <C : S> updateStateAs(func: (state: C) -> S) {
        mutableUiState.update {
            func(it as C)
        }
    }

    protected abstract fun getInitialState(): S
}