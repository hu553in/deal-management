package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.requests.AddCustomerRequest
import org.springframework.stereotype.Component

@Component
class AddCustomerRequestValidator(
    private val commonsValidator: CommonsValidator
) : Validator<AddCustomerRequest>() {
    override fun validateInternal(errors: MutableMap<String, String>, value: AddCustomerRequest) {
        commonsValidator.isNotEmpty(value.product, errors, "product")
        commonsValidator.hasLengthLessThanOrEqualTo(value.product, 255, errors, "product")
        commonsValidator.isNotEmpty(value.phone, errors, "phone")
        commonsValidator.hasLengthLessThanOrEqualTo(value.phone, 255, errors, "phone")
    }
}
