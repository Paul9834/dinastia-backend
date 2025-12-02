package org.marketinglab.org.marketinglab.dinastia.infrastructure.config



import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.entity.RolEntity
import org.marketinglab.dinastia.infrastructure.adapter.output.persistance.repository.JpaRolRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CargaDatosIniciales {

    @Bean
    fun iniciarDatos(rolRepository: JpaRolRepository): CommandLineRunner {
        return CommandLineRunner {
            // Verificamos si ya existen roles para no duplicarlos
            if (rolRepository.count() == 0L) {
                val rolAdmin = RolEntity(nombre = "ADMIN")
                val rolCliente = RolEntity(nombre = "CLIENTE")
                rolRepository.saveAll(listOf(rolAdmin, rolCliente))
                println("✅ Roles iniciales (ADMIN, CLIENTE) creados correctamente.")
            }
        }
    }
}
