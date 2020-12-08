package com.github.hu553in.dealmanagement.repositories.provider

import com.github.hu553in.dealmanagement.entities.Provider
import com.github.hu553in.dealmanagement.exceptions.RepositoryException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ProviderRepository(private val jdbcOperations: JdbcOperations) : IProviderRepository {
    @Throws(RepositoryException::class)
    override fun getById(id: String): Provider {
        val query = "select product, phone, email, created_at, updated_at from provider where id = ?"
        return try {
            jdbcOperations.query(query, arrayOf(id)) { resultSet, _ ->
                Provider(
                    id,
                    resultSet.getString("product"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("created_at"),
                    resultSet.getString("updated_at")
                )
            }.singleOrNull() ?: error("There are zero or multiple providers with passed ID")
        } catch (t: Throwable) {
            throw RepositoryException("Unable to get providers by ID because of: ${t.message}", t)
        }
    }

    @Throws(RepositoryException::class)
    override fun getAll(): List<Provider> {
        val query = "select id, product, phone, email, created_at, updated_at from provider"
        return try {
            jdbcOperations.query(query) { resultSet, _ ->
                Provider(
                    resultSet.getString("id"),
                    resultSet.getString("product"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("created_at"),
                    resultSet.getString("updated_at")
                )
            }
        } catch (t: Throwable) {
            throw RepositoryException("Unable to get all providers because of: ${t.message}", t)
        }
    }

    @Throws(RepositoryException::class)
    override fun add(product: String, phone: String, email: String): String {
        val id = UUID.randomUUID().toString()
        val query = "insert into provider values (?, ?, ?, ?)"
        try {
            jdbcOperations.update(query, id, product, phone, email)
        } catch (t: Throwable) {
            throw RepositoryException("Unable to add provider because of: ${t.message}", t)
        }
        return id
    }

    @Throws(RepositoryException::class)
    override fun update(id: String, product: String?, phone: String?, email: String?) {
        val params = mutableListOf<Pair<String, String>>()
        product?.let { params.add(it to "product = ?") }
        phone?.let { params.add(it to "phone = ?") }
        email?.let { params.add(it to "email = ?") }
        if (params.isNotEmpty()) {
            val query = "update provider set ${params.joinToString(", ") { it.second }} where id = ?"
            try {
                val updatedRowsCount = jdbcOperations.update(query, *params.map { it.first }.toTypedArray(), id)
                if (updatedRowsCount != 1) {
                    error("There are zero or multiple providers with passed ID")
                }
            } catch (t: Throwable) {
                throw RepositoryException("Unable to update provider because of: ${t.message}", t)
            }
        }
    }

    @Throws(RepositoryException::class)
    override fun delete(id: String) {
        val query = "delete from provider where id = ?"
        try {
            val updatedRowsCount = jdbcOperations.update(query, id)
            if (updatedRowsCount != 1) {
                error("There are zero or multiple providers with passed ID")
            }
        } catch (t: Throwable) {
            throw RepositoryException("Unable to delete provider because of: ${t.message}", t)
        }
    }
}
