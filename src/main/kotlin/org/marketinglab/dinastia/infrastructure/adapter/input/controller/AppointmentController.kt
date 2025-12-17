package org.marketinglab.dinastia.infrastructure.adapter.input.controller

import org.marketinglab.dinastia.application.dto.AppointmentResponse
import org.marketinglab.dinastia.application.dto.CreateAppointmentRequest
import org.marketinglab.dinastia.application.dto.UpdateAppointmentRequest
import org.marketinglab.dinastia.application.service.AppointmentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/appointments")
class AppointmentController(
    // Idealmente inyectarías la interfaz AppointmentUseCase,
    // pero inyectar el servicio concreto funciona por ahora.
    private val appointmentService: AppointmentService
) {

    @PostMapping
    fun create(@RequestBody request: CreateAppointmentRequest): ResponseEntity<AppointmentResponse> {
        val response = appointmentService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping
    fun listMine(@RequestParam(required = false) petId: Long?): ResponseEntity<List<AppointmentResponse>> {
        val response = appointmentService.listMine(petId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<AppointmentResponse> {
        val response = appointmentService.getById(id)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody request: UpdateAppointmentRequest
    ): ResponseEntity<AppointmentResponse> {
        val response = appointmentService.update(id, request)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        appointmentService.delete(id)
        return ResponseEntity.noContent().build()
    }
}