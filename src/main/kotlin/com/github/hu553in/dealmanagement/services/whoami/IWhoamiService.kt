package com.github.hu553in.dealmanagement.services.whoami

import com.github.hu553in.dealmanagement.entities.User

interface IWhoamiService {
    fun whoami(): User
}
