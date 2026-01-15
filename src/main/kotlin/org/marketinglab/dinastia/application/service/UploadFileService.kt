package org.marketinglab.dinastia.application.service


import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path

@Service
class UploadFileService(
    @Value("\${app.upload.dir}") private val uploadBaseDir: String
) {

    fun openPetPhotoByPublicUrl(publicUrl: String?): InputStream? {
        if (publicUrl.isNullOrBlank()) return null

        val filename = publicUrl.substringAfterLast('/')
        if (filename.isBlank()) return null

        val path: Path = Path.of(uploadBaseDir, "pets", filename)
        if (!Files.exists(path)) return null

        return Files.newInputStream(path)
    }
}