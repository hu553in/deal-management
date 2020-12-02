package com.github.hu553in.dealmanagement.components.validators

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CommonsValidator {
    companion object {
        private val EMAIL_REGEX = Regex("^\\S+@\\S+\\.\\S+$")
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

    fun hasLengthLessThan(
            value: String?,
            bound: Int,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should have length less than $bound"
    ) {
        if (value != null && !errors.containsKey(field)) {
            if (value.length >= bound) {
                errors[field] = key
            }
        }
    }

    fun hasLengthLessThanOrEqualTo(
            value: String?,
            bound: Long,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should have length less than or equal to $bound"
    ) {
        if (value != null && !errors.containsKey(field)) {
            if (value.length > bound) {
                errors[field] = key
            }
        }
    }

    fun hasLengthGreaterThan(
            value: String?,
            bound: Long,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should have length greater than $bound"
    ) {
        if (value != null && !errors.containsKey(field)) {
            if (value.length <= bound) {
                errors[field] = key
            }
        }
    }

    fun hasLengthGreaterThanOrEqualTo(
            value: String?,
            bound: Long,
            errors: MutableMap<String, String>,
            field: String,
            key: String = "Should have length greater than or equal to $bound"
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
