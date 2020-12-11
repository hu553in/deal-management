package com.github.hu553in.dealmanagement.entities

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails

data class User(
    val id: String,
    val email: String,
    @JsonIgnore val password: String?,
    val role: UserRole
) {
    @JsonCreator
    constructor(id: String, email: String, role: UserRole) : this(id, email, null, role)

    constructor(auth: Authentication) : this(
        auth.details.toString(),
        if (auth.principal.javaClass == UserDetails::class.java)
            (auth.principal as UserDetails).username.toString()
        else
            auth.principal.toString(),
        null,
        UserRole.valueOf(auth.authorities.singleOrNull()?.authority ?: error("User has zero or multiple roles"))
    )
}
