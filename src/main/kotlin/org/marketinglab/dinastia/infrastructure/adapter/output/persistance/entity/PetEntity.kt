package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "pets")
data class PetEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val species: String,

    val breed: String? = null,

    val birthDate: LocalDate? = null,

    val sex: String? = null,

    val photoUrl: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UsuarioEntity
)