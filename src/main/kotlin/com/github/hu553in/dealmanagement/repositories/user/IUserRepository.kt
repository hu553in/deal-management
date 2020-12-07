package com.github.hu553in.dealmanagement.repositories.user

import com.github.hu553in.dealmanagement.entities.User
import com.github.hu553in.dealmanagement.entities.UserRole
import com.github.hu553in.dealmanagement.exceptions.RepositoryException

interface IUserRepository {
    @Throws(RepositoryException::class)
    fun getById(id: String): User

    @Throws(RepositoryException::class)
    fun getByEmail(email: String): User

    @Throws(RepositoryException::class)
    fun getAll(): List<User>

    @Throws(RepositoryException::class)
    fun add(email: String, password: String, role: UserRole): String

    @Throws(RepositoryException::class)
    fun update(id: String, email: String?, password: String?, role: UserRole?)
}
