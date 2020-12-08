package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.requests.UpdateUserRequest
import org.springframework.stereotype.Component

@Component
class UpdateUserRequestValidator(
    private val commonsValidator: CommonsValidator,
    private val userRoleValidator: UserRoleValidator
) : Validator<UpdateUserRequest>() {
    override fun validateInternal(errors: MutableMap<String, String>, value: UpdateUserRequest) {
        value.email?.let {
            commonsValidator.isEmail(it, errors, "email")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "email")
        }
        value.password?.let {
            commonsValidator.hasLengthGreaterThanOrEqualTo(it, 8, errors, "password")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "password")
        }
        value.role?.let { userRoleValidator.isUserRole(it, errors, "role") }
    }
}
