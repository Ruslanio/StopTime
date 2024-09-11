package com.stan.checker.util.validation

interface Rule<V> {
    fun isValid(value: V): Boolean
}

sealed class StringRule : Rule<String> {

    object NotEmptyRule : StringRule() {
        override fun isValid(value: String): Boolean {
            return value.isNotEmpty()
        }
    }

    data class MaxLengthRule(private val maxLength: Int) : StringRule() {
        override fun isValid(value: String): Boolean {
            return value.length <= maxLength
        }
    }

    data class MinLengthRule(private val minLength: Int) : StringRule() {
        override fun isValid(value: String): Boolean {
            return value.length >= minLength
        }
    }
}


