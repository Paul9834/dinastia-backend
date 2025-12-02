package org.marketinglab.dinastia.infrastructure.adapter.input.controller

import org.marketinglab.dinastia.application.port.input.RegistrarUsuarioUseCase
import org.marketinglab.org.marketinglab.dinastia.application.dto.RegistroUsuarioDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = ["*"])
class UsuarioController(
    private val registrarUsuarioUseCase: RegistrarUsuarioUseCase
) {

    @PostMapping("/registro")
    fun registrar(@RequestBody dto: RegistroUsuarioDto): ResponseEntity<Any> {
        return try {
            val usuarioCreado = registrarUsuarioUseCase.registrar(dto)
            ResponseEntity.status(HttpStatus.CREATED).body(usuarioCreado)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mapOf("error" to e.message))
        }
    }
}