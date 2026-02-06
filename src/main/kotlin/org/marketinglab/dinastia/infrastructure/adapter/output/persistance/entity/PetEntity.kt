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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    val sex: PetSex = PetSex.UNKNOWN,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    val status: PetStatus = PetStatus.ACTIVO,
    
    val photoUrl: String? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "owner_user_id", nullable = true)
    val owner: UsuarioEntity? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by_user_id", nullable = false)
    val createdBy: UsuarioEntity
)