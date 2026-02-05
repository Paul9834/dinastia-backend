    package org.marketinglab.dinastia.application.service

    import org.marketinglab.dinastia.application.dto.CreatePetRequest
    import org.marketinglab.dinastia.application.dto.PetResponse
    import org.marketinglab.dinastia.application.dto.UpdatePetRequest
    import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.PetEntity
    import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.PetSex
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
            val actor = currentUser()

            val entity = PetEntity(
                name = request.name,
                species = request.species,
                breed = request.breed,
                birthDate = request.birthDate,
                sex = request.sex ?: PetSex.UNKNOWN,
                photoUrl = request.photoUrl,
                owner = actor,
                createdBy = actor
            )

            return petRepository.save(entity).toResponse()
        }

        fun getByIdPublic(id: Long): PetEntity {
            return petRepository.findById(id)
                .orElseThrow { NoSuchElementException("Pet not found") }
        }

        fun listMine(): List<PetResponse> {
            val actor = currentUser()
            return petRepository.findAllByOwnerId(actor.id!!).map { it.toResponse() }
        }

        fun listPending(): List<PetResponse> {
            val actor = currentUser()
            return petRepository.findAllByCreatedByIdAndOwnerIsNull(actor.id!!).map { it.toResponse() }
        }

        fun getById(id: Long): PetResponse {
            val actor = currentUser()
            val pet = petRepository.findById(id).orElseThrow { NoSuchElementException("Pet not found") }

            if (!canAccess(pet, actor)) throw IllegalAccessException("Forbidden")
            return pet.toResponse()
        }

        fun update(id: Long, request: UpdatePetRequest): PetResponse {
            val actor = currentUser()
            val current = petRepository.findById(id).orElseThrow { NoSuchElementException("Pet not found") }

            if (!canAccess(current, actor)) throw IllegalAccessException("Forbidden")

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
            val actor = currentUser()
            val pet = petRepository.findById(id).orElseThrow { NoSuchElementException("Pet not found") }

            if (!canAccess(pet, actor)) throw IllegalAccessException("Forbidden")
            petRepository.delete(pet)
        }

        fun assignOwner(petId: Long, ownerId: Long): PetResponse {
            val actor = currentUser()
            val pet = petRepository.findById(petId).orElseThrow { NoSuchElementException("Pet not found") }

            val isAdminOrVet = actor.rol?.nombre in setOf("ADMIN", "VET")
            val isCreator = pet.createdBy.id == actor.id

            if (!isAdminOrVet && !isCreator) throw IllegalAccessException("Forbidden")

            val owner = userRepository.findById(ownerId).orElseThrow { NoSuchElementException("Owner not found") }

            val updated = pet.copy(owner = owner)
            return petRepository.save(updated).toResponse()
        }

        private fun canAccess(pet: PetEntity, actor: UsuarioEntity): Boolean {
            val isAdminOrVet = actor.rol?.nombre in setOf("ADMIN", "VET")
            val isOwner = pet.owner?.id != null && pet.owner?.id == actor.id
            val isCreator = pet.createdBy.id == actor.id
            return isAdminOrVet || isOwner || isCreator
        }

        private fun currentUser(): UsuarioEntity {
            val authentication = SecurityContextHolder.getContext().authentication
            val email = authentication?.name ?: throw IllegalStateException("Unauthenticated")
            return userRepository.findByCorreo(email) ?: throw IllegalStateException("User not found")
        }

        fun mapToResponse(entity: PetEntity): PetResponse {
            return PetResponse(
                id = entity.id!!,
                name = entity.name,
                species = entity.species,
                breed = entity.breed,
                birthDate = entity.birthDate,
                sex = entity.sex,
                photoUrl = entity.photoUrl
            )
        }

        private fun PetEntity.toResponse(): PetResponse {
            return PetResponse(
                id = id!!,
                name = name,
                species = species,
                breed = breed,
                birthDate = birthDate,
                sex = sex,
                photoUrl = photoUrl
            )
        }
    }