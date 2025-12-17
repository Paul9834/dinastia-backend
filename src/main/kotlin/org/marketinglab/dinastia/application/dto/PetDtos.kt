package org.marketinglab.dinastia.application.dto


import java.time.LocalDate

data class CreatePetRequest(
    val name: String,
    val species: String,
    val breed: String? = null,
    val birthDate: LocalDate? = null,
    val sex: String? = null,
    val photoUrl: String? = null
)

data class UpdatePetRequest(
    val name: String? = null,
    val species: String? = null,
    val breed: String? = null,
    val birthDate: LocalDate? = null,
    val sex: String? = null,
    val photoUrl: String? = null
)

data class PetResponse(
    val id: Long,
    val name: String,
    val species: String,
    val breed: String?,
    val birthDate: LocalDate?,
    val sex: String?,
    val photoUrl: String?
)
