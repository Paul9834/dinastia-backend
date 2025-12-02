package org.marketinglab.org.marketinglab.dinastia.application.dto

data class RegistroUsuarioDto(
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String,
    val contrasena: String,
    val rolNombre: String // Ejemplo: "CLIENTE"
)