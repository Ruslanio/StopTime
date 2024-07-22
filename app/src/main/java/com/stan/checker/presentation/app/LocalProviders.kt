package com.stan.checker.presentation.app

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

val LocalActivityStoreProvider = compositionLocalOf<ViewModelStoreOwner> {
    object : ViewModelStoreOwner {
        override val viewModelStore: ViewModelStore
            get() = ViewModelStore()
    }
}