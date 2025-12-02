package org.marketinglab.dinastia.application.port.input

import org.marketinglab.org.marketinglab.dinastia.application.dto.RegistroUsuarioDto
import org.marketinglab.org.marketinglab.dinastia.domain.model.Usuario

interface RegistrarUsuarioUseCase {
    fun registrar(dto: RegistroUsuarioDto): Usuario
}
