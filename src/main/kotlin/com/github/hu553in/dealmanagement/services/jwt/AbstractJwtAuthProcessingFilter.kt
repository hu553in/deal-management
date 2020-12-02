package com.github.hu553in.dealmanagement.services.jwt

import com.github.hu553in.dealmanagement.entities.Role
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class AbstractJwtAuthProcessingFilter
internal constructor(requestMatcher: RequestMatcher) : AbstractAuthenticationProcessingFilter(requestMatcher) {
    @Throws(AuthenticationException::class)
    protected abstract fun processToken(request: HttpServletRequest): String

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
            request: HttpServletRequest,
            response: HttpServletResponse
    ) = try {
        JwtToken(processToken(request))
    } catch (e: Exception) {
        anonymousToken()
    }

    private fun anonymousToken() = AnonymousAuthenticationToken(
            Role.ANONYMOUS.name,
            Role.ANONYMOUS.name,
            listOf(SimpleGrantedAuthority(Role.ANONYMOUS.name))
    )

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain,
            authResult: Authentication
    ) {
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }
}
