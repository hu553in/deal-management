package com.github.hu553in.dealmanagement.entities

import org.springframework.security.core.GrantedAuthority

enum class UserRole : GrantedAuthority {
    ROLE_ANONYMOUS,
    ROLE_VIEWER,
    ROLE_EDITOR,
    ROLE_SUPERVISOR,
    ROLE_ADMIN;

    override fun getAuthority() = name

    override fun toString() = name
}
