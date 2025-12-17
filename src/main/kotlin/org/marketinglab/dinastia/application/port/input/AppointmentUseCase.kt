package org.marketinglab.dinastia.application.port.input

import org.marketinglab.dinastia.application.dto.*
import java.util.*

interface AppointmentUseCase {
    fun create(req: CreateAppointmentRequest): AppointmentResponse
    fun update(id: Long, req: UpdateAppointmentRequest): AppointmentResponse
    fun delete(id: Long)
    fun findById(id: Long): AppointmentResponse
    fun findByPet(petId: Long): List<AppointmentResponse>
}