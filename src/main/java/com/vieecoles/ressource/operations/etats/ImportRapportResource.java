package com.vieecoles.ressource.operations.etats;

import com.vieecoles.dto.ImportMatieresClasseDto;
import com.vieecoles.dto.ImportNotesMatieresEcole;
import com.vieecoles.dto.ImportReportDto;
import com.vieecoles.services.etats.RapportImportService;
import com.vieecoles.steph.dto.BulletinDto;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Path("/import-list-evaluation-classe")
public class ImportRapportResource {
  @Inject
  RapportImportService rapportImportService ;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String importData(List<ImportNotesMatieresEcole> importNotes) throws Exception {
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd yyyy HH:mm:ss", Locale.ENGLISH);
      String currentDate = now.format(formatter);
      importNotes.forEach(dto -> {
        dto.setDate(currentDate);
      });
      List<ImportMatieresClasseDto>  evaluaListe= new ArrayList<ImportMatieresClasseDto>();
    evaluaListe =rapportImportService.processDto(importNotes);


    return  rapportImportService.importAndSaveNote(evaluaListe);
    }
}
