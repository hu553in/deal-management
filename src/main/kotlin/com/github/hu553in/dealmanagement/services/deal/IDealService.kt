package com.github.hu553in.dealmanagement.services.deal

import com.github.hu553in.dealmanagement.entities.Deal
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.requests.AddDealRequest
import com.github.hu553in.dealmanagement.models.requests.UpdateDealRequest

interface IDealService {
    @Throws(ServiceException::class)
    fun getById(id: String): Deal

    @Throws(ServiceException::class)
    fun getAll(): List<Deal>

    @Throws(ServiceException::class)
    fun add(addDealRequest: AddDealRequest)

    @Throws(ServiceException::class)
    fun update(id: String, updateDealRequest: UpdateDealRequest)

    @Throws(ServiceException::class)
    fun doAction(id: String, action: String)

    @Throws(ServiceException::class)
    fun delete(id: String)
}
