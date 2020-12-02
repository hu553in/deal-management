package com.github.hu553in.dealmanagement.controllers

import com.github.hu553in.dealmanagement.models.CommonResponse
import com.github.hu553in.dealmanagement.models.SignUpRequest
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
class SignUpController(private val signUpService: ISignUpService) {
    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<CommonResponse> = try {
        signUpService.signUp(signUpRequest)
        ResponseEntity.noContent().build()
    } catch (t: Throwable) {
        ResponseEntity.unprocessableEntity()
                .contentType(MediaType.APPLICATION_JSON)
                .body(CommonResponse(
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        errors = listOf("Unable to sign in because of: ${t.message}")
                ))
    }
}
