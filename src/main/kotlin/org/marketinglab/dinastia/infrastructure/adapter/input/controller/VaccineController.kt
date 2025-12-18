package org.marketinglab.dinastia.infrastructure.adapter.input.controller

import org.marketinglab.dinastia.application.dto.CreateVaccineRequest
import org.marketinglab.dinastia.application.dto.VaccineResponse
import org.marketinglab.dinastia.application.service.VaccineService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/vaccines")
class VaccineController(
    private val vaccineService: VaccineService
) {

    // 1. Crear una nueva vacuna
    @PostMapping
    fun create(@RequestBody request: CreateVaccineRequest): ResponseEntity<VaccineResponse> {
        val response = vaccineService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    // 2. Listar vacunas por mascota
    // Uso: GET /api/v1/vaccines?petId=5
    @GetMapping
    fun listByPet(@RequestParam petId: Long): ResponseEntity<List<VaccineResponse>> {
        val response = vaccineService.listByPet(petId)
        return ResponseEntity.ok(response)
    }

    // 3. Eliminar una vacuna (por si hubo error al registrar)
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        vaccineService.delete(id)
        return ResponseEntity.noContent().build()
    }
}