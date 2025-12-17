package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.UsuarioEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaUsuarioRepository : JpaRepository<UsuarioEntity, Long> {

    fun findByCorreo(correo: String): UsuarioEntity?

    fun existsByCorreo(correo: String): Boolean
}