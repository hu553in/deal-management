package com.github.hu553in.dealmanagement.services.signup

import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.requests.SignUpRequest

interface ISignUpService {
    @Throws(ServiceException::class)
    fun signUp(signUpRequest: SignUpRequest)
}
