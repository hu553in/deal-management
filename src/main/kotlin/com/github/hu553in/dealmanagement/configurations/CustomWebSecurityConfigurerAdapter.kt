package com.github.hu553in.dealmanagement.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hu553in.dealmanagement.helpers.HeaderJwtAuthProcessingFilter
import com.github.hu553in.dealmanagement.helpers.JwtAuthProvider
import com.github.hu553in.dealmanagement.models.CommonResponse
import com.github.hu553in.dealmanagement.services.jwt.IJwtService
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class CustomWebSecurityConfigurerAdapter(
    private val jwtService: IJwtService,
    private val objectMapper: ObjectMapper
) : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity.csrf().disable()
        httpSecurity.formLogin().disable()
        httpSecurity.logout().disable()
        httpSecurity.sessionManagement().apply {
            disable()
            sessionCreationPolicy(SessionCreationPolicy.NEVER)
        }
        httpSecurity.requestCache().disable()
        httpSecurity.anonymous()
        val withoutAuthPathMatcher = OrRequestMatcher(
            AntPathRequestMatcher("/sign-up"),
            AntPathRequestMatcher("/sign-in")
        )
        val withAuthPathMatcher = NegatedRequestMatcher(withoutAuthPathMatcher)
        val headerJwtAuthProcessingFilter = HeaderJwtAuthProcessingFilter(withAuthPathMatcher)
        httpSecurity.addFilterBefore(headerJwtAuthProcessingFilter, FilterSecurityInterceptor::class.java)
        httpSecurity.exceptionHandling().apply {
            authenticationEntryPoint { _, response, authException ->
                response.status = HttpStatus.UNAUTHORIZED.value()
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                response.writer.use {
                    it.write(objectMapper.writeValueAsString(CommonResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        errors = authException.message?.let { msg -> listOf(msg) } ?: listOf()
                    )))
                }
            }
            accessDeniedHandler { _, response, accessDeniedException ->
                response.status = HttpStatus.FORBIDDEN.value()
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                response.writer.use {
                    it.write(objectMapper.writeValueAsString(CommonResponse(
                        HttpStatus.FORBIDDEN.value(),
                        errors = accessDeniedException.message?.let { msg -> listOf(msg) } ?: listOf()
                    )))
                }
            }
        }
        httpSecurity
            .authorizeRequests().antMatchers("/sign-up", "/sign-in").permitAll()
            .and()
            .authorizeRequests().anyRequest().authenticated()
    }

    public override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(JwtAuthProvider(jwtService))
    }
}
