package com.github.hu553in.dealmanagement.configurations

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.regex.Pattern

@Configuration
class CorsConfiguration(
    @Value("\${corsHeaders.allowedOrigins:*}") private val allowedOrigins: String,
    @Value("\${corsHeaders.allowCredentials:true}") private val allowCredentials: Boolean,
    @Value("\${corsHeaders.allowedMethods:*}") private val allowedMethods: String,
    @Value("\${corsHeaders.allowedHeaders:*}") private val allowedHeaders: String
) {
    private val delimiter = Pattern.compile("[,\\s]+")

    @Bean
    fun corsConfigurer(): WebMvcConfigurer = object : WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/**")
                .allowedOrigins(*delimiter.split(allowedOrigins))
                .allowCredentials(allowCredentials)
                .allowedMethods(*delimiter.split(allowedMethods))
                .allowedHeaders(*delimiter.split(allowedHeaders))
        }
    }
}
