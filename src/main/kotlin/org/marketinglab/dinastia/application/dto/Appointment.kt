package org.marketinglab.dinastia.application.dto

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.AppointmentEntity
import java.time.LocalDateTime

data class AppointmentResponse(
    val id: Long,
    val petId: Long,
    val petName: String,
    val title: String,
    val dateTime: LocalDateTime,
    val type: String,
    val status: String,
    val notes: String?
)

data class CreateAppointmentRequest(
    val petId: Long,
    val title: String,
    val dateTime: LocalDateTime,
    val type: String,            // “VACCINE”, “CHECKUP”… (front lo controla)
    val notes: String? = null
)

data class UpdateAppointmentRequest(
    val title: String?        = null,
    val dateTime: LocalDateTime? = null,
    val status: String?       = null,
    val notes: String?        = null,
    val type: String?
)

