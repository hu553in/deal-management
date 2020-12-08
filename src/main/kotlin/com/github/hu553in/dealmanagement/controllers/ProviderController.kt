package com.github.hu553in.dealmanagement.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hu553in.dealmanagement.components.ResponseUtils
import com.github.hu553in.dealmanagement.components.validators.AddProviderRequestValidator
import com.github.hu553in.dealmanagement.components.validators.UpdateProviderRequestValidator
import com.github.hu553in.dealmanagement.models.CommonResponse
import com.github.hu553in.dealmanagement.models.requests.AddProviderRequest
import com.github.hu553in.dealmanagement.models.requests.UpdateProviderRequest
import com.github.hu553in.dealmanagement.services.provider.IProviderService
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
@RequestMapping("/provider")
class ProviderController(
    private val providerService: IProviderService,
    private val objectMapper: ObjectMapper,
    private val addProviderRequestValidator: AddProviderRequestValidator,
    private val updateProviderRequestValidator: UpdateProviderRequestValidator,
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
                    objectMapper.valueToTree(providerService.getAll())
                )
            )
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to get all providers because of: ${t.message}")
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
                    objectMapper.valueToTree(providerService.getById(id))
                )
            )
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to get provider by ID because of: ${t.message}")
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
    fun add(@RequestBody addProviderRequest: AddProviderRequest): ResponseEntity<CommonResponse> = try {
        val errors = addProviderRequestValidator.validate(addProviderRequest)
        if (errors.isNotEmpty()) {
            responseUtils.respondWithValidationErrors(errors)
        } else {
            providerService.add(addProviderRequest)
            ResponseEntity.noContent().build()
        }
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to add provider because of: ${t.message}")
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
        @RequestBody updateProviderRequest: UpdateProviderRequest
    ): ResponseEntity<CommonResponse> = try {
        val errors = updateProviderRequestValidator.validate(updateProviderRequest)
        if (errors.isNotEmpty()) {
            responseUtils.respondWithValidationErrors(errors)
        } else {
            providerService.update(id, updateProviderRequest)
            ResponseEntity.noContent().build()
        }
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to update provider because of: ${t.message}")
                )
            )
    }

    @DeleteMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @PreAuthorize("hasRole(T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN)")
    fun delete(@PathVariable("id") id: String): ResponseEntity<CommonResponse> = try {
        providerService.delete(id)
        ResponseEntity.noContent().build()
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to delete provider because of: ${t.message}")
                )
            )
    }
}
