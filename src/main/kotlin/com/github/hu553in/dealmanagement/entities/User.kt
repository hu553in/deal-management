package com.github.hu553in.dealmanagement.entities

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails

data class User(
        val id: String,
        val email: String,
        @JsonIgnore val password: String?,
        val role: Role
) {
    @JsonCreator
    constructor(id: String, email: String, role: Role) : this(id, email, null, role)

    constructor(auth: Authentication) : this(
            if (auth.principal.javaClass == UserDetails::class.java)
                (auth.principal as UserDetails).username as String
            else
                auth.principal as String,
            auth.details as String,
            null,
            Role.valueOf(auth.authorities.singleOrNull()?.authority ?: throw Exception())
    )
}
