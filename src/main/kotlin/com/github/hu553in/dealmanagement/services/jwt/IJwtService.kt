package com.github.hu553in.dealmanagement.services.jwt

import com.github.hu553in.dealmanagement.entities.User
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import org.springframework.security.core.Authentication

interface IJwtService {
    @Throws(ServiceException::class)
    fun parseToken(token: String): Authentication

    @Throws(ServiceException::class)
    fun createToken(user: User): String
}
