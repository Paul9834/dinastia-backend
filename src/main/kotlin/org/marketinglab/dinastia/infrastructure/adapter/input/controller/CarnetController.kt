package org.marketinglab.dinastia.infrastructure.adapter.input.controller

import org.marketinglab.dinastia.application.dto.DesparasitacionRow
import org.marketinglab.dinastia.application.dto.VacunaRow
import org.marketinglab.dinastia.application.service.JasperCarnetService

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.ByteArrayInputStream
import java.io.InputStream

@RestController
@RequestMapping("/api/pets")
class CarnetController(
    private val jasperCarnetService: JasperCarnetService,
) {

    @GetMapping("/{id}/carnet.pdf", produces = [MediaType.APPLICATION_PDF_VALUE])
    fun carnet(@PathVariable id: Long): ResponseEntity<ByteArray> {
        val params = buildParams(fotoStream = null)

        val vacunas =

            listOf(VacunaRow("Rabia", "Zoetis", "2026-01-10"),
                VacunaRow("Rabia", "Zoetis", "2026-01-10"),
                VacunaRow("Rabia", "Zoetis", "2026-01-10"),
                VacunaRow("Rabia", "Zoetis", "2026-01-10"),
                VacunaRow("Rabia", "Zoetis", "2026-01-10"),
                VacunaRow("Rabia", "Zoetis", "2026-01-10"),
                VacunaRow("Rabia", "Zoetis", "2026-01-10"),
                VacunaRow("Rabia", "Zoetis", "2026-01-10"),
                VacunaRow("Rabia", "Zoetis", "2026-01-10")

            )

        val desparas = listOf(
            DesparasitacionRow(
                "Drontal", "2026-01-12",
                certificado = true
            )
        )

        val pdf = jasperCarnetService.generarCarnetPdf(params, vacunas, desparas)

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=carnet-$id.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf)
    }

    private fun buildParams(fotoStream: InputStream?): Map<String, Any?> {
        val safeFoto = fotoStream ?: ByteArrayInputStream(ByteArray(0))

        return mapOf(
            "nombreMascota" to "N/A",
            "especie" to "N/A",
            "raza" to "N/A",
            "sexo" to "N/A",
            "color" to "N/A",
            "fechaNacimiento" to "N/A",
            "microchip" to "N/A",
            "propietario" to "N/A",
            "fotoMascota" to safeFoto,
            "qrPayload" to "dinastia://pets"
        )
    }

    
}