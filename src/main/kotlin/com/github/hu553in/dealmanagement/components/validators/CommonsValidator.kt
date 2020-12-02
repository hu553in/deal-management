package com.github.hu553in.dealmanagement.components.validators

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CommonsValidator {
    companion object {
        private val EMAIL_REGEX = Regex("^.+@.+$", RegexOption.IGNORE_CASE)
    }

    fun isNotNullOrEmpty(
            value: String?,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should not be null or empty"
    ) {
        if (!errors.containsKey(field) && (value == null || value.trim().isEmpty())) {
            errors[field] = key
        }
    }

    fun shorterThan(
            value: String?,
            bound: Int,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should be shorter than $bound"
    ) {
        if (value != null && !errors.containsKey(field)) {
            if (value.length >= bound) {
                errors[field] = key
            }
        }
    }

    fun shorterThanOrIsTheSameLengthAs(
            value: String?,
            bound: Long,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should be shorter than or be the same length as $bound"
    ) {
        if (value != null && !errors.containsKey(field)) {
            if (value.length > bound) {
                errors[field] = key
            }
        }
    }

    fun longerThan(
            value: String?,
            bound: Long,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should be longer than $bound"
    ) {
        if (value != null && !errors.containsKey(field)) {
            if (value.length <= bound) {
                errors[field] = key
            }
        }
    }

    fun longerThanOrIsTheSameLengthAs(
            value: String?,
            bound: Long,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should be longer than or be the same length as $bound"
    ) {
        if (value != null && !errors.containsKey(field)) {
            if (value.length < bound) {
                errors[field] = key
            }
        }
    }

    fun isEmail(
            value: String?,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should be valid email"
    ) {
        if (!errors.containsKey(field) && (value == null || !EMAIL_REGEX.matches(value))) {
            errors[field] = key
        }
    }

    fun isNotNull(
            value: Any?,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should be not null"
    ) {
        if (!errors.containsKey(field) && value == null) {
            errors[field] = key
        }
    }

    fun isUuid(
            value: String?,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should be valid UUID"
    ) {
        if (!errors.containsKey(field)) {
            try {
                UUID.fromString(value)
            } catch (t: Throwable) {
                errors[field] = key
            }
        }
    }
}
