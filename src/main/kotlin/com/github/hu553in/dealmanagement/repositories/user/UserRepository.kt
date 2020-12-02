package com.github.hu553in.dealmanagement.repositories.user

import com.github.hu553in.dealmanagement.entities.Role
import com.github.hu553in.dealmanagement.entities.User
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserRepository(private val jdbcOperations: JdbcOperations) : IUserRepository {
    override fun getById(id: String): User {
        val query = "select email, password, \"role\" from \"user\" where id = ?"
        return try {
            jdbcOperations.query(query, arrayOf(id)) { resultSet, _ ->
                User(
                        id,
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role"))
                )
            }.singleOrNull() ?: throw Exception()
        } catch (e: Throwable) {
            throw e
        }
    }

    override fun getByEmail(email: String): User {
        val query = "select id, password, \"role\" from \"user\" where email = ?"
        return try {
            jdbcOperations.query(query, arrayOf(email)) { resultSet, _ ->
                User(
                        resultSet.getString("id"),
                        email,
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role"))
                )
            }.singleOrNull() ?: throw Exception()
        } catch (e: Throwable) {
            throw e
        }
    }

    override fun getAll(): List<User> {
        val query = "select id, email, password, \"role\" from \"user\""
        return try {
            jdbcOperations.query(query) { resultSet, _ ->
                User(
                        resultSet.getString("id"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role"))
                )
            }
        } catch (e: Throwable) {
            throw e
        }
    }

    override fun add(email: String, password: String, role: Role): String {
        val id = UUID.randomUUID().toString()
        val query = "insert into \"user\" values ($id, ?, ?, ?)"
        try {
            jdbcOperations.update(query, email, password, role)
        } catch (e: Throwable) {
            throw e
        }
        return id
    }

    override fun update(id: String, email: String?, password: String?, role: Role?) {
        val params = mutableListOf<Pair<String, String>>()
        email?.let { params.add(email to "email = ?") }
        password?.let { params.add(password to "password = ?") }
        role?.let { params.add(role.name to "role = ?") }
        if (params.isNotEmpty()) {
            val query = "update \"user\" set ${params.joinToString(", ") { it.second }} where id = ?"
            try {
                jdbcOperations.update(query, *params.map { it.first }.toTypedArray(), id)
            } catch (e: Throwable) {
                throw e
            }
        } else {
            throw Exception()
        }
    }
}
