package com.github.hu553in.dealmanagement.services.signin

import com.github.hu553in.dealmanagement.entities.User
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.SignInRequest
import com.github.hu553in.dealmanagement.repositories.user.IUserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class SignInService(
    private val userRepository: IUserRepository,
    private val passwordEncoder: PasswordEncoder
) : ISignInService {
    @Throws(ServiceException::class)
    override fun signIn(signInRequest: SignInRequest): User = try {
        val user = userRepository.getByEmail(signInRequest.email)
        if (!passwordEncoder.matches(signInRequest.password, user.password)) {
            error("Invalid password")
        }
        user.copy(password = null)
    } catch (t: Throwable) {
        throw ServiceException("Unable to sign in because of: ${t.message}", t)
    }
}
