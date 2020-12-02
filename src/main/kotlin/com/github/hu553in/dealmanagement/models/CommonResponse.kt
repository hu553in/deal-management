package com.github.hu553in.dealmanagement.models

import com.fasterxml.jackson.databind.JsonNode

data class CommonResponse(
        val statusCode: Int,
        val data: JsonNode? = null,
        val errors: List<String> = listOf()
)
