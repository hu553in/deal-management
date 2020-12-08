package com.github.hu553in.dealmanagement.models.requests

import com.fasterxml.jackson.annotation.JsonCreator

data class AddCustomerRequest @JsonCreator constructor(
    val product: String,
    val phone: String
)
