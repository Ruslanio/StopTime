package com.stan.checker.util.resourse

import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes stringId: Int): String

    fun getString(@StringRes stringId: Int, parameter: Int): String

    fun getString(@StringRes stringId: Int, parameter: String): String

}