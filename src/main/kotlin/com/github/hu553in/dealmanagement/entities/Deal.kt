package com.github.hu553in.dealmanagement.entities

data class Deal(
    val id: String,
    val customerId: String,
    val providerId: String,
    val status: DealStatus,
    val description: String,
    val createdAt: String,
    val updatedAt: String
)
