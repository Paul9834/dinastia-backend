package org.marketinglab.dinastia.infrastructure.adapter.input.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.util.*

@RestController
@RequestMapping("/api/uploads")
class UploadController(

    @Value("\${app.upload.dir}")
    private val uploadBaseDir: String

) {

    @PostMapping(
        value = ["/pets/photo"],
        consumes = ["multipart/form-data"]
    )
    fun uploadPetPhoto(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<Map<String, String>> {

        if (file.isEmpty) {
            return ResponseEntity.badRequest()
                .body(mapOf("error" to "Archivo vacío"))
        }

        val petsDir: Path = Path.of(uploadBaseDir, "pets")
        Files.createDirectories(petsDir)

        val filename = "${UUID.randomUUID()}-${file.originalFilename}"
        val targetPath = petsDir.resolve(filename)

        // ✅ COPY seguro (no transferTo)
        Files.copy(file.inputStream, targetPath)

        val publicUrl = "/uploads/pets/$filename"

        return ResponseEntity.ok(mapOf("url" to publicUrl))
    }
}
