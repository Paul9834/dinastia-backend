package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.PetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaPetRepository : JpaRepository<PetEntity, Long> {
    fun findAllByUserId(userId: Long): List<PetEntity>
    fun findByIdAndUserId(id: Long, userId: Long): PetEntity?
}