package org.marketinglab.dinastia.infrastructure.adapter.security

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

        // 1. Revisar si trae el header Authorization: Bearer ...
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        // 2. Extraer el token y el usuario
        val token = authHeader.substring(7) // Quitar "Bearer "
        val userEmail = jwtService.extractUsername(token)

        // 3. Si hay usuario y nadie está autenticado aún en el contexto
        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = this.userDetailsService.loadUserByUsername(userEmail)

            // 4. Validar token
            if (jwtService.isTokenValid(token, userDetails)) {
                val authToken = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

                // 5. ¡Aprobado! Registrar en el contexto de seguridad
                SecurityContextHolder.getContext().authentication = authToken
            }
        }
        filterChain.doFilter(request, response)
    }
}