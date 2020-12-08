package com.github.hu553in.dealmanagement.services.provider

import com.github.hu553in.dealmanagement.entities.Provider
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.requests.AddProviderRequest
import com.github.hu553in.dealmanagement.models.requests.UpdateProviderRequest

interface IProviderService {
    @Throws(ServiceException::class)
    fun getById(id: String): Provider

    @Throws(ServiceException::class)
    fun getAll(): List<Provider>

    @Throws(ServiceException::class)
    fun add(addProviderRequest: AddProviderRequest)

    @Throws(ServiceException::class)
    fun update(id: String, updateProviderRequest: UpdateProviderRequest)

    @Throws(ServiceException::class)
    fun delete(id: String)
}
