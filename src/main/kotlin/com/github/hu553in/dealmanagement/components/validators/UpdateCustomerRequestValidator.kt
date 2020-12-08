package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.requests.UpdateCustomerRequest
import org.springframework.stereotype.Component

@Component
class UpdateCustomerRequestValidator(
    private val commonsValidator: CommonsValidator
) : Validator<UpdateCustomerRequest>() {
    override fun validateInternal(errors: MutableMap<String, String>, value: UpdateCustomerRequest) {
        value.product?.let {
            commonsValidator.isNotEmpty(it, errors, "product")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "product")
        }
        value.phone?.let {
            commonsValidator.isNotEmpty(it, errors, "phone")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "phone")
        }
    }
}
