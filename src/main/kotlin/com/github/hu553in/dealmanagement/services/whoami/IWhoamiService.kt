package com.github.hu553in.dealmanagement.services.whoami

import com.github.hu553in.dealmanagement.entities.User
import com.github.hu553in.dealmanagement.exceptions.ServiceException

interface IWhoamiService {
    @Throws(ServiceException::class)
    fun whoami(): User
}
