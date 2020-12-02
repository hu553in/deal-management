package it.sevenbits.todolist.web.service.security.jwt

import com.github.hu553in.dealmanagement.services.jwt.IJwtService
import com.github.hu553in.dealmanagement.services.jwt.JwtToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

class JwtAuthenticationProvider(private val jwtService: IJwtService) : AuthenticationProvider {
    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val token = authentication.credentials.toString()
        return try {
            jwtService.parseToken(token)
        } catch (e: Exception) {
            throw e
//            throw JwtAuthenticationException("Invalid token received", e)
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return JwtToken::class.java.isAssignableFrom(authentication)
    }
}
