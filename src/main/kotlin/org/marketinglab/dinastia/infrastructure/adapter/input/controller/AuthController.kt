package org.marketinglab.dinastia.infrastructure.adapter.input.controller


import org.marketinglab.dinastia.application.service.AuthService
import org.marketinglab.org.marketinglab.dinastia.application.dto.LoginDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["*"])
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginDto: LoginDto): ResponseEntity<Any> {
        return try {
            val respuesta = authService.login(loginDto)
            ResponseEntity.ok(respuesta)
        } catch (e: Exception) {
            ResponseEntity.status(401).body(mapOf("error" to e.message))
        }
    }
}