package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.requests.SignInRequest
import org.springframework.stereotype.Component

@Component
class SignInRequestValidator(private val commonsValidator: CommonsValidator) : Validator<SignInRequest>() {
    override fun validateInternal(errors: MutableMap<String, String>, value: SignInRequest) {
        commonsValidator.isEmail(value.email, errors, "email")
        commonsValidator.hasLengthLessThanOrEqualTo(value.email, 255, errors, "email")
        commonsValidator.hasLengthGreaterThanOrEqualTo(value.password, 8, errors, "password")
        commonsValidator.hasLengthLessThanOrEqualTo(value.password, 255, errors, "password")
    }
}
