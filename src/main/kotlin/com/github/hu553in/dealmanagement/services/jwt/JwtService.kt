package com.github.hu553in.dealmanagement.services.jwt

import com.github.hu553in.dealmanagement.components.JwtSettings
import com.github.hu553in.dealmanagement.entities.AuthenticatedJwt
import com.github.hu553in.dealmanagement.entities.UserRole
import com.github.hu553in.dealmanagement.entities.User
import com.github.hu553in.dealmanagement.exceptions.ServiceException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Date

@Service
class JwtService(private val jwtSettings: JwtSettings) : IJwtService {
    @Throws(ServiceException::class)
    override fun createToken(user: User): String {
        val currentInstant = Instant.now()
        val claims = Jwts.claims()
                .setIssuer(jwtSettings.issuer)
                .setIssuedAt(Date.from(currentInstant))
                .setSubject(user.id)
                .setExpiration(Date.from(currentInstant.plus(jwtSettings.ttl)))
        claims["details"] = user.email
        claims["role"] = user.role
        return try {
            val key = Keys.hmacShaKeyFor(jwtSettings.secret)
            Jwts.builder()
                    .setClaims(claims)
                    .signWith(key, SignatureAlgorithm.HS512)
                    .compact()
        } catch (t: Throwable) {
            throw ServiceException("Unable to create token because of: ${t.message}", t)
        }
    }

    @Throws(ServiceException::class)
    override fun parseToken(token: String): Authentication {
        val claims = Jwts.parserBuilder()
                .setSigningKey(jwtSettings.secret)
                .requireIssuer(jwtSettings.issuer)
                .build()
                .parseClaimsJws(token)
        val id = claims.body.subject
        val email = claims.body.get("details", String::class.java)
        return try {
            val role = UserRole.valueOf(claims.body.get("role", String::class.java))
            AuthenticatedJwt(id, email, listOf(role))
        } catch (t: Throwable) {
            throw ServiceException("Unable to parse token because of: ${t.message}", t)
        }
    }
}
