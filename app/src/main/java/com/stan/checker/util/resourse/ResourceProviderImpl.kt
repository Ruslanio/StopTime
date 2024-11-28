package com.stan.checker.util.resourse

import android.content.Context
import javax.inject.Inject

class ResourceProviderImpl @Inject constructor(private val context: Context) : ResourceProvider {

    override fun getString(stringId: Int) = context.getString(stringId)

    override fun getString(stringId: Int, parameter: Int) = context.getString(stringId, parameter)

    override fun getString(stringId: Int, parameter: String) = context.getString(stringId, parameter)
}