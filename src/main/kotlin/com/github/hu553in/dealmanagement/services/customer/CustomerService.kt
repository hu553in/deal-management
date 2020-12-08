package com.github.hu553in.dealmanagement.services.customer

import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.requests.AddCustomerRequest
import com.github.hu553in.dealmanagement.models.requests.UpdateCustomerRequest
import com.github.hu553in.dealmanagement.repositories.customer.ICustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: ICustomerRepository) : ICustomerService {
    @Throws(ServiceException::class)
    override fun getById(id: String) = try {
        customerRepository.getById(id)
    } catch (t: Throwable) {
        throw ServiceException("Unable to get customer by ID because of: ${t.message}", t)
    }

    @Throws(ServiceException::class)
    override fun getAll() = try {
        customerRepository.getAll()
    } catch (t: Throwable) {
        throw ServiceException("Unable to get all customers because of: ${t.message}", t)
    }

    @Throws(ServiceException::class)
    override fun add(addCustomerRequest: AddCustomerRequest) {
        try {
            customerRepository.add(addCustomerRequest.product, addCustomerRequest.phone)
        } catch (t: Throwable) {
            throw ServiceException("Unable to add customer because of: ${t.message}", t)
        }
    }

    @Throws(ServiceException::class)
    override fun update(id: String, updateCustomerRequest: UpdateCustomerRequest) {
        try {
            customerRepository.update(id, updateCustomerRequest.product, updateCustomerRequest.phone)
        } catch (t: Throwable) {
            throw ServiceException("Unable to update customer because of: ${t.message}", t)
        }
    }

    @Throws(ServiceException::class)
    override fun delete(id: String) {
        try {
            customerRepository.delete(id)
        } catch (t: Throwable) {
            throw ServiceException("Unable to delete customer because of: ${t.message}", t)
        }
    }
}
