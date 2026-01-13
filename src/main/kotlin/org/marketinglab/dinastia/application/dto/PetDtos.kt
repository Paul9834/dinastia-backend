package org.marketinglab.dinastia.application.dto


import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.PetEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.PetSex
import java.time.LocalDate

data class CreatePetRequest(
    val name: String,
    val species: String,
    val breed: String? = null,
    val birthDate: LocalDate? = null,
    val sex: PetSex? = null,
    val photoUrl: String? = null
)

data class UpdatePetRequest(
    val name: String? = null,
    val species: String? = null,
    val breed: String? = null,
    val birthDate: LocalDate? = null,
    val sex: PetSex? = null,
    val photoUrl: String? = null
)

data class PetResponse(
    val id: Long,
    val name: String,
    val species: String,
    val breed: String?,
    val birthDate: LocalDate?,
    val sex: PetSex,
    val photoUrl: String?
)

private fun PetEntity.toResponse() = PetResponse(
    id = id!!,
    name = name,
    species = species,
    breed = breed,
    birthDate = birthDate,
    sex = sex,
    photoUrl = photoUrl
)

