package org.marketinglab.dinastia.infrastructure.adapter.input.controller

import org.marketinglab.dinastia.application.dto.DesparasitacionRow
import org.marketinglab.dinastia.application.dto.VacunaRow
import org.marketinglab.dinastia.application.service.JasperCarnetService
import org.marketinglab.dinastia.application.service.CarnetQueryService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pets")
class CarnetController(
    private val carnetQueryService: CarnetQueryService,
    private val jasperCarnetService: JasperCarnetService,
) {

    @GetMapping("/{id}/carnet.pdf", produces = [MediaType.APPLICATION_PDF_VALUE])
    fun carnet(@PathVariable id: Long): ResponseEntity<ByteArray> {
        val data = carnetQueryService.getCarnetData(petId = id)

        val pdf = jasperCarnetService.generarCarnetPdf(
            params = data.params,
            vacunas = data.vacunas,
            desparasitaciones = data.desparas
        )

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=carnet-$id.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf)
    }
}
