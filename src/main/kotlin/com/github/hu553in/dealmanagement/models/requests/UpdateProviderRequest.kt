package com.github.hu553in.dealmanagement.models.requests

import com.fasterxml.jackson.annotation.JsonCreator

data class UpdateProviderRequest @JsonCreator constructor(
    val product: String? = null,
    val phone: String? = null,
    val email: String? = null
)
