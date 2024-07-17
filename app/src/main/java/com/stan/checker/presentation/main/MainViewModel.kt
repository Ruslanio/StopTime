package com.stan.checker.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stan.checker.core.database.dao.TaskDao
import com.stan.checker.util.testdata.TaskTestData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

    fun populateTempData() {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insertAll(*TaskTestData.getEntities().toTypedArray())
        }
    }
}