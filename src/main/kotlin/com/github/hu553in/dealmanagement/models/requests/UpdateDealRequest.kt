package com.github.hu553in.dealmanagement.models.requests

import com.fasterxml.jackson.annotation.JsonCreator

data class UpdateDealRequest @JsonCreator constructor(
    val customerId: String? = null,
    val providerId: String? = null,
    val status: String? = null,
    val description: String? = null
)
