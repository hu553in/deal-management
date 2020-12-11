package com.github.hu553in.dealmanagement.services.deal

import com.github.hu553in.dealmanagement.entities.DealStatus
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import com.github.hu553in.dealmanagement.models.requests.AddDealRequest
import com.github.hu553in.dealmanagement.models.requests.UpdateDealRequest
import com.github.hu553in.dealmanagement.repositories.deal.IDealRepository
import org.springframework.stereotype.Service

@Service
class DealService(private val dealRepository: IDealRepository) : IDealService {
    private val dealActions = mapOf<String, (String) -> Unit>(
        "approve" to { id -> update(id, UpdateDealRequest(status = DealStatus.APPROVED.name)) },
        "reject" to { id -> update(id, UpdateDealRequest(status = DealStatus.REJECTED.name)) }
    )

    @Throws(ServiceException::class)
    override fun getById(id: String) = try {
        dealRepository.getById(id)
    } catch (t: Throwable) {
        throw ServiceException("Unable to get deal by ID because of: ${t.message}", t)
    }

    @Throws(ServiceException::class)
    override fun getAll() = try {
        dealRepository.getAll()
    } catch (t: Throwable) {
        throw ServiceException("Unable to get all deals because of: ${t.message}", t)
    }

    @Throws(ServiceException::class)
    override fun add(addDealRequest: AddDealRequest) {
        try {
            dealRepository.add(addDealRequest.customerId, addDealRequest.providerId, addDealRequest.description)
        } catch (t: Throwable) {
            throw ServiceException("Unable to add deal because of: ${t.message}", t)
        }
    }

    @Throws(ServiceException::class)
    override fun update(id: String, updateDealRequest: UpdateDealRequest) {
        try {
            val dealStatus = updateDealRequest.status?.let { DealStatus.valueOf(it) }
            dealRepository.update(
                id,
                updateDealRequest.customerId,
                updateDealRequest.providerId,
                dealStatus,
                updateDealRequest.description
            )
        } catch (t: Throwable) {
            throw ServiceException("Unable to update deal because of: ${t.message}", t)
        }
    }

    @Throws(ServiceException::class)
    override fun changeStatus(id: String, status: DealStatus) {
        try {
            update(id, UpdateDealRequest(status = status.name))
        } catch (t: Throwable) {
            throw ServiceException("Unable to change deal status because of: ${t.message}", t)
        }
    }

    @Throws(ServiceException::class)
    override fun delete(id: String) {
        try {
            dealRepository.delete(id)
        } catch (t: Throwable) {
            throw ServiceException("Unable to delete deal because of: ${t.message}", t)
        }
    }
}
