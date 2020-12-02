package com.github.hu553in.dealmanagement.configurations

import java.nio.charset.StandardCharsets
import java.time.Duration

class JwtSettings(
        val issuer: String,
        private val secret: String,
        private val ttl: Int
) {
    fun getSecret() = secret.toByteArray(StandardCharsets.UTF_8)

    fun getTtl() = Duration.ofMinutes(ttl.toLong())

}
