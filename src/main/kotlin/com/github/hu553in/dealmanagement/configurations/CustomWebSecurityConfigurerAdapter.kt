package com.github.hu553in.dealmanagement.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.hu553in.dealmanagement.entities.UserRole
import com.github.hu553in.dealmanagement.helpers.HeaderJwtAuthProcessingFilter
import com.github.hu553in.dealmanagement.helpers.JwtAuthProvider
import com.github.hu553in.dealmanagement.helpers.JwtFailureAuthEntryPoint
import com.github.hu553in.dealmanagement.services.jwt.IJwtService
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher

@Configuration
@EnableWebSecurity
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
        val signInPathMatcher = AntPathRequestMatcher("/sign-in")
        val nonSignInPathMatcher = NegatedRequestMatcher(signInPathMatcher)
        val headerJwtAuthProcessingFilter = HeaderJwtAuthProcessingFilter(nonSignInPathMatcher)
        httpSecurity.addFilterBefore(headerJwtAuthProcessingFilter, FilterSecurityInterceptor::class.java)
        httpSecurity
            .exceptionHandling()
            .authenticationEntryPoint(JwtFailureAuthEntryPoint(objectMapper))
        httpSecurity
            .authorizeRequests().antMatchers("/sign-in", "/sign-up").permitAll()
            .and()
            .authorizeRequests().antMatchers("/user/**").hasAuthority(UserRole.ADMIN.name)
            .and()
            .authorizeRequests().antMatchers("/whoami").authenticated()
            .and()
            .authorizeRequests().anyRequest().authenticated()
    }

    public override fun configure(authenticationManagerBuilder: AuthenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(JwtAuthProvider(jwtService))
    }
}
