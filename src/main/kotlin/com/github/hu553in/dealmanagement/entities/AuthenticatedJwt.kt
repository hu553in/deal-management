package com.github.hu553in.dealmanagement.entities

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

internal class AuthenticatedJwt(
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
