package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.DewormingEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DewormingRepository : JpaRepository<DewormingEntity, Long> {
    fun findByPetId(petId: Long): List<DewormingEntity>
}
