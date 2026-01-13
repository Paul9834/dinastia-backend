package org.marketinglab.dinastia.domain.model

import org.marketinglab.dinastia.application.dto.DesparasitacionRow
import org.marketinglab.dinastia.application.dto.VacunaRow

data class CarnetMascotaReport(
    val nombre: String,
    val especie: String,
    val raza: String,
    val sexo: String,
    val microchip: String?,
    val nacimiento: String,
    val propietario: String,
    val qrText: String,
    val vacunas: List<VacunaRow>,
    val desparasitaciones: List<DesparasitacionRow>
)

