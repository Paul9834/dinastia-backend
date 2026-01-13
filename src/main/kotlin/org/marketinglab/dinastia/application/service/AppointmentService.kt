package org.marketinglab.dinastia.application.service


import org.marketinglab.dinastia.application.dto.*
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.*
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaAppointmentRepository
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaPetRepository
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaUsuarioRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.*


@Service
class AppointmentService(
    private val appointmentRepository: JpaAppointmentRepository,
    private val petRepository: JpaPetRepository,
    private val userRepository: JpaUsuarioRepository
) {

    fun create(request: CreateAppointmentRequest): AppointmentResponse {
        val user = currentUser()

        val pet = findPetAccessible(request.petId, user.id!!)

        val entity = AppointmentEntity(
            title = request.title,
            dateTime = request.dateTime,
            type = AppointmentType.valueOf(request.type.uppercase()),
            status = AppointmentStatus.PENDING,
            notes = request.notes,
            pet = pet
        )

        return appointmentRepository.save(entity).toResponse()
    }

    fun listMine(petId: Long?): List<AppointmentResponse> {
        val user = currentUser()

        val items = if (petId != null) {
            appointmentRepository.findAllByPetIdAccessibleByUserId(petId, user.id!!)
        } else {
            appointmentRepository.findAllAccessibleByUserId(user.id!!)
        }

        return items.map { it.toResponse() }
    }

    fun getById(id: Long): AppointmentResponse {
        val user = currentUser()
        val appt = appointmentRepository.findByIdAccessibleByUserId(id, user.id!!)
            ?: throw NoSuchElementException("Appointment not found")
        return appt.toResponse()
    }

    fun update(id: Long, request: UpdateAppointmentRequest): AppointmentResponse {
        val user = currentUser()

        val current = appointmentRepository.findByIdAccessibleByUserId(id, user.id!!)
            ?: throw NoSuchElementException("Appointment not found")

        request.title?.let { current.title = it }
        request.dateTime?.let { current.dateTime = it }
        request.notes?.let { current.notes = it }

        request.type?.let { current.type = AppointmentType.valueOf(it.uppercase()) }
        request.status?.let { current.status = AppointmentStatus.valueOf(it.uppercase()) }

        return appointmentRepository.save(current).toResponse()
    }

    fun delete(id: Long) {
        val user = currentUser()
        val appt = appointmentRepository.findByIdAccessibleByUserId(id, user.id!!)
            ?: throw NoSuchElementException("Appointment not found")
        appointmentRepository.delete(appt)
    }

    private fun findPetAccessible(petId: Long, userId: Long): PetEntity {
        return petRepository.findByIdAndOwnerId(petId, userId)
            ?: petRepository.findByIdAndCreatedById(petId, userId)
            ?: throw NoSuchElementException("Pet not found")
    }

    private fun currentUser(): UsuarioEntity {
        val authentication = SecurityContextHolder.getContext().authentication
        val email = authentication?.name ?: throw IllegalStateException("Unauthenticated")
        return userRepository.findByCorreo(email) ?: throw IllegalStateException("User not found")
    }

    private fun AppointmentEntity.toResponse() = AppointmentResponse(
        id = id!!,
        petId = pet.id!!,
        petName = pet.name,
        title = title,
        dateTime = dateTime,
        type = type.name,
        status = status.name,
        notes = notes
    )
}