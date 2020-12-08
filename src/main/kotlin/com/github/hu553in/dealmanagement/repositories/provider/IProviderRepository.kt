package com.github.hu553in.dealmanagement.repositories.provider

import com.github.hu553in.dealmanagement.entities.Provider
import com.github.hu553in.dealmanagement.exceptions.RepositoryException

interface IProviderRepository {
    @Throws(RepositoryException::class)
    fun getById(id: String): Provider

    @Throws(RepositoryException::class)
    fun getAll(): List<Provider>

    @Throws(RepositoryException::class)
    fun add(product: String, phone: String, email: String): String

    @Throws(RepositoryException::class)
    fun update(id: String, product: String?, phone: String?, email: String?)

    @Throws(RepositoryException::class)
    fun delete(id: String)
}
