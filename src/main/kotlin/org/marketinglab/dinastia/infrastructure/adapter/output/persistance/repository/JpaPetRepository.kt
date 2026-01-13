package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.PetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaPetRepository : JpaRepository<PetEntity, Long> {

    fun findAllByOwnerId(ownerId: Long): List<PetEntity>

    fun findAllByCreatedByIdAndOwnerIsNull(createdById: Long): List<PetEntity>

    fun findByIdAndOwnerId(id: Long, ownerId: Long): PetEntity?

    fun findByIdAndCreatedById(id: Long, createdById: Long): PetEntity?
}