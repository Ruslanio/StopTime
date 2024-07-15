package com.stan.checker.presentation.home

import com.stan.checker.core.mvvm.BaseState

sealed class HomeState : BaseState {

    data object Initial : HomeState()
}