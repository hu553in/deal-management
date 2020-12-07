package com.github.hu553in.dealmanagement.repositories.user

import com.github.hu553in.dealmanagement.entities.User
import com.github.hu553in.dealmanagement.entities.UserRole
import com.github.hu553in.dealmanagement.exceptions.RepositoryException
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserRepository(private val jdbcOperations: JdbcOperations) : IUserRepository {
    @Throws(RepositoryException::class)
    override fun getById(id: String): User {
        val query = "select email, password, \"role\" from \"user\" where id = ?"
        return try {
            jdbcOperations.query(query, arrayOf(id)) { resultSet, _ ->
                User(
                    id,
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    UserRole.valueOf(resultSet.getString("role"))
                )
            }.singleOrNull() ?: error("There are zero or multiple users with passed ID")
        } catch (t: Throwable) {
            throw RepositoryException("Unable to get user by ID because of: ${t.message}", t)
        }
    }

    @Throws(RepositoryException::class)
    override fun getByEmail(email: String): User {
        val query = "select id, password, \"role\" from \"user\" where email = ?"
        return try {
            jdbcOperations.query(query, arrayOf(email)) { resultSet, _ ->
                User(
                    resultSet.getString("id"),
                    email,
                    resultSet.getString("password"),
                    UserRole.valueOf(resultSet.getString("role"))
                )
            }.singleOrNull() ?: error("There are zero or multiple users with passed email")
        } catch (t: Throwable) {
            throw RepositoryException("Unable to get user by email because of: ${t.message}", t)
        }
    }

    @Throws(RepositoryException::class)
    override fun getAll(): List<User> {
        val query = "select id, email, password, \"role\" from \"user\""
        return try {
            jdbcOperations.query(query) { resultSet, _ ->
                User(
                    resultSet.getString("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    UserRole.valueOf(resultSet.getString("role"))
                )
            }
        } catch (t: Throwable) {
            throw RepositoryException("Unable to get all users because of: ${t.message}", t)
        }
    }

    @Throws(RepositoryException::class)
    override fun add(email: String, password: String, role: UserRole): String {
        val id = UUID.randomUUID().toString()
        val query = "insert into \"user\" values (?, ?, ?, ?::role_type)"
        try {
            jdbcOperations.update(query, id, email, password, role.name)
        } catch (t: Throwable) {
            throw RepositoryException("Unable to add user because of: ${t.message}", t)
        }
        return id
    }

    @Throws(RepositoryException::class)
    override fun add(email: String, password: String): String {
        val id = UUID.randomUUID().toString()
        val query = "insert into \"user\" values (?, ?, ?)"
        try {
            jdbcOperations.update(query, id, email, password)
        } catch (t: Throwable) {
            throw RepositoryException("Unable to add user because of: ${t.message}", t)
        }
        return id
    }

    @Throws(RepositoryException::class)
    override fun update(id: String, email: String?, password: String?, role: UserRole?) {
        val params = mutableListOf<Pair<String, String>>()
        email?.let { params.add(email to "email = ?") }
        password?.let { params.add(password to "password = ?") }
        role?.let { params.add(role.name to "role = ?::role_type") }
        if (params.isNotEmpty()) {
            val query = "update \"user\" set ${params.joinToString(", ") { it.second }} where id = ?"
            try {
                val updatedRowsCount = jdbcOperations.update(query, *params.map { it.first }.toTypedArray(), id)
                if (updatedRowsCount != 1) {
                    error("There are zero or multiple users with passed ID")
                }
            } catch (t: Throwable) {
                throw RepositoryException("Unable to update user because of: ${t.message}", t)
            }
        }
    }
}
