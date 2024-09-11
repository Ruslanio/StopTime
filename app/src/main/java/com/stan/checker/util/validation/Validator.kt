package com.stan.checker.util.validation

abstract class Validator<V> {

    abstract val ruleSet: Map<String, Rule<V>>

    fun validate(valueToCheck: V): ValidationResult {
        val errors = mutableListOf<String>()
        ruleSet.forEach { (errorMessage, rule) ->
            if (!rule.isValid(valueToCheck)) {
                errors.add(errorMessage)
            }
        }
        return if (errors.isNotEmpty()) {
            ValidationResult.NotValid(errors)
        } else {
            ValidationResult.Valid
        }
    }
}

sealed class ValidationResult {
    fun isSuccess() = this == Valid
    fun getErrorMessage(): String? {
        return if (this is NotValid) {
            first()
        } else {
            null
        }
    }

    object Valid : ValidationResult()
    data class NotValid(val errorMessageList: List<String>) : ValidationResult() {
        fun first() = errorMessageList.first()
    }
}