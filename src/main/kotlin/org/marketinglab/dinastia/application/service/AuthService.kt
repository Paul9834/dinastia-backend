package org.marketinglab.dinastia.application.service

import org.marketinglab.dinastia.infrastructure.adapter.security.JwtService
import org.marketinglab.org.marketinglab.dinastia.application.dto.LoginDto
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaUsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usuarioRepository: JpaUsuarioRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    fun login(loginDto: LoginDto): Map<String, String> {
        val usuario = usuarioRepository.findByCorreo(loginDto.correo)
            ?: throw RuntimeException("Usuario o contraseña incorrectos") // No decir cuál falló por seguridad

        if (!passwordEncoder.matches(loginDto.contrasena, usuario.contrasena)) {
            throw RuntimeException("Usuario o contraseña incorrectos")
        }

        // 3. Generar Token
        val rolNombre = usuario.rol?.nombre ?: "USER"
        val token = jwtService.generarToken(usuario.correo, rolNombre)

        // 4. Devolver
        return mapOf("token" to token)
    }
}