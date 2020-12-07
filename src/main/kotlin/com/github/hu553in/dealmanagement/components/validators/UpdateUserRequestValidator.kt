package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.UpdateUserRequest
import org.springframework.stereotype.Component

@Component
class UpdateUserRequestValidator(
    private val commonsValidator: CommonsValidator,
    private val roleValidator: RoleValidator
) : Validator<UpdateUserRequest>() {
    override fun validation(errors: MutableMap<String, String>, value: UpdateUserRequest) {
        value.email?.let {
            commonsValidator.isEmail(it, errors, "email")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "email")
        }
        value.password?.let {
            commonsValidator.hasLengthGreaterThanOrEqualTo(it, 8, errors, "password")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "password")
        }
        value.role?.let { roleValidator.isRole(it, errors, "role") }
    }
}
