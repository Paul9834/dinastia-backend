package org.marketinglab.dinastia

import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.RolEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.UsuarioEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaRolRepository
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaUsuarioRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class DinastiaApplication {

    @Bean
    fun initData(
        rolRepo: JpaRolRepository,
        usuarioRepo: JpaUsuarioRepository
    ): CommandLineRunner {
        return CommandLineRunner {
            println("\n---------- INICIANDO PRUEBA DE BASE DE DATOS ----------")

            // 1. CREAR ROLES (Si no existen)
            if (rolRepo.count() == 0L) {
                rolRepo.saveAll(listOf(
                    RolEntity(nombre = "Administrador"),
                    RolEntity(nombre = "Cliente"),
                    RolEntity(nombre = "Asesor")
                ))
                println("✅ Roles creados.")
            }

            // 2. CREAR UN USUARIO DE PRUEBA
            val emailPrueba = "cliente@test.com"

            // Verificamos si ya existe para no crearlo doble
            if (usuarioRepo.findByCorreo(emailPrueba) == null) {

                // Buscamos el rol 'Cliente' que acabamos de crear
                val rolCliente = rolRepo.findAll().find { it.nombre == "Cliente" }

                if (rolCliente != null) {
                    val nuevoUsuario = UsuarioEntity(
                        nombre = "Juan",
                        apellido = "Perez",
                        correo = emailPrueba,
                        telefono = "3001234567",
                        contrasena = "123456",
                        rol = rolCliente // <--- AQUÍ UNIMOS LA RELACIÓN
                    )

                    usuarioRepo.save(nuevoUsuario)
                }
            } else {
                println("ℹ️ El usuario de prueba ya existe.")
            }

            println("-------------------------------------------------------\n")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<DinastiaApplication>(*args)
}