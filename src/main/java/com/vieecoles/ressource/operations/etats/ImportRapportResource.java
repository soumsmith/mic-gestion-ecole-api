package com.vieecoles.ressource.operations.etats;

import com.vieecoles.dto.ImportMatieresClasseDto;
import com.vieecoles.dto.ImportReportDto;
import com.vieecoles.services.etats.RapportImportService;
import com.vieecoles.steph.dto.BulletinDto;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Path("/import-data-for-report")
public class ImportRapportResource {
  @Inject
  RapportImportService rapportImportService ;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String importData(List<ImportMatieresClasseDto>  bulletinDtoList) throws Exception {
    return  rapportImportService.importAndSaveNote(bulletinDtoList);
    }
}
