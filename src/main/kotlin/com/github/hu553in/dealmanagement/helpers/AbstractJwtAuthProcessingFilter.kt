package com.github.hu553in.dealmanagement.helpers

import com.github.hu553in.dealmanagement.entities.Jwt
import com.github.hu553in.dealmanagement.entities.UserRole
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
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
        Jwt(processToken(request))
    } catch (t: Throwable) {
        anonymousToken()
    }

    private fun anonymousToken() = AnonymousAuthenticationToken(
        UserRole.ROLE_ANONYMOUS.name,
        UserRole.ROLE_ANONYMOUS.name,
        listOf(UserRole.ROLE_ANONYMOUS)
    )

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authentication: Authentication
    ) {
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }
}
