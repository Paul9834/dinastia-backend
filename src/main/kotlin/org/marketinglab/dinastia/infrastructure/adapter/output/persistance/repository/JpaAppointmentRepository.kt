package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.AppointmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface JpaAppointmentRepository : JpaRepository<AppointmentEntity, Long> {

    @Query(
        """
        select a from AppointmentEntity a
        where a.pet.owner.id = :userId
           or a.pet.createdBy.id = :userId
        """
    )
    fun findAllAccessibleByUserId(userId: Long): List<AppointmentEntity>

    @Query(
        """
        select a from AppointmentEntity a
        where a.pet.id = :petId
          and (a.pet.owner.id = :userId or a.pet.createdBy.id = :userId)
        """
    )
    fun findAllByPetIdAccessibleByUserId(petId: Long, userId: Long): List<AppointmentEntity>

    @Query(
        """
        select a from AppointmentEntity a
        where a.id = :id
          and (a.pet.owner.id = :userId or a.pet.createdBy.id = :userId)
        """
    )
    fun findByIdAccessibleByUserId(id: Long, userId: Long): AppointmentEntity?
}