package com.github.hu553in.dealmanagement.services.jwt

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest

class HeaderJwtAuthProcessingFilter(requestMatcher: RequestMatcher) : AbstractJwtAuthProcessingFilter(requestMatcher) {
    @Throws(AuthenticationException::class)
    override fun processToken(request: HttpServletRequest): String {
        val authHeader: String = request.getHeader("Authorization")
        val matcher = BEARER_AUTH_PATTERN.matcher(authHeader)
        return if (matcher.matches()) {
            matcher.group(1)
        } else {
//            throw JwtAuthenticationException("Invalid Authorization header: $authHeader")
            throw Exception()
        }
    }

    companion object {
        private val BEARER_AUTH_PATTERN = Pattern.compile("^Bearer\\s+(.*)$")
    }
}
