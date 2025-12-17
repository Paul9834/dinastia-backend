package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity
import jakarta.persistence.*

@Entity
@Table(
    name = "usuarios",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["correo"])
    ]
)
data class UsuarioEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val nombre: String,

    @Column(nullable = false)
    val apellido: String,

    @Column(nullable = false, unique = true)
    val correo: String,

    val telefono: String?,

    @Column(nullable = false)
    val contrasena: String,

    @ManyToOne
    @JoinColumn(name = "rol_id")
    val rol: RolEntity?
)