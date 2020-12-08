package com.github.hu553in.dealmanagement.services.provider

import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.requests.AddProviderRequest
import com.github.hu553in.dealmanagement.models.requests.UpdateProviderRequest
import com.github.hu553in.dealmanagement.repositories.provider.IProviderRepository
import org.springframework.stereotype.Service

@Service
class ProviderService(private val providerRepository: IProviderRepository) : IProviderService {
    @Throws(ServiceException::class)
    override fun getById(id: String) = try {
        providerRepository.getById(id)
    } catch (t: Throwable) {
        throw ServiceException("Unable to get provider by ID because of: ${t.message}", t)
    }

    @Throws(ServiceException::class)
    override fun getAll() = try {
        providerRepository.getAll()
    } catch (t: Throwable) {
        throw ServiceException("Unable to get all providers because of: ${t.message}", t)
    }

    @Throws(ServiceException::class)
    override fun add(addProviderRequest: AddProviderRequest) {
        try {
            providerRepository.add(addProviderRequest.product, addProviderRequest.phone, addProviderRequest.email)
        } catch (t: Throwable) {
            throw ServiceException("Unable to add provider because of: ${t.message}", t)
        }
    }

    @Throws(ServiceException::class)
    override fun update(id: String, updateProviderRequest: UpdateProviderRequest) {
        try {
            providerRepository.update(
                id,
                updateProviderRequest.product,
                updateProviderRequest.phone,
                updateProviderRequest.email
            )
        } catch (t: Throwable) {
            throw ServiceException("Unable to update provider because of: ${t.message}", t)
        }
    }

    @Throws(ServiceException::class)
    override fun delete(id: String) {
        try {
            providerRepository.delete(id)
        } catch (t: Throwable) {
            throw ServiceException("Unable to delete provider because of: ${t.message}", t)
        }
    }
}
