package com.github.hu553in.dealmanagement.components

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration

@Component
class JwtSettings(
    @Value("\${jwt.issuer}") val issuer: String,
    @Value("\${jwt.secret}") secret: String,
    @Value("\${jwt.ttl}") ttl: Int
) {
    val secret = secret.toByteArray(StandardCharsets.UTF_8)
    val ttl = Duration.ofMinutes(ttl.toLong())
}
