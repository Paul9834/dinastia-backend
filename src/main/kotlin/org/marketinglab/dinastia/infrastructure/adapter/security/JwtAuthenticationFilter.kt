package org.marketinglab.dinastia.infrastructure.adapter.security

import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        // 1. Si no hay token, dejamos pasar la petición (quizás va a un endpoint público)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val token = authHeader.substring(7)

        try {
            // 2. Intentamos leer el usuario.
            // AQUÍ ES DONDE OCURRE EL ERROR si el token expiró.
            val userEmail = jwtService.extractUsername(token)

            if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
                val userDetails = this.userDetailsService.loadUserByUsername(userEmail)

                if (jwtService.isTokenValid(token, userDetails)) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                    )
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }

            // Si todo salió bien, continuamos
            filterChain.doFilter(request, response)

        } catch (e: ExpiredJwtException) {
            // 3. CAPTURAMOS EL ERROR DE TOKEN VENCIDO
            // En lugar de explotar, devolvemos un JSON limpio con error 401
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json"
            response.writer.write("{\"error\": \"El token ha expirado\", \"code\": \"TOKEN_EXPIRED\"}")

        } catch (e: Exception) {
            // 4. Capturamos cualquier otro error (token falso, firma mala, etc)
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.contentType = "application/json"
            response.writer.write("{\"error\": \"Token inválido o malformado\"}")
        }
    }
}