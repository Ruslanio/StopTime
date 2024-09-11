package com.stan.checker.presentation.task.validation

import com.stan.checker.R
import com.stan.checker.util.resourse.ResourceProvider
import com.stan.checker.util.validation.StringRule
import com.stan.checker.util.validation.Validator
import javax.inject.Inject

private const val DESCRIPTION_MAX_LENGTH = 256

class TaskDescriptionValidatorImpl @Inject constructor(
    resourceProvider: ResourceProvider
) : TaskDescriptionValidator() {

    override val ruleSet = mapOf(
        resourceProvider.getString(
            R.string.task_validation_error_description_max_length,
            DESCRIPTION_MAX_LENGTH
        ) to StringRule.MaxLengthRule(
            DESCRIPTION_MAX_LENGTH
        )
    )
}

abstract class TaskDescriptionValidator: Validator<String>()