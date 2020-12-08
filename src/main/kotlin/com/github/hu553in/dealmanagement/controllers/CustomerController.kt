package com.github.hu553in.dealmanagement.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hu553in.dealmanagement.components.ResponseUtils
import com.github.hu553in.dealmanagement.components.validators.AddCustomerRequestValidator
import com.github.hu553in.dealmanagement.components.validators.UpdateCustomerRequestValidator
import com.github.hu553in.dealmanagement.models.CommonResponse
import com.github.hu553in.dealmanagement.models.requests.AddCustomerRequest
import com.github.hu553in.dealmanagement.models.requests.UpdateCustomerRequest
import com.github.hu553in.dealmanagement.services.customer.ICustomerService
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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customer")
class CustomerController(
    private val customerService: ICustomerService,
    private val objectMapper: ObjectMapper,
    private val addCustomerRequestValidator: AddCustomerRequestValidator,
    private val updateCustomerRequestValidator: UpdateCustomerRequestValidator,
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
                    objectMapper.valueToTree(customerService.getAll())
                )
            )
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to get all customers because of: ${t.message}")
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
                    objectMapper.valueToTree(customerService.getById(id))
                )
            )
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to get customer by ID because of: ${t.message}")
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
    fun add(@RequestBody addCustomerRequest: AddCustomerRequest): ResponseEntity<CommonResponse> = try {
        val errors = addCustomerRequestValidator.validate(addCustomerRequest)
        if (errors.isNotEmpty()) {
            responseUtils.respondWithValidationErrors(errors)
        } else {
            customerService.add(addCustomerRequest)
            ResponseEntity.noContent().build()
        }
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to add customer because of: ${t.message}")
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
        @RequestBody updateCustomerRequest: UpdateCustomerRequest
    ): ResponseEntity<CommonResponse> = try {
        val errors = updateCustomerRequestValidator.validate(updateCustomerRequest)
        if (errors.isNotEmpty()) {
            responseUtils.respondWithValidationErrors(errors)
        } else {
            customerService.update(id, updateCustomerRequest)
            ResponseEntity.noContent().build()
        }
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to update customer because of: ${t.message}")
                )
            )
    }

    @DeleteMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @PreAuthorize("hasRole(T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN)")
    fun delete(@PathVariable("id") id: String): ResponseEntity<CommonResponse> = try {
        customerService.delete(id)
        ResponseEntity.noContent().build()
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to delete customer because of: ${t.message}")
                )
            )
    }
}
