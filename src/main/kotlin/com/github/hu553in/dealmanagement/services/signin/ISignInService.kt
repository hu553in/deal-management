package com.github.hu553in.dealmanagement.services.signin

import com.github.hu553in.dealmanagement.entities.User
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.SignInRequest
import com.github.hu553in.dealmanagement.models.SignUpRequest

interface ISignInService {
    @Throws(ServiceException::class)
    fun signIn(signInRequest: SignInRequest): User
}
