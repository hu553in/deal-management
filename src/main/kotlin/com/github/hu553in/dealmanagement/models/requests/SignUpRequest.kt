package com.github.hu553in.dealmanagement.models.requests

import com.fasterxml.jackson.annotation.JsonCreator

data class SignUpRequest @JsonCreator constructor(val email: String, val password: String)
