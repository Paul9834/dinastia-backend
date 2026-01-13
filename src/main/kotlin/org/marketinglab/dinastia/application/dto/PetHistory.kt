package org.marketinglab.dinastia.application.dto

data class VacunaRow(
    val nombreVacuna: String,
    val laboratorio: String?,
    val fechaAplicacion: String
)

data class DesparasitacionRow(
    val nombreProducto: String,
    val fechaAplicacion: String,
    val certificado: Boolean
)