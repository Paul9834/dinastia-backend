package org.marketinglab.dinastia.application.service

import org.marketinglab.dinastia.application.dto.CreateDewormingRequest
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.DewormingEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.PetEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.DewormingRepository
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaPetRepository
import org.springframework.stereotype.Service

@Service
class DewormingService(
    private val dewormingRepository: DewormingRepository,
    private val petRepository: JpaPetRepository

) {

    fun create(req: CreateDewormingRequest): DewormingEntity {

        val pet = petRepository.findById(req.petId)
            .orElseThrow { RuntimeException("Pet not found") }

        val entity = DewormingEntity(
            product = req.product,
            method = req.method,
            veterinarian = req.veterinarian,
            applicationDate = req.applicationDate,
            nextDueDate = req.nextDueDate,
            notes = req.notes,
            verified = req.verified ?: true,
            pet = pet
        )

        return dewormingRepository.save(entity)
    }

    fun findByPet(petId: Long): List<DewormingEntity> {
        return dewormingRepository.findByPetId(petId)
    }

    fun findById(id: Long): DewormingEntity? {
        return dewormingRepository.findById(id).orElse(null)
    }

    fun update(id: Long, updated: DewormingEntity): DewormingEntity {
        val existing = dewormingRepository.findById(id)
            .orElseThrow { RuntimeException("Deworming not found") }

        existing.product = updated.product
        existing.method = updated.method
        existing.veterinarian = updated.veterinarian
        existing.applicationDate = updated.applicationDate
        existing.nextDueDate = updated.nextDueDate
        existing.notes = updated.notes
        existing.verified = updated.verified
        existing.pet = updated.pet

        return dewormingRepository.save(existing)
    }

    fun delete(id: Long) {
        dewormingRepository.deleteById(id)
    }
}

