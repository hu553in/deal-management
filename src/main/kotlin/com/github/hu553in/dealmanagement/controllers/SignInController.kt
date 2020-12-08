package com.github.hu553in.dealmanagement.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hu553in.dealmanagement.components.ResponseUtils
import com.github.hu553in.dealmanagement.components.validators.SignInRequestValidator
import com.github.hu553in.dealmanagement.models.CommonResponse
import com.github.hu553in.dealmanagement.models.requests.SignInRequest
import com.github.hu553in.dealmanagement.services.jwt.IJwtService
import com.github.hu553in.dealmanagement.services.signin.ISignInService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sign-in")
class SignInController(
    private val signInService: ISignInService,
    private val jwtService: IJwtService,
    private val objectMapper: ObjectMapper,
    private val signInRequestValidator: SignInRequestValidator,
    private val responseUtils: ResponseUtils
) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun signIn(@RequestBody signInRequest: SignInRequest): ResponseEntity<CommonResponse> = try {
        val errors = signInRequestValidator.validate(signInRequest)
        if (errors.isNotEmpty()) {
            responseUtils.respondWithValidationErrors(errors)
        } else {
            val token = jwtService.createToken(signInService.signIn(signInRequest))
            ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    CommonResponse(
                        HttpStatus.OK.value(),
                        objectMapper.valueToTree(token)
                    )
                )
        }
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to sign in because of: ${t.message}")
                )
            )
    }
}
