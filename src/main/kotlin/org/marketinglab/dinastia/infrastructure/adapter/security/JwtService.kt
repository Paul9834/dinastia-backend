package org.marketinglab.dinastia.infrastructure.adapter.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtService {

    // IMPORTANTE: Esta clave debe ser larga y segura.
    private val SECRET_KEY = "clave_super_secreta_para_firmar_tokens_dinastia_backend_key"

    // --- PARTE 1: GENERACIÓN (LO QUE YA TENÍAS) ---

    fun generarToken(username: String, role: String): String {
        val claims = mapOf("rol" to role)
        return createToken(claims, username)
    }
    private fun createToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
            .setClaims(claims)       // 1. IMPORTANTE: Guardar roles
            .setSubject(subject)     // 2. IMPORTANTE: Guardar el usuario (email)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60)) // 1 minuto
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(encodeSecret(SECRET_KEY))
        return Keys.hmacShaKeyFor(keyBytes)
    }

    private fun encodeSecret(secret: String): String {
        return java.util.Base64.getEncoder().encodeToString(secret.toByteArray())
    }

    // --- PARTE 2: VALIDACIÓN (LO QUE TE FALTABA) ---
    // Estos son los métodos que usa tu filtro JwtAuthenticationFilter

    // Extraer el usuario (correo) del token
    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    // Verificar si el token es válido:
    // 1. ¿El usuario del token coincide con el de la BD?
    // 2. ¿El token no ha expirado?
    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return (username == userDetails.username && !isTokenExpired(token))
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    // Método genérico para extraer cualquier dato del token
    fun <T> extractClaim(token: String, claimsResolver: (Claims) -> T): T {
        val claims = extractAllClaims(token)
        return claimsResolver(claims)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .body
    }
}