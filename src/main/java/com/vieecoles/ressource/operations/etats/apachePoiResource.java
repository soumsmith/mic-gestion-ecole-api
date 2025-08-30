package com.vieecoles.ressource.operations.etats;

import com.vieecoles.dto.StatistiquesNiveauSexeDto;
import com.vieecoles.processors.bouake.WordTempProcessorBouake;
import com.vieecoles.processors.dren3.WordTempProcessorDren3;
import com.vieecoles.processors.dren4.Services.StatistiquesNiveauSexeService;
import com.vieecoles.processors.dren4.WordTempDren4Processor;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

@Path("/apachePoi")
public class apachePoiResource {

   @Inject
   WordTempProcessorDren3 wordTempProcessor ;
  @Inject
  com.vieecoles.processors.yamoussoukro.WordTempProcessor wordTempYakroProcessor ;
  @Inject
  WordTempProcessorBouake wordTempBouakeProcessor ;
  @Inject
  WordTempDren4Processor wordTempDren4Processor;
  @Inject
  StatistiquesNiveauSexeService statistiquesNiveauSexeService ;
    @GET
    @Path("/download/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]> downloadWord(@PathParam("idEcole") Long idEcole ,
                                 @PathParam("libelleAnnee") String libelleAnnee,
                                 @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception {

        byte[] wordFile;
      // FileInputStream fis = new FileInputStream("src/main/resources/etats/apochePoi/Bouake/RAPPORT_TRIMESTRIEL.docx");

    //FileInputStream fis = new FileInputStream("src/main/resources/etats/apochePoi/DREN YAMOUSSOUKRO/RAPPORT_TRIMESTRIEL_ANNUEL.docx");

     FileInputStream fis = new FileInputStream("src/main/resources/etats/apochePoi/Dren4/RAPPORT_TRIMESTRIEL_ANNUEL.docx");

      try {
            // Lire le fichier dans un tableau de bytes pour pouvoir le réutiliser
            byte[] fileContent = fis.readAllBytes();
            ByteArrayInputStream fis1 = new ByteArrayInputStream(fileContent);
        //wordFile = wordTempProcessor.generateWordFile(idEcole, libelleAnnee, libelleTrimetre, fis1);
          //wordFile = wordTempYakroProcessor.generateWordFile(idEcole, libelleAnnee, libelleTrimetre, fis1);
          wordFile= wordTempDren4Processor.generateWordFile(idEcole, libelleAnnee, libelleTrimetre, fis1);
       // wordFile = wordTempBouakeProcessor.generateWordFile(idEcole, libelleAnnee, libelleTrimetre, fis1);

            // Préparer les en-têtes pour la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=Rapport Pouls-Scolaire.docx");

            // Retourner la réponse avec le fichier Word
            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(wordFile);

        } finally {
            fis.close(); // Assurez-vous de fermer le FileInputStream après utilisation
        }

    }
  @GET
  @Path("/statDFA/{idEcole}/{libelleAnnee}/{libelleTrimetre}/{ordre}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<StatistiquesNiveauSexeDto> getDfa(@PathParam("idEcole") Long idEcole ,
                                                @PathParam("libelleAnnee") String libelleAnnee,
                                                @PathParam("libelleTrimetre") String libelleTrimetre ,
                                                @PathParam("ordre") Integer ordre) {
      System.out.println("Entree>>> je suis là");
 return    statistiquesNiveauSexeService.getStatistiqueDfa(idEcole,libelleAnnee,libelleTrimetre);
  }
}
