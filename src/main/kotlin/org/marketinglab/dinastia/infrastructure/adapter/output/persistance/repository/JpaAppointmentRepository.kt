package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.AppointmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaAppointmentRepository : JpaRepository<AppointmentEntity, Long> {

    fun findAllByPetUserId(userId: Long): List<AppointmentEntity>

    fun findAllByPetIdAndPetUserId(petId: Long, userId: Long): List<AppointmentEntity>

    fun findByIdAndPetUserId(id: Long, userId: Long): AppointmentEntity?
}