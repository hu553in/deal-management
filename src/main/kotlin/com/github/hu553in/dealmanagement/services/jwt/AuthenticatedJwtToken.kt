package com.github.hu553in.dealmanagement.services.jwt

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

internal class AuthenticatedJwtToken(
        private val subject: String,
        details: String,
        authorities: Collection<GrantedAuthority>
) : AbstractAuthenticationToken(authorities) {
    init {
        setDetails(details)
        isAuthenticated = true
    }

    override fun getCredentials() = null

    override fun getPrincipal() = subject
}
