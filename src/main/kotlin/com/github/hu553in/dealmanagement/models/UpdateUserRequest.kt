package com.github.hu553in.dealmanagement.models

import com.fasterxml.jackson.annotation.JsonCreator

data class UpdateUserRequest @JsonCreator constructor(
    val email: String? = null,
    val password: String? = null,
    val role: String? = null
)
