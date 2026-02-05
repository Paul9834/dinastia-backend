package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.VaccineEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JpaVaccineRepository : JpaRepository<VaccineEntity, Long> {

    @Query(
        """
        select v from VaccineEntity v
        where v.pet.id = :petId
          and (
                v.pet.owner.id = :userId
             or v.pet.createdBy.id = :userId
          )
        """
    )
    fun findAllByPetIdAccessibleByUserId(petId: Long, userId: Long): List<VaccineEntity>

    @Query(
        """
        select v from VaccineEntity v
        where v.id = :id
          and (
                v.pet.owner.id = :userId
             or v.pet.createdBy.id = :userId
          )
        """
    )
    fun findByIdAccessibleByUserId(id: Long, userId: Long): VaccineEntity?

    fun findAllByPetId(petId: Long): List<VaccineEntity>
}