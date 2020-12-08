package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.requests.AddDealRequest
import org.springframework.stereotype.Component

@Component
class AddDealRequestValidator(private val commonsValidator: CommonsValidator) : Validator<AddDealRequest>() {
    override fun validateInternal(errors: MutableMap<String, String>, value: AddDealRequest) {
        commonsValidator.isNotEmpty(value.customerId, errors, "customerId")
        commonsValidator.hasLengthLessThanOrEqualTo(value.customerId, 255, errors, "customerId")
        commonsValidator.isNotEmpty(value.providerId, errors, "providerId")
        commonsValidator.hasLengthLessThanOrEqualTo(value.providerId, 255, errors, "providerId")
        commonsValidator.isNotEmpty(value.description, errors, "description")
        commonsValidator.hasLengthLessThanOrEqualTo(value.description, 255, errors, "description")
    }
}
