package org.marketinglab.org.marketinglab.dinastia.domain.model

data class Usuario(
    val id: Long? = null,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String,
    val rol: String
)
