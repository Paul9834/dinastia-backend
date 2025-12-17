package org.marketinglab.dinastia.application.service

import org.marketinglab.dinastia.application.dto.CreatePetRequest
import org.marketinglab.dinastia.application.dto.PetResponse
import org.marketinglab.dinastia.application.dto.UpdatePetRequest
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.PetEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.UsuarioEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaPetRepository
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaUsuarioRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class PetService(
    private val petRepository: JpaPetRepository,
    private val userRepository: JpaUsuarioRepository
) {

    fun create(request: CreatePetRequest): PetResponse {
        val user = currentUser()

        val entity = PetEntity(
            name = request.name,
            species = request.species,
            breed = request.breed,
            birthDate = request.birthDate,
            sex = request.sex,
            photoUrl = request.photoUrl,
            user = user
        )

        return petRepository.save(entity).toResponse()
    }

    fun listMine(): List<PetResponse> {
        val user = currentUser()
        return petRepository.findAllByUserId(user.id!!).map { it.toResponse() }
    }

    fun getById(id: Long): PetResponse {
        val user = currentUser()
        val pet = petRepository.findByIdAndUserId(id, user.id!!)
            ?: throw NoSuchElementException("Pet not found")
        return pet.toResponse()
    }

    fun update(id: Long, request: UpdatePetRequest): PetResponse {
        val user = currentUser()
        val current = petRepository.findByIdAndUserId(id, user.id!!)
            ?: throw NoSuchElementException("Pet not found")

        val updated = current.copy(
            name = request.name ?: current.name,
            species = request.species ?: current.species,
            breed = request.breed ?: current.breed,
            birthDate = request.birthDate ?: current.birthDate,
            sex = request.sex ?: current.sex,
            photoUrl = request.photoUrl ?: current.photoUrl
        )

        return petRepository.save(updated).toResponse()
    }

    fun delete(id: Long) {
        val user = currentUser()
        val pet = petRepository.findByIdAndUserId(id, user.id!!)
            ?: throw NoSuchElementException("Pet not found")
        petRepository.delete(pet)
    }

    private fun currentUser(): UsuarioEntity {
        val authentication = SecurityContextHolder.getContext().authentication
        val email = authentication?.name ?: throw IllegalStateException("Unauthenticated")
        return userRepository.findByCorreo(email) ?: throw IllegalStateException("User not found")
    }

    private fun PetEntity.toResponse() = PetResponse(
        id = id!!,
        name = name,
        species = species,
        breed = breed,
        birthDate = birthDate,
        sex = sex,
        photoUrl = photoUrl
    )
}