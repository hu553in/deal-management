package com.github.hu553in.dealmanagement.entities

import org.springframework.security.core.GrantedAuthority

enum class Role(val order: Int) : GrantedAuthority {
    ANONYMOUS(0),
    VIEWER(1),
    EDITOR(2),
    SUPERVISOR(3),
    ADMIN(4);

    override fun getAuthority() = name

    override fun toString() = name
}
