package org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity
import jakarta.persistence.*

@Entity
@Table(name = "usuarios")
data class UsuarioEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nombre: String,
    val apellido: String,
    val correo: String,
    val telefono: String?,
    val contrasena: String,

    @ManyToOne
    @JoinColumn(name = "rol_id")
    val rol: RolEntity?
)