package com.github.hu553in.dealmanagement.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hu553in.dealmanagement.models.CommonResponse
import com.github.hu553in.dealmanagement.services.whoami.IWhoamiService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/whoami")
class WhoamiController(private val whoamiService: IWhoamiService, private val objectMapper: ObjectMapper) {
    @GetMapping
    @PreAuthorize(
        """hasAnyRole(
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_VIEWER,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_EDITOR,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_SUPERVISOR,
            T(com.github.hu553in.dealmanagement.entities.UserRole).ROLE_ADMIN
        )"""
    )
    fun whoami(): ResponseEntity<CommonResponse> = try {
        ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.OK.value(),
                    objectMapper.valueToTree(whoamiService.whoami())
                )
            )
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to get auth data because of: ${t.message}")
                )
            )
    }
}
