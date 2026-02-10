package org.marketinglab.dinastia.infrastructure.adapter.input.controller

import org.marketinglab.dinastia.application.dto.CreateDewormingRequest
import org.marketinglab.dinastia.application.service.DewormingService
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.DewormingEntity
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/api/dewormings")
class DewormingController(
    private val dewormingService: DewormingService
) {
    private val log = LoggerFactory.getLogger(DewormingController::class.java)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody req: CreateDewormingRequest): DewormingEntity {
        log.info("Creating deworming for petId={}", req.petId)
        return dewormingService.create(req)
    }

    @GetMapping("/pet/{petId}")
    fun findByPet(@PathVariable petId: Long): List<DewormingEntity> {
        log.debug("Finding dewormings for petId={}", petId)
        return dewormingService.findByPet(petId)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<DewormingEntity> {
        val found = dewormingService.findById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(found)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody deworming: DewormingEntity
    ): ResponseEntity<DewormingEntity> {
        val existing = dewormingService.findById(id) ?: return ResponseEntity.notFound().build()
        val updated = dewormingService.update(existing.id!!, deworming)
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        val existing = dewormingService.findById(id) ?: return ResponseEntity.notFound().build()
        dewormingService.delete(existing.id!!)
        return ResponseEntity.noContent().build()
    }
}