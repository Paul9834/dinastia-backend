package org.marketinglab.dinastia.application.service

import org.marketinglab.dinastia.application.dto.CreateVaccineRequest
import org.marketinglab.dinastia.application.dto.VaccineResponse
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.UsuarioEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.VaccineEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaPetRepository
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaUsuarioRepository
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaVaccineRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.NoSuchElementException

@Service
class VaccineService(
    private val vaccineRepository: JpaVaccineRepository,
    private val petRepository: JpaPetRepository,
    private val userRepository: JpaUsuarioRepository
) {

    fun create(request: CreateVaccineRequest): VaccineResponse {
        val user = currentUser()

        val pet = petRepository.findByIdAndUserId(request.petId, user.id!!)
            ?: throw NoSuchElementException("Pet not found")

        val entity = VaccineEntity(
            name = request.name,
            applicationDate = request.applicationDate,
            nextDueDate = request.nextDueDate,
            batchNumber = request.batchNumber,
            notes = request.notes,
            veterinarian = request.veterinarian, // <--- ¡ASEGÚRATE DE TENER ESTA LÍNEA!
            pet = pet
        )

        return vaccineRepository.save(entity).toResponse()
    }

    fun listByPet(petId: Long): List<VaccineResponse> {
        val user = currentUser()
        // El repositorio ya filtra por userId, así que es seguro
        val vaccines = vaccineRepository.findAllByPetIdAndPetUserId(petId, user.id!!)
        return vaccines.map { it.toResponse() }
    }

    fun delete(id: Long) {
        val user = currentUser()
        val vaccine = vaccineRepository.findByIdAndPetUserId(id, user.id!!)
            ?: throw NoSuchElementException("Vaccine not found")
        vaccineRepository.delete(vaccine)
    }

    // --- Helpers ---

    private fun currentUser(): UsuarioEntity {
        val authentication = SecurityContextHolder.getContext().authentication
        val email = authentication?.name ?: throw IllegalStateException("Unauthenticated")
        return userRepository.findByCorreo(email) ?: throw IllegalStateException("User not found")
    }

    private fun VaccineEntity.toResponse(): VaccineResponse {
        // Lógica simple para saber si está vencida
        val isOverdue = nextDueDate != null && nextDueDate!!.isBefore(LocalDate.now())
        val statusText = if (isOverdue) "VENCIDA" else "VIGENTE"

        return VaccineResponse(
            id = id,
            petId = pet.id!!,
            petName = pet.name,
            name = name,
            applicationDate = applicationDate,
            nextDueDate = nextDueDate,
            veterinarian = veterinarian,
            status = statusText
        )
    }
}