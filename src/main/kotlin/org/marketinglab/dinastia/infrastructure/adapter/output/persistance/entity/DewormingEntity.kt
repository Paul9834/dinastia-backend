package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    name = "dewormings",
    indexes = [
        Index(name = "ix_dewormings_pet_id", columnList = "pet_id")
    ]
)
class DewormingEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var product: String,

    @Column
    var method: String? = null,

    @Column(name = "veterinarian")
    var veterinarian: String? = null,

    @Column(name = "application_date", nullable = false)
    var applicationDate: LocalDate,

    @Column(name = "next_due_date")
    var nextDueDate: LocalDate? = null,

    @Column
    var notes: String? = null,

    @Column(name = "verified", nullable = false)
    var verified: Boolean = true,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    var pet: PetEntity
)