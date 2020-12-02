package com.github.hu553in.dealmanagement.services.whoami

import com.github.hu553in.dealmanagement.entities.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class WhoamiService : IWhoamiService {
    override fun whoami() = User(SecurityContextHolder.getContext().authentication)
}
