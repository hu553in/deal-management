package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.requests.UpdateProviderRequest
import org.springframework.stereotype.Component

@Component
class UpdateProviderRequestValidator(
    private val commonsValidator: CommonsValidator
) : Validator<UpdateProviderRequest>() {
    override fun validateInternal(errors: MutableMap<String, String>, value: UpdateProviderRequest) {
        value.product?.let {
            commonsValidator.isNotEmpty(it, errors, "product")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "product")
        }
        value.phone?.let {
            commonsValidator.isNotEmpty(it, errors, "phone")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "phone")
        }
        value.email?.let {
            commonsValidator.isEmail(it, errors, "email")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "email")
        }
    }
}
