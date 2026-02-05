package org.marketinglab.dinastia.application.service

import org.marketinglab.dinastia.application.dto.DesparasitacionRow
import org.marketinglab.dinastia.application.dto.PetResponse
import org.marketinglab.dinastia.application.dto.VacunaRow
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.InputStream

data class CarnetData(
    val pet: PetResponse,
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
    private val tokenService: CarnetTokenService
) : CarnetQueryService {

    override fun getCarnetData(petId: Long): CarnetData {

        val petEntity = petService.getByIdPublic(petId)
        val pet = petService.mapToResponse(petEntity)

        val vacunas = vaccineService.listByPetPublic(petId).map { v ->
            VacunaRow(
                nombreVacuna = v.name,
                laboratorio = v.veterinarian,
                fechaAplicacion = v.applicationDate.toString()
            )
        }

        val desparas = emptyList<DesparasitacionRow>()

        val fotoStream: InputStream = uploadFileService.openPetPhotoByPublicUrl(pet.photoUrl) ?: ByteArrayInputStream(ByteArray(0))

        val token = tokenService.generateToken(petId)

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
            "qrPayload" to "http://localhost:9090/verify/pet/$petId/$token"
        )

        return CarnetData(
            pet = pet,
            params = params,
            vacunas = vacunas,
            desparas = desparas
        )
    }}