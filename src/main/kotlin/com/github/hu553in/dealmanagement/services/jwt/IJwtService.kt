package com.github.hu553in.dealmanagement.services.jwt

import com.github.hu553in.dealmanagement.entities.User
import org.springframework.security.core.Authentication

interface IJwtService {
    fun parseToken(token: String): Authentication

    fun createToken(user: User): String
}
