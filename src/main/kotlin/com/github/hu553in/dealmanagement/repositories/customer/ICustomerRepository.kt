package com.github.hu553in.dealmanagement.repositories.customer

import com.github.hu553in.dealmanagement.entities.Customer
import com.github.hu553in.dealmanagement.exceptions.RepositoryException

interface ICustomerRepository {
    @Throws(RepositoryException::class)
    fun getById(id: String): Customer

    @Throws(RepositoryException::class)
    fun getAll(): List<Customer>

    @Throws(RepositoryException::class)
    fun add(product: String, phone: String): String

    @Throws(RepositoryException::class)
    fun update(id: String, product: String?, phone: String?)

    @Throws(RepositoryException::class)
    fun delete(id: String)
}
