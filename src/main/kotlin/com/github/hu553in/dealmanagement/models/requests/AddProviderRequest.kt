package com.github.hu553in.dealmanagement.models.requests

import com.fasterxml.jackson.annotation.JsonCreator

data class AddProviderRequest @JsonCreator constructor(
    val product: String,
    val phone: String,
    val email: String
)
