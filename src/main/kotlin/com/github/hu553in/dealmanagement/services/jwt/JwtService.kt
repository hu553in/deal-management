package com.github.hu553in.dealmanagement.services.jwt

import com.github.hu553in.dealmanagement.configurations.JwtSettings
import com.github.hu553in.dealmanagement.entities.Role
import com.github.hu553in.dealmanagement.entities.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.time.Instant
import java.util.Date

class JwtService(private val jwtSettings: JwtSettings) : IJwtService {
    override fun createToken(user: User): String {
        val currentInstant = Instant.now()
        val claims = Jwts.claims()
                .setIssuer(jwtSettings.issuer)
                .setIssuedAt(Date.from(currentInstant))
                .setSubject(user.id)
                .setExpiration(Date.from(currentInstant.plus(jwtSettings.getTtl())))
        claims["details"] = user.email
        claims["role"] = user.role
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, jwtSettings.getSecret())
                .compact()
    }

    override fun parseToken(token: String): Authentication {
        val claims = Jwts.parserBuilder()
                .setSigningKey(jwtSettings.getSecret())
                .requireIssuer(jwtSettings.issuer)
                .build()
                .parseClaimsJws(token)
        val id = claims.body.subject
        val email = claims.body.get("details", String::class.java)
        val role = Role.valueOf(claims.body.get("role", String::class.java))
        return AuthenticatedJwtToken(id, email, listOf(SimpleGrantedAuthority(role.name)))
    }
}
