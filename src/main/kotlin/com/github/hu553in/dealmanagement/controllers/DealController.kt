package com.github.hu553in.dealmanagement.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hu553in.dealmanagement.components.ResponseUtils
import com.github.hu553in.dealmanagement.components.validators.AddDealRequestValidator
import com.github.hu553in.dealmanagement.components.validators.DealStatusValidator
import com.github.hu553in.dealmanagement.components.validators.UpdateDealRequestValidator
import com.github.hu553in.dealmanagement.entities.DealStatus
import com.github.hu553in.dealmanagement.models.CommonResponse
import com.github.hu553in.dealmanagement.models.requests.AddDealRequest
import com.github.hu553in.dealmanagement.models.requests.UpdateDealRequest
import com.github.hu553in.dealmanagement.services.deal.IDealService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/deal")
class DealController(
    private val dealService: IDealService,
    private val objectMapper: ObjectMapper,
    private val addDealRequestValidator: AddDealRequestValidator,
    private val updateDealRequestValidator: UpdateDealRequestValidator,
    private val dealStatusValidator: DealStatusValidator,
    private val responseUtils: ResponseUtils
) {
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize(
        """hasAnyRole(
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_VIEWER,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_EDITOR,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_SUPERVISOR,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN
        )"""
    )
    fun getAll(): ResponseEntity<CommonResponse> = try {
        ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.OK.value(),
                    objectMapper.valueToTree(dealService.getAll())
                )
            )
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to get all deals because of: ${t.message}")
                )
            )
    }

    @GetMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @PreAuthorize(
        """hasAnyRole(
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_VIEWER,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_EDITOR,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_SUPERVISOR,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN
        )"""
    )
    fun getById(@PathVariable("id") id: String): ResponseEntity<CommonResponse> = try {
        ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.OK.value(),
                    objectMapper.valueToTree(dealService.getById(id))
                )
            )
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to get deal by ID because of: ${t.message}")
                )
            )
    }

    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @PreAuthorize(
        """hasAnyRole(
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_EDITOR,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_SUPERVISOR,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN
        )"""
    )
    fun add(@RequestBody addDealRequest: AddDealRequest): ResponseEntity<CommonResponse> = try {
        val errors = addDealRequestValidator.validate(addDealRequest)
        if (errors.isNotEmpty()) {
            responseUtils.respondWithValidationErrors(errors)
        } else {
            dealService.add(addDealRequest)
            ResponseEntity.noContent().build()
        }
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to add deal because of: ${t.message}")
                )
            )
    }

    @PatchMapping(
        value = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @PreAuthorize("hasRole(T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN)")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody updateDealRequest: UpdateDealRequest
    ): ResponseEntity<CommonResponse> = try {
        val errors = updateDealRequestValidator.validate(updateDealRequest)
        if (errors.isNotEmpty()) {
            responseUtils.respondWithValidationErrors(errors)
        } else {
            dealService.update(id, updateDealRequest)
            ResponseEntity.noContent().build()
        }
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to update deal because of: ${t.message}")
                )
            )
    }

    @PatchMapping(
        value = ["/change-status/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @PreAuthorize(
        """hasAnyRole(
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_SUPERVISOR,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN
        )"""
    )
    fun changeStatus(
        @PathVariable("id") id: String,
        @RequestParam("status") status: String
    ): ResponseEntity<CommonResponse> = try {
        val errors = mutableMapOf<String, String>().apply {
            dealStatusValidator.isDealStatus(status, this, "status")
        }
        if (errors.isNotEmpty()) {
            responseUtils.respondWithValidationErrors(errors)
        } else {
            dealService.changeStatus(id, DealStatus.valueOf(status))
            ResponseEntity.noContent().build()
        }
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to change deal status because of: ${t.message}")
                )
            )
    }

    @DeleteMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @PreAuthorize("hasRole(T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN)")
    fun delete(@PathVariable("id") id: String): ResponseEntity<CommonResponse> = try {
        dealService.delete(id)
        ResponseEntity.noContent().build()
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to delete deal because of: ${t.message}")
                )
            )
    }
}
