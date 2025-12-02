package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository


import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.RolEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaRolRepository : JpaRepository<RolEntity, Long> {
    // Spring crea esta consulta automáticamente basada en el nombre del método
    fun findByNombre(nombre: String): RolEntity?
}
