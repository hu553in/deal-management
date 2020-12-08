package com.github.hu553in.dealmanagement.repositories.deal

import com.github.hu553in.dealmanagement.entities.Deal
import com.github.hu553in.dealmanagement.entities.DealStatus
import com.github.hu553in.dealmanagement.exceptions.RepositoryException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class DealRepository(private val jdbcOperations: JdbcOperations) : IDealRepository {
    @Throws(RepositoryException::class)
    override fun getById(id: String): Deal {
        val query = "select customer_id, provider_id, status, description, created_at, updated_at " +
                "from deal where id = ?"
        return try {
            jdbcOperations.query(query, arrayOf(id)) { resultSet, _ ->
                Deal(
                    id,
                    resultSet.getString("customer_id"),
                    resultSet.getString("provider_id"),
                    DealStatus.valueOf(resultSet.getString("status")),
                    resultSet.getString("description"),
                    resultSet.getString("created_at"),
                    resultSet.getString("updated_at")
                )
            }.singleOrNull() ?: error("There are zero or multiple deals with passed ID")
        } catch (t: Throwable) {
            throw RepositoryException("Unable to get deal by ID because of: ${t.message}", t)
        }
    }

    @Throws(RepositoryException::class)
    override fun getAll(): List<Deal> {
        val query = "select id, customer_id, provider_id, status, description, created_at, updated_at from deal"
        return try {
            jdbcOperations.query(query) { resultSet, _ ->
                Deal(
                    resultSet.getString("id"),
                    resultSet.getString("customer_id"),
                    resultSet.getString("provider_id"),
                    DealStatus.valueOf(resultSet.getString("status")),
                    resultSet.getString("description"),
                    resultSet.getString("created_at"),
                    resultSet.getString("updated_at")
                )
            }
        } catch (t: Throwable) {
            throw RepositoryException("Unable to get all deals because of: ${t.message}", t)
        }
    }

    @Throws(RepositoryException::class)
    override fun add(customerId: String, providerId: String, status: DealStatus, description: String): String {
        val id = UUID.randomUUID().toString()
        val query = "insert into deal values (?, ?, ?, ?::status_type, ?)"
        try {
            jdbcOperations.update(query, id, customerId, providerId, status.name, description)
        } catch (t: Throwable) {
            throw RepositoryException("Unable to add deal because of: ${t.message}", t)
        }
        return id
    }

    @Throws(RepositoryException::class)
    override fun add(customerId: String, providerId: String, description: String): String {
        val id = UUID.randomUUID().toString()
        val query = "insert into deal values (?, ?, ?, ?)"
        try {
            jdbcOperations.update(query, id, customerId, providerId, description)
        } catch (t: Throwable) {
            throw RepositoryException("Unable to add deal because of: ${t.message}", t)
        }
        return id
    }

    @Throws(RepositoryException::class)
    override fun update(
        id: String,
        customerId: String?,
        providerId: String?,
        status: DealStatus?,
        description: String?
    ) {
        val params = mutableListOf<Pair<String, String>>()
        customerId?.let { params.add(it to "customerId = ?") }
        providerId?.let { params.add(it to "providerId = ?") }
        status?.let { params.add(it.name to "status = ?::status_type") }
        description?.let { params.add(it to "description = ?") }
        if (params.isNotEmpty()) {
            val query = "update deal set ${params.joinToString(", ") { it.second }} where id = ?"
            try {
                val updatedRowsCount = jdbcOperations.update(query, *params.map { it.first }.toTypedArray(), id)
                if (updatedRowsCount != 1) {
                    error("There are zero or multiple deals with passed ID")
                }
            } catch (t: Throwable) {
                throw RepositoryException("Unable to update deal because of: ${t.message}", t)
            }
        }
    }

    @Throws(RepositoryException::class)
    override fun delete(id: String) {
        val query = "delete from deal where id = ?"
        try {
            val updatedRowsCount = jdbcOperations.update(query, id)
            if (updatedRowsCount != 1) {
                error("There are zero or multiple deals with passed ID")
            }
        } catch (t: Throwable) {
            throw RepositoryException("Unable to delete deal because of: ${t.message}", t)
        }
    }
}
