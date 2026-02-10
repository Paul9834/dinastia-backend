package org.marketinglab.dinastia.application.dto

import java.time.LocalDate

data class CreateDewormingRequest(
    val product: String,
    val method: String?,
    val veterinarian: String?,
    val applicationDate: LocalDate,
    val nextDueDate: LocalDate?,
    val notes: String?,
    val verified: Boolean,
    val petId: Long
)
