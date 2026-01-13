package org.marketinglab.dinastia.domain.model

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

data class VacunaRow(
    val vacunaLab: String,
    val fechaAplicacion: String,
    val certificado: String
)

data class DesparasitacionRow(
    val productoMetodo: String,
    val fechaAplicacion: String,
    val certificado: String
)