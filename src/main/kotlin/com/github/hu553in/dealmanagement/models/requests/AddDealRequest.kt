package com.github.hu553in.dealmanagement.models.requests

import com.fasterxml.jackson.annotation.JsonCreator

data class AddDealRequest @JsonCreator constructor(
    val customerId: String,
    val providerId: String,
    val description: String
)
