package com.github.hu553in.dealmanagement.helpers

import com.github.hu553in.dealmanagement.entities.Jwt
import com.github.hu553in.dealmanagement.exceptions.JwtAuthException
import com.github.hu553in.dealmanagement.services.jwt.IJwtService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

class JwtAuthProvider(private val jwtService: IJwtService) : AuthenticationProvider {
    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val token = authentication.credentials.toString()
        return try {
            jwtService.parseToken(token)
        } catch (t: Throwable) {
            throw JwtAuthException("Invalid token: $token", t)
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return Jwt::class.java.isAssignableFrom(authentication)
    }
}
