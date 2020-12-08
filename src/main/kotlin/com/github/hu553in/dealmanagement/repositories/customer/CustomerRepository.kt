package com.github.hu553in.dealmanagement.repositories.customer

import com.github.hu553in.dealmanagement.entities.Customer
import com.github.hu553in.dealmanagement.exceptions.RepositoryException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class CustomerRepository(private val jdbcOperations: JdbcOperations) : ICustomerRepository {
    @Throws(RepositoryException::class)
    override fun getById(id: String): Customer {
        val query = "select product, phone, created_at, updated_at from customer where id = ?"
        return try {
            jdbcOperations.query(query, arrayOf(id)) { resultSet, _ ->
                Customer(
                    id,
                    resultSet.getString("product"),
                    resultSet.getString("phone"),
                    resultSet.getString("created_at"),
                    resultSet.getString("updated_at")
                )
            }.singleOrNull() ?: error("There are zero or multiple customers with passed ID")
        } catch (t: Throwable) {
            throw RepositoryException("Unable to get customer by ID because of: ${t.message}", t)
        }
    }

    @Throws(RepositoryException::class)
    override fun getAll(): List<Customer> {
        val query = "select id, product, phone, created_at, updated_at from customer"
        return try {
            jdbcOperations.query(query) { resultSet, _ ->
                Customer(
                    resultSet.getString("id"),
                    resultSet.getString("product"),
                    resultSet.getString("phone"),
                    resultSet.getString("created_at"),
                    resultSet.getString("updated_at")
                )
            }
        } catch (t: Throwable) {
            throw RepositoryException("Unable to get all customers because of: ${t.message}", t)
        }
    }

    @Throws(RepositoryException::class)
    override fun add(product: String, phone: String): String {
        val id = UUID.randomUUID().toString()
        val query = "insert into customer values (?, ?, ?)"
        try {
            jdbcOperations.update(query, id, product, phone)
        } catch (t: Throwable) {
            throw RepositoryException("Unable to add customer because of: ${t.message}", t)
        }
        return id
    }

    @Throws(RepositoryException::class)
    override fun update(id: String, product: String?, phone: String?) {
        val params = mutableListOf<Pair<String, String>>()
        product?.let { params.add(it to "product = ?") }
        phone?.let { params.add(it to "phone = ?") }
        if (params.isNotEmpty()) {
            val query = "update customer set ${params.joinToString(", ") { it.second }} where id = ?"
            try {
                val updatedRowsCount = jdbcOperations.update(query, *params.map { it.first }.toTypedArray(), id)
                if (updatedRowsCount != 1) {
                    error("There are zero or multiple customers with passed ID")
                }
            } catch (t: Throwable) {
                throw RepositoryException("Unable to update customer because of: ${t.message}", t)
            }
        }
    }

    @Throws(RepositoryException::class)
    override fun delete(id: String) {
        val query = "delete from customer where id = ?"
        try {
            val updatedRowsCount = jdbcOperations.update(query, id)
            if (updatedRowsCount != 1) {
                error("There are zero or multiple customers with passed ID")
            }
        } catch (t: Throwable) {
            throw RepositoryException("Unable to delete customer because of: ${t.message}", t)
        }
    }
}
