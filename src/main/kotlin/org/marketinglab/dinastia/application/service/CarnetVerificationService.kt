package org.marketinglab.dinastia.application.service
import org.springframework.stereotype.Service

data class VerificationResult(
    val valid: Boolean,
    val carnetData: CarnetData?
)

interface CarnetVerificationService {
    fun verifyPetCarnet(petId: Long, token: String): VerificationResult
}

@Service
class CarnetVerificationServiceImpl(
    private val carnetQueryService: CarnetQueryService,
    private val tokenService: CarnetTokenService
) : CarnetVerificationService {

    override fun verifyPetCarnet(petId: Long, token: String): VerificationResult {

        val validToken = tokenService.validateToken(petId, token)
        if (!validToken) {
            return VerificationResult(false, null)
        }

        val carnetData = try {
            carnetQueryService.getCarnetData(petId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }


        println("TOKEN ESPERADO verifyPetCarnet = ${tokenService.generateToken(petId)}")
        println("TOKEN RECIBIDO verifyPetCarnet = $token")

        return VerificationResult(
            valid = carnetData != null,
            carnetData = carnetData
        )
    }
}