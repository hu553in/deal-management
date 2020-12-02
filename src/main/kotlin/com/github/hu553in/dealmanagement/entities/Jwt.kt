package com.github.hu553in.dealmanagement.entities

import org.springframework.security.authentication.AbstractAuthenticationToken

internal class Jwt(private val token: String) : AbstractAuthenticationToken(null) {
    init {
        super.setAuthenticated(false)
    }

    override fun setAuthenticated(authenticated: Boolean) {
        require(!authenticated) { "Unable to mark token as trusted" }
        super.setAuthenticated(false)
    }

    override fun getCredentials() = token

    override fun getPrincipal() = null
}
