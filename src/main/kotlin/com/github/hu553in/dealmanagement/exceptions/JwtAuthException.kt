package com.github.hu553in.dealmanagement.exceptions

import org.springframework.security.core.AuthenticationException

class JwtAuthException(message: String) : AuthenticationException(message)
