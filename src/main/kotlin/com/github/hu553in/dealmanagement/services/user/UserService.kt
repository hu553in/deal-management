package com.github.hu553in.dealmanagement.services.user

import com.github.hu553in.dealmanagement.entities.UserRole
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.requests.UpdateUserRequest
import com.github.hu553in.dealmanagement.repositories.user.IUserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: IUserRepository,
    private val passwordEncoder: PasswordEncoder
) : IUserService {
    @Throws(ServiceException::class)
    override fun getById(id: String) = try {
        userRepository.getById(id)
    } catch (t: Throwable) {
        throw ServiceException("Unable to get user by ID because of: ${t.message}", t)
    }

    @Throws(ServiceException::class)
    override fun getAll() = try {
        userRepository.getAll()
    } catch (t: Throwable) {
        throw ServiceException("Unable to get all users because of: ${t.message}", t)
    }

    @Throws(ServiceException::class)
    override fun update(id: String, updateUserRequest: UpdateUserRequest) {
        try {
            val role = updateUserRequest.role?.let { UserRole.valueOf(it) }
            val password = updateUserRequest.password?.let { passwordEncoder.encode(it) }
            userRepository.update(id, updateUserRequest.email, password, role)
        } catch (t: Throwable) {
            throw ServiceException("Unable to update user because of: ${t.message}", t)
        }
    }

    @Throws(ServiceException::class)
    override fun delete(id: String) {
        try {
            userRepository.delete(id)
        } catch (t: Throwable) {
            throw ServiceException("Unable to delete user because of: ${t.message}", t)
        }
    }
}
