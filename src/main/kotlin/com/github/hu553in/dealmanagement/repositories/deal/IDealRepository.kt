package com.github.hu553in.dealmanagement.repositories.deal

import com.github.hu553in.dealmanagement.entities.Deal
import com.github.hu553in.dealmanagement.entities.DealStatus
import com.github.hu553in.dealmanagement.exceptions.RepositoryException

interface IDealRepository {
    @Throws(RepositoryException::class)
    fun getById(id: String): Deal

    @Throws(RepositoryException::class)
    fun getAll(): List<Deal>

    @Throws(RepositoryException::class)
    fun add(customerId: String, providerId: String, status: DealStatus, description: String): String

    @Throws(RepositoryException::class)
    fun add(customerId: String, providerId: String, description: String): String

    @Throws(RepositoryException::class)
    fun update(id: String, customerId: String?, providerId: String?, status: DealStatus?, description: String?)

    @Throws(RepositoryException::class)
    fun delete(id: String)
}
