package com.github.hu553in.dealmanagement.components.validators

import org.springframework.stereotype.Component

@Component
class DealActionValidator : Validator<String>() {
    override fun validateInternal(errors: MutableMap<String, String>, value: String) {
        if (!errors.containsKey("action")) {
            val validDealActions = listOf("approve", "reject")
            if (!validDealActions.contains(value.toLowerCase())) {
                errors["action"] = "Should be valid deal action"
            }
        }
    }
}
