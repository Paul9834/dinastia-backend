package org.marketinglab.dinastia.application.service

import org.marketinglab.org.marketinglab.dinastia.application.dto.RegistroUsuarioDto
import org.marketinglab.dinastia.application.port.input.RegistrarUsuarioUseCase
import org.marketinglab.org.marketinglab.dinastia.domain.model.Usuario
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.UsuarioEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaRolRepository
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaUsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class RegistrarUsuarioService(
    private val usuarioRepository: JpaUsuarioRepository,
    private val rolRepository: JpaRolRepository,
    private val passwordEncoder: PasswordEncoder // 1. Inyectamos el encriptador
) : RegistrarUsuarioUseCase {

    override fun registrar(dto: RegistroUsuarioDto): Usuario {
        // 1. Buscar el rol en la BD
        val rol = rolRepository.findByNombre(dto.rolNombre)
            ?: throw RuntimeException("El rol ${dto.rolNombre} no existe")

        // 2. Convertir DTO a Entidad
        val entidad = UsuarioEntity(
            nombre = dto.nombre,
            apellido = dto.apellido,
            correo = dto.correo,
            telefono = dto.telefono,
            contrasena = passwordEncoder.encode(dto.contrasena),
            rol = rol
        )

        // 3. Guardar en BD
        val usuarioGuardado = usuarioRepository.save(entidad)

        // 4. Retornar modelo de dominio
        return Usuario(
            id = usuarioGuardado.id,
            nombre = usuarioGuardado.nombre,
            apellido = usuarioGuardado.apellido,
            correo = usuarioGuardado.correo,
            telefono = usuarioGuardado.telefono ?: "",
            rol = usuarioGuardado.rol?.nombre ?: "SIN_ROL"
        )
    }
}
