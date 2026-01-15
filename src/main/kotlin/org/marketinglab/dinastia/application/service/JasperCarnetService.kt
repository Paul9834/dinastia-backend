package org.marketinglab.dinastia.application.service

import net.sf.jasperreports.engine.*
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import org.marketinglab.dinastia.application.dto.DesparasitacionRow
import org.marketinglab.dinastia.application.dto.VacunaRow
import org.marketinglab.dinastia.domain.model.CarnetMascotaReport
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream


@Service
class JasperCarnetService(
    private val compiledCarnetReport: JasperReport
) {

    fun generarCarnetPdf(
        params: Map<String, Any?>,
        vacunas: List<VacunaRow>,
        desparasitaciones: List<DesparasitacionRow>
    ): ByteArray {
        val finalParams = HashMap<String, Any?>()
        finalParams.putAll(params)
        finalParams["dsVacunas"] = JRBeanCollectionDataSource(vacunas)
        finalParams["dsDesparasitacion"] = JRBeanCollectionDataSource(desparasitaciones)

        val jasperPrint = JasperFillManager.fillReport(
            compiledCarnetReport,
            finalParams,
            JREmptyDataSource(1)
        )

        val out = ByteArrayOutputStream()
        JasperExportManager.exportReportToPdfStream(jasperPrint, out)
        return out.toByteArray()
    }
}