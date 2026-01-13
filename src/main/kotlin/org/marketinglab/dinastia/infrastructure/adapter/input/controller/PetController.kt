package org.marketinglab.dinastia.infrastructure.adapter.input.controller

import org.marketinglab.dinastia.application.dto.CreatePetRequest
import org.marketinglab.dinastia.application.dto.UpdatePetRequest
import org.marketinglab.dinastia.application.service.PetService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/pets")
@CrossOrigin(origins = ["*"])
class PetController(
    private val petService: PetService
) {

    @PostMapping
    fun create(@RequestBody request: CreatePetRequest): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.CREATED).body(petService.create(request))
    }

    @GetMapping("/mine")
    fun listMine(): ResponseEntity<Any> {
        return ResponseEntity.ok(petService.listMine())
    }

    @GetMapping("/pending")
    fun listPending(): ResponseEntity<Any> {
        return ResponseEntity.ok(petService.listPending())
    }

    @PutMapping("/{id}/assign-owner/{ownerId}")
    fun assignOwner(@PathVariable id: Long, @PathVariable ownerId: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(petService.assignOwner(id, ownerId))
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(petService.getById(id))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: UpdatePetRequest): ResponseEntity<Any> {
        return ResponseEntity.ok(petService.update(id, request))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        petService.delete(id)
        return ResponseEntity.noContent().build()
    }
}