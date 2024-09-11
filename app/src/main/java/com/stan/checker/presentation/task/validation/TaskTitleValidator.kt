package com.stan.checker.presentation.task.validation

import com.stan.checker.R
import com.stan.checker.util.resourse.ResourceProvider
import com.stan.checker.util.validation.StringRule
import com.stan.checker.util.validation.Validator
import javax.inject.Inject

private const val NAME_MIN_LENGTH = 3
private const val NAME_MAX_LENGTH = 64

class TaskTitleValidatorImpl @Inject constructor(
    resourceProvider: ResourceProvider
): TaskTitleValidator() {

    override val ruleSet = mapOf(
        resourceProvider.getString(R.string.task_validation_error_name_not_empty) to StringRule.NotEmptyRule,
        resourceProvider.getString(
            R.string.task_validation_error_name_max_length,
            NAME_MAX_LENGTH
        ) to StringRule.MaxLengthRule(
            NAME_MAX_LENGTH
        ),
        resourceProvider.getString(
            R.string.task_validation_error_name_min_length,
            NAME_MIN_LENGTH
        ) to StringRule.MinLengthRule(
            NAME_MIN_LENGTH
        )
    )
}

abstract class TaskTitleValidator : Validator<String>()