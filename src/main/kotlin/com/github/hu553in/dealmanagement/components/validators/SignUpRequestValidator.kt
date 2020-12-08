package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.requests.SignUpRequest
import org.springframework.stereotype.Component

@Component
class SignUpRequestValidator(private val commonsValidator: CommonsValidator) : Validator<SignUpRequest>() {
    override fun validateInternal(errors: MutableMap<String, String>, value: SignUpRequest) {
        commonsValidator.isEmail(value.email, errors, "email")
        commonsValidator.hasLengthLessThanOrEqualTo(value.email, 255, errors, "email")
        commonsValidator.hasLengthGreaterThanOrEqualTo(value.password, 8, errors, "password")
        commonsValidator.hasLengthLessThanOrEqualTo(value.password, 255, errors, "password")
    }
}
