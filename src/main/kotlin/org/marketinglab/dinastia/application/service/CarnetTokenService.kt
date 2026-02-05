package org.marketinglab.dinastia.application.service

import org.springframework.stereotype.Service
import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

interface CarnetTokenService {
    fun generateToken(petId: Long): String
    fun validateToken(petId: Long, token: String): Boolean
}

@Service
class CarnetTokenServiceImpl : CarnetTokenService {

    private val secret = "DINASTIA_SUPER_SECRET"

    override fun generateToken(petId: Long): String {
        return hmacSha256(petId.toString())
    }

    override fun validateToken(petId: Long, token: String): Boolean {
        return MessageDigest.isEqual(
            generateToken(petId).toByteArray(Charsets.UTF_8),
            token.toByteArray(Charsets.UTF_8)
        )
    }

    private fun hmacSha256(data: String): String {

        val mac = Mac.getInstance("HmacSHA256")

        val secretKey = SecretKeySpec(
            secret.toByteArray(Charsets.UTF_8),
            "HmacSHA256"
        )

        mac.init(secretKey)

        val raw = mac.doFinal(
            data.toByteArray(Charsets.UTF_8)
        )

        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(raw)
    }
}