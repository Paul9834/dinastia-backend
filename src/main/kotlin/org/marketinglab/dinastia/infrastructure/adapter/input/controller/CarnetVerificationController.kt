package org.marketinglab.dinastia.infrastructure.adapter.input.controller

import org.marketinglab.dinastia.application.service.CarnetVerificationService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/verify/pet")
class CarnetVerificationController(
    private val verificationService: CarnetVerificationService
) {

    @GetMapping("/{petId}/{token:[A-Za-z0-9_-]+}")
    fun verifyPet(
        @PathVariable petId: Long,
        @PathVariable token: String,
        model: Model
    ): String {

        val result = verificationService.verifyPetCarnet(petId, token)

        model.addAttribute("valid", result.valid)

        if (result.valid && result.carnetData != null) {
            model.addAttribute("pet", result.carnetData.pet)
            model.addAttribute("params", result.carnetData.params)
            model.addAttribute("vacunas", result.carnetData.vacunas)
            model.addAttribute("desparas", result.carnetData.desparas)
        }

        println("TOKEN RECIBIDO = $token")

        return "carnet-verification"
    }
}