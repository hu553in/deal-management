package com.github.hu553in.dealmanagement.services.whoami

import com.github.hu553in.dealmanagement.entities.User
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class WhoamiService : IWhoamiService {
    @Throws(ServiceException::class)
    override fun whoami() = try {
        User(SecurityContextHolder.getContext().authentication)
    } catch (t: Throwable) {
        throw ServiceException("Unable to get auth data because of: ${t.message}", t)
    }
}
