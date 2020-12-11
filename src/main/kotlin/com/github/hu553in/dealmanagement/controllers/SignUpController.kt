package com.github.hu553in.dealmanagement.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.hu553in.dealmanagement.components.ResponseUtils
import com.github.hu553in.dealmanagement.components.validators.SignUpRequestValidator
import com.github.hu553in.dealmanagement.models.CommonResponse
import com.github.hu553in.dealmanagement.models.requests.SignInRequest
import com.github.hu553in.dealmanagement.models.requests.SignUpRequest
import com.github.hu553in.dealmanagement.services.jwt.IJwtService
import com.github.hu553in.dealmanagement.services.signin.ISignInService
import com.github.hu553in.dealmanagement.services.signup.ISignUpService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sign-up")
class SignUpController(
    private val signUpService: ISignUpService,
    private val signUpRequestValidator: SignUpRequestValidator,
    private val signInService: ISignInService,
    private val jwtService: IJwtService,
    private val objectMapper: ObjectMapper,
    private val responseUtils: ResponseUtils
) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<CommonResponse> = try {
        val errors = signUpRequestValidator.validate(signUpRequest)
        if (errors.isNotEmpty()) {
            responseUtils.respondWithValidationErrors(errors)
        } else {
            signUpService.signUp(signUpRequest)
            val user = signInService.signIn(SignInRequest(signUpRequest.email, signUpRequest.password))
            val token = jwtService.createToken(user)
            ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    CommonResponse(
                        HttpStatus.OK.value(),
                        objectMapper.createObjectNode().apply {
                            put("token", token)
                            set<ObjectNode>("user", objectMapper.valueToTree(user))
                        }
                    )
                )
        }
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                CommonResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    errors = listOf("Unable to sign up because of: ${t.message}")
                )
            )
    }
}
