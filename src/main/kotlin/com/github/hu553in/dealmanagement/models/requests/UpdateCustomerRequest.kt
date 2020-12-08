package com.github.hu553in.dealmanagement.models.requests

import com.fasterxml.jackson.annotation.JsonCreator

data class UpdateCustomerRequest @JsonCreator constructor(
    val product: String? = null,
    val phone: String? = null
)
