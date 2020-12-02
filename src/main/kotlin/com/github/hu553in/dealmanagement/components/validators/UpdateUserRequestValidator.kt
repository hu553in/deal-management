package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.UpdateUserRequest
import org.springframework.stereotype.Component

@Component
class UpdateUserRequestValidator(
        private val commonsValidator: CommonsValidator,
        private val roleValidator: RoleValidator
) : Validator<UpdateUserRequest>() {
    override fun validation(errors: MutableMap<String, String>, value: UpdateUserRequest) {
        commonsValidator.isEmail(value.email, errors, "email")
        commonsValidator.shorterThanOrIsTheSameLengthAs(value.email, 255, errors, "email")
        commonsValidator.longerThanOrIsTheSameLengthAs(value.email, 8, errors, "password")
        commonsValidator.shorterThanOrIsTheSameLengthAs(value.email, 255, errors, "password")
        roleValidator.isRole(value.role, errors, "role")
    }
}
