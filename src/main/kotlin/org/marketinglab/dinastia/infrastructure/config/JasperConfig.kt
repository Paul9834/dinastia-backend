package org.marketinglab.dinastia.infrastructure.config

import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperReport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class JasperConfig {

    @Bean
    fun compiledCarnetReport(): JasperReport {
        val jrxml = ClassPathResource("reports/CarnetMascota.jrxml").inputStream
        return JasperCompileManager.compileReport(jrxml)
    }
}