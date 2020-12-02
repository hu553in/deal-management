package com.github.hu553in.dealmanagement.services.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken

internal class JwtToken(private val token: String) : AbstractAuthenticationToken(null) {
    init {
        super.setAuthenticated(false)
    }

    override fun setAuthenticated(authenticated: Boolean) {
        require(!authenticated) { "Can not mark this token as trusted" }
        super.setAuthenticated(false)
    }

    override fun getCredentials() = token

    override fun getPrincipal() = null
}
