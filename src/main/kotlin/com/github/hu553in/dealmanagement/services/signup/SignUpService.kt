package com.github.hu553in.dealmanagement.services.signup

import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.SignUpRequest
import com.github.hu553in.dealmanagement.repositories.user.IUserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignUpService(
    private val userRepository: IUserRepository,
    private val passwordEncoder: PasswordEncoder
) : ISignUpService {
    @Throws(ServiceException::class)
    override fun signUp(signUpRequest: SignUpRequest) {
        val email = signUpRequest.email
        val password = signUpRequest.password
        try {
            val encodedPassword = passwordEncoder.encode(password)
            userRepository.add(email, encodedPassword)
        } catch (t: Throwable) {
            throw ServiceException("Unable to sign up because of: ${t.message}", t)
        }
    }
}
