package org.marketinglab.dinastia.application.service

import org.marketinglab.dinastia.application.dto.DesparasitacionRow
import org.marketinglab.dinastia.application.dto.VacunaRow
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.InputStream

data class CarnetData(
    val params: Map<String, Any?>,
    val vacunas: List<VacunaRow>,
    val desparas: List<DesparasitacionRow>,
)

interface CarnetQueryService {
    fun getCarnetData(petId: Long): CarnetData
}

@Service
class CarnetQueryServiceImpl(
    private val petService: PetService,
    private val vaccineService: VaccineService,
    private val uploadFileService: UploadFileService,
) : CarnetQueryService {

    override fun getCarnetData(petId: Long): CarnetData {
        val pet = petService.getById(petId)

        val vacunas = vaccineService.listByPet(petId).map { v ->
            VacunaRow(
                nombreVacuna = v.name,
                laboratorio = v.veterinarian,
                fechaAplicacion = v.applicationDate.toString()
            )
        }

        val desparas = emptyList<DesparasitacionRow>()

        val fotoStream: InputStream =
            uploadFileService.openPetPhotoByPublicUrl(pet.photoUrl)
                ?: ByteArrayInputStream(ByteArray(0))

        val params = mapOf(
            "nombreMascota" to (pet.name.ifBlank { "N/A" }),
            "especie" to (pet.species.ifBlank { "N/A" }),
            "raza" to (pet.breed?.ifBlank { "N/A" } ?: "N/A"),
            "sexo" to pet.sex.name,
            "color" to "N/A",
            "fechaNacimiento" to (pet.birthDate?.toString() ?: "N/A"),
            "microchip" to "N/A",
            "propietario" to "N/A",
            "fotoMascota" to fotoStream,
            "qrPayload" to "dinastia://pets/$petId"
        )

        return CarnetData(
            params = params,
            vacunas = vacunas,
            desparas = desparas
        )
    }
}