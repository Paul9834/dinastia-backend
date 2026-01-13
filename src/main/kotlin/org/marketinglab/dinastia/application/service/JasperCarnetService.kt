package org.marketinglab.dinastia.application.service

import net.sf.jasperreports.engine.*
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import org.marketinglab.dinastia.domain.model.CarnetMascotaReport
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class JasperCarnetService {

    fun generarPdf(report: CarnetMascotaReport): ByteArray {
        val jrxml = ClassPathResource("reports/carnet_mascota.jrxml").inputStream
        val jasperReport = JasperCompileManager.compileReport(jrxml)

        val params = mutableMapOf<String, Any>(
            "nombre" to report.nombre,
            "especie" to report.especie,
            "raza" to report.raza,
            "sexo" to report.sexo,
            "microchip" to (report.microchip ?: ""),
            "nacimiento" to report.nacimiento,
            "propietario" to report.propietario,
            "qrText" to report.qrText,
            "logoPath" to ClassPathResource("reports/images/dinastia_logo.png").file.absolutePath,
            "vacunasDs" to JRBeanCollectionDataSource(report.vacunas),
            "desparasDs" to JRBeanCollectionDataSource(report.desparasitaciones)
        )

        val jasperPrint = JasperFillManager.fillReport(jasperReport, params, JREmptyDataSource())

        val out = ByteArrayOutputStream()
        JasperExportManager.exportReportToPdfStream(jasperPrint, out)
        return out.toByteArray()
    }
}