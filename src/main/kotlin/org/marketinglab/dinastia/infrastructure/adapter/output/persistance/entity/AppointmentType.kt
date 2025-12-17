package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity

enum class AppointmentStatus {
    PENDING,
    COMPLETED,
    CANCELLED,
    OVERDUE
}

enum class AppointmentType {
    VACCINE,    // Vacuna
    CHECKUP,    // Chequeo
    SURGERY,    // Cirugía
    GROOMING,   // Peluquería
    OTHER       // Otro
}