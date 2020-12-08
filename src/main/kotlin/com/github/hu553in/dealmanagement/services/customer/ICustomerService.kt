package com.github.hu553in.dealmanagement.services.customer

import com.github.hu553in.dealmanagement.entities.Customer
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.requests.AddCustomerRequest
import com.github.hu553in.dealmanagement.models.requests.UpdateCustomerRequest

interface ICustomerService {
    @Throws(ServiceException::class)
    fun getById(id: String): Customer

    @Throws(ServiceException::class)
    fun getAll(): List<Customer>

    @Throws(ServiceException::class)
    fun add(addCustomerRequest: AddCustomerRequest)

    @Throws(ServiceException::class)
    fun update(id: String, updateCustomerRequest: UpdateCustomerRequest)

    @Throws(ServiceException::class)
    fun delete(id: String)
}
