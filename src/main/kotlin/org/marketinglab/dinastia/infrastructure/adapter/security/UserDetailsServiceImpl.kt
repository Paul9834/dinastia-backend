package org.marketinglab.dinastia.infrastructure.adapter.security

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaUsuarioRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val usuarioRepository: JpaUsuarioRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        // Buscamos por correo, ya que es nuestro identificador principal
        val usuario = usuarioRepository.findByCorreo(username)
            ?: throw UsernameNotFoundException("Usuario no encontrado: $username")

        // Convertimos tu Entidad a un objeto User de Spring Security
        return User.builder()
            .username(usuario.correo)
            .password(usuario.contrasena)
            .roles(usuario.rol?.nombre ?: "USER")
            .build()
    }
}