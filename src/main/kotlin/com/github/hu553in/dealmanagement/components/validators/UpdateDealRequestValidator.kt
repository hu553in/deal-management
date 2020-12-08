package com.github.hu553in.dealmanagement.components.validators

import com.github.hu553in.dealmanagement.models.requests.UpdateDealRequest
import org.springframework.stereotype.Component

@Component
class UpdateDealRequestValidator(
    private val commonsValidator: CommonsValidator,
    private val dealStatusValidator: DealStatusValidator
) : Validator<UpdateDealRequest>() {
    override fun validateInternal(errors: MutableMap<String, String>, value: UpdateDealRequest) {
        value.customerId?.let {
            commonsValidator.isNotEmpty(it, errors, "customerId")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "customerId")
        }
        value.providerId?.let {
            commonsValidator.isNotEmpty(it, errors, "providerId")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "providerId")
        }
        value.status?.let { dealStatusValidator.isDealStatus(it, errors, "status") }
        value.description?.let {
            commonsValidator.isNotEmpty(it, errors, "description")
            commonsValidator.hasLengthLessThanOrEqualTo(it, 255, errors, "description")
        }
    }
}
