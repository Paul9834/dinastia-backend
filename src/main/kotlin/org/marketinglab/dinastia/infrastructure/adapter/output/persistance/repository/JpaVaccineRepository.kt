package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.VaccineEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaVaccineRepository : JpaRepository<VaccineEntity, Long> {

    // Buscar todas las vacunas de una mascota específica (validando dueño)
    fun findAllByPetIdAndPetUserId(petId: Long, userId: Long): List<VaccineEntity>

    // Buscar una vacuna específica (validando dueño)
    fun findByIdAndPetUserId(id: Long, userId: Long): VaccineEntity?
}