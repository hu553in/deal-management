package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.entities.UserRole
import org.springframework.stereotype.Component

@Component
class UserRoleValidator {
    fun isUserRole(
        value: String?,
        errors: MutableMap<String, String>,
        field: String,
        key: String = "Should be valid user role"
    ) {
        if (!errors.containsKey(field)) {
            if (value == null) {
                errors[field] = key
            } else {
                try {
                    UserRole.valueOf(value)
                } catch (t: Throwable) {
                    errors[field] = key
                }
            }
        }
    }
}
