package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity


import jakarta.persistence.*

@Entity
@Table(name = "roles")
data class RolEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nombre: String
)