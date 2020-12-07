package com.github.hu553in.dealmanagement.helpers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hu553in.dealmanagement.models.CommonResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFailureAuthEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        response.writer.use {
            it.write(objectMapper.writeValueAsString(CommonResponse(
                HttpStatus.UNAUTHORIZED.value(),
                errors = authException.message?.let { msg -> listOf(msg) } ?: listOf()
            )))
        }
    }
}
