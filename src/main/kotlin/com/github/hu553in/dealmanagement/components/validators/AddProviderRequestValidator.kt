package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.requests.AddProviderRequest
import org.springframework.stereotype.Component

@Component
class AddProviderRequestValidator(
    private val commonsValidator: CommonsValidator
) : Validator<AddProviderRequest>() {
    override fun validateInternal(errors: MutableMap<String, String>, value: AddProviderRequest) {
        commonsValidator.isNotEmpty(value.product, errors, "product")
        commonsValidator.hasLengthLessThanOrEqualTo(value.product, 255, errors, "product")
        commonsValidator.isNotEmpty(value.phone, errors, "phone")
        commonsValidator.hasLengthLessThanOrEqualTo(value.phone, 255, errors, "phone")
        commonsValidator.isEmail(value.email, errors, "email")
        commonsValidator.hasLengthLessThanOrEqualTo(value.email, 255, errors, "email")
    }
}
