package com.github.hu553in.dealmanagement.helpers

import com.github.hu553in.dealmanagement.exceptions.JwtAuthException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.util.matcher.RequestMatcher
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest

class HeaderJwtAuthProcessingFilter(requestMatcher: RequestMatcher) : AbstractJwtAuthProcessingFilter(requestMatcher) {
    @Throws(AuthenticationException::class)
    override fun processToken(request: HttpServletRequest): String {
        val authorizationHeader: String = request.getHeader("Authorization")
        val matcher = BEARER_HEADER_PATTERN.matcher(authorizationHeader)
        return if (matcher.matches()) {
            matcher.group(1)
        } else {
            throw JwtAuthException("Invalid 'Authorization' header: $authorizationHeader")
        }
    }

    companion object {
        private val BEARER_HEADER_PATTERN = Pattern.compile("^Bearer\\s+(.*)$")
    }
}
