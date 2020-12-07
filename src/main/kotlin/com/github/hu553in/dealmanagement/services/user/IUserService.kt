package com.github.hu553in.dealmanagement.services.user

import com.github.hu553in.dealmanagement.entities.User
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.UpdateUserRequest

interface IUserService {
    @Throws(ServiceException::class)
    fun getById(id: String): User

    @Throws(ServiceException::class)
    fun getAll(): List<User>

    @Throws(ServiceException::class)
    fun update(id: String, updateUserRequest: UpdateUserRequest)
}
