package com.github.hu553in.dealmanagement.entities

import org.springframework.security.core.GrantedAuthority

enum class UserRole : GrantedAuthority {
    ANONYMOUS,
    VIEWER,
    EDITOR,
    SUPERVISOR,
    ADMIN;

    override fun getAuthority() = name

    override fun toString() = name
}
