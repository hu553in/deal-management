package com.github.hu553in.dealmanagement.entities

enum class DealStatus {
    PENDING,
    REJECTED,
    APPROVED;

    override fun toString() = name
}
