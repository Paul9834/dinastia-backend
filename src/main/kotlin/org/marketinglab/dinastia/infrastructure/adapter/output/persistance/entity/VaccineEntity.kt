package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "vaccines")
class VaccineEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var name: String, // Ej: "Rabia", "Parvovirus"
    @Column(name = "veterinarian")
    var veterinarian: String? = null, // <--- NUEVO CAMPO (Ej: "Dr. Emily Carter")

    @Column(nullable = false)
    var applicationDate: LocalDate, // Cuándo se la puso

    var nextDueDate: LocalDate? = null, // Cuándo le toca la próxima (Refuerzo)

    var batchNumber: String? = null, // Lote (opcional, pero útil para historial real)

    var notes: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    var pet: PetEntity
)