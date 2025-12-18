package org.marketinglab.dinastia.application.dto

import java.time.LocalDate

data class CreateVaccineRequest(
    val petId: Long,
    val name: String,
    val applicationDate: LocalDate,
    val nextDueDate: LocalDate?,
    val batchNumber: String?,
    val notes: String?,
    val veterinarian: String?
)

data class VaccineResponse(
    val id: Long,
    val petId: Long,
    val veterinarian: String?,
    val petName: String, // Importante para el frontend
    val name: String,
    val applicationDate: LocalDate,
    val nextDueDate: LocalDate?,
    val status: String // Calculado: "VIGENTE" o "VENCIDA"
)