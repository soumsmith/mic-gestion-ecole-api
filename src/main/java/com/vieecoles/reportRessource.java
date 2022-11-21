package com.vieecoles.ressource.reports;

import com.vieecoles.dao.ReportEffectifParSexeStatut;
import com.vieecoles.entities.cycle;
import com.vieecoles.services.cycleService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/reports")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class reportRessource {
    @Inject
    cycleService matService ;
    @GET
    @Path("/reports/effectifParStatutsEtSexe")
    public String  reportParStatutEtSexe() throws FileNotFoundException, JRException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Arrays.asList(

			new ReportEffectifParSexeStatut("6ième1","6ième", 12,45,63,54,52,
                    16,45,52),
                new ReportEffectifParSexeStatut("6ième2","6ième", 12,45,63,54,52,
                        16,45,52),
                new ReportEffectifParSexeStatut("6ième3","6ième", 12,45,63,54,52,
                        16,45,52),
                new ReportEffectifParSexeStatut("5ième1","5ième", 12,45,63,54,52,
                        16,45,52),
                new ReportEffectifParSexeStatut("5ième1","5ième", 12,45,63,54,52,
                        16,45,52),
                new ReportEffectifParSexeStatut("5ième2","5ième", 12,45,63,54,52,
                        16,45,52),
                new ReportEffectifParSexeStatut("5ième3","5ième", 12,45,63,54,52,
                        16,45,52)


	), false);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("ecoleName", "SoumEcole");

		JasperReport compileReport = JasperCompileManager
			.compileReport(new FileInputStream("src/main/resources/BilanDesEffectifs.jrxml"));

	JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);

	JasperExportManager.exportReportToPdfFile(jasperPrint, System.currentTimeMillis() + ".pdf");
return  "generated ";
    }


}
