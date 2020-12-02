package com.github.hu553in.dealmanagement.models

import com.fasterxml.jackson.annotation.JsonCreator

data class SignInRequest @JsonCreator constructor(val email: String, val password: String)
