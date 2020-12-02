package com.github.hu553in.dealmanagement.repositories.user

import com.github.hu553in.dealmanagement.entities.Role
import com.github.hu553in.dealmanagement.entities.User

interface IUserRepository {
    fun getById(id: String): User

    fun getByEmail(email: String): User

    fun getAll(): List<User>

    fun add(email: String, password: String, role: Role): String

    fun update(id: String, email: String?, password: String?, role: Role?)
}
