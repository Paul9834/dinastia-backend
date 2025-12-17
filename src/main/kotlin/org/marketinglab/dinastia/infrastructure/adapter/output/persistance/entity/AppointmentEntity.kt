package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity @Table(name = "appointments")
class AppointmentEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false) var title: String,
    @Column(nullable = false) var dateTime: LocalDateTime,
    @Enumerated(EnumType.STRING) var type: AppointmentType,
    @Enumerated(EnumType.STRING) var status: AppointmentStatus = AppointmentStatus.PENDING,
    var notes: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    var pet: PetEntity
)