package com.github.hu553in.dealmanagement.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hu553in.dealmanagement.components.validators.UpdateUserRequestValidator
import com.github.hu553in.dealmanagement.models.CommonResponse
import com.github.hu553in.dealmanagement.models.UpdateUserRequest
import com.github.hu553in.dealmanagement.services.user.IUserService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: IUserService,
    private val objectMapper: ObjectMapper,
    private val updateUserRequestValidator: UpdateUserRequestValidator,
    private val controllerUtils: ControllerUtils
) {
    @GetMapping
    @PreAuthorize("hasRole(T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN)")
    fun getAll(): ResponseEntity<CommonResponse> = try {
        ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.OK.value(),
                    objectMapper.valueToTree(userService.getAll())
                )
            )
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to get all users because of: ${t.message}")
                )
            )
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole(T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN)")
    fun getById(@PathVariable("id") id: String): ResponseEntity<CommonResponse> = try {
        ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.OK.value(),
                    objectMapper.valueToTree(userService.getById(id))
                )
            )
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to get user by ID because of: ${t.message}")
                )
            )
    }

    @PatchMapping(
        value = ["/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    @PreAuthorize("hasRole(T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN)")
    fun update(
        @PathVariable("id") id: String,
        @RequestBody updateUserRequest: UpdateUserRequest
    ): ResponseEntity<CommonResponse> = try {
        val errors = updateUserRequestValidator.validate(updateUserRequest)
        if (errors.isNotEmpty()) {
            controllerUtils.respondWithValidationErrors(errors)
        } else {
            userService.update(id, updateUserRequest)
            ResponseEntity.noContent().build()
        }
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to update user because of: ${t.message}")
                )
            )
    }
}
