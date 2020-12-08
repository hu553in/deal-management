package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.entities.DealStatus
import org.springframework.stereotype.Component

@Component
class DealStatusValidator {
    fun isDealStatus(
        value: String?,
        errors: MutableMap<String, String>,
        field: String,
        key: String = "Should be valid deal status"
    ) {
        if (!errors.containsKey(field)) {
            if (value == null) {
                errors[field] = key
            } else {
                try {
                    DealStatus.valueOf(value)
                } catch (t: Throwable) {
                    errors[field] = key
                }
            }
        }
    }
}
