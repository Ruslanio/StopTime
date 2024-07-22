package com.stan.checker.presentation.app

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FabViewModel @Inject constructor() : ViewModel() {
    val isVisible = mutableStateOf(false)
    val onClickAction = mutableStateOf<() -> Unit>({})
    val iconRes = mutableStateOf(Icons.Default.Add)

    fun clearFabInfo() {
        isVisible.value = false
        onClickAction.value = {}
        iconRes.value = Icons.Default.Add
    }
}

@Composable
fun fabViewModel(
    key: String? = null
): FabViewModel {
    val owner = LocalActivityStoreProvider.current
    return viewModel(owner, key)
}
