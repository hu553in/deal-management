package com.github.hu553in.dealmanagement.components

import com.github.hu553in.dealmanagement.models.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ResponseUtils {
    fun respondWithValidationErrors(errors: Map<String, String>): ResponseEntity<CommonResponse> =
        ResponseEntity.badRequest()
            .contentType(MediaType.APPLICATION_JSON)
            .body(CommonResponse(
                HttpStatus.BAD_REQUEST.value(),
                errors = errors.entries.map { "${it.key} - ${it.value}" }
            ))
}
