package com.vieecoles.ressource.operations.etats;

import com.vieecoles.processors.WordTempProcessor;
import com.vieecoles.processors.WordTempRecapResultatProcessor;
import com.vieecoles.processors.WordTempResultaAffProcessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;

@Path("/apachePoi")
public class apachePoiResource {
    @Inject
    WordTempResultaAffProcessor wordService;
    @Inject
    WordTempRecapResultatProcessor wordRecapResultaService;
   @Inject
    WordTempProcessor wordTempProcessor ;

    @GET
    @Path("/download/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]> downloadWord(@PathParam("idEcole") Long idEcole ,
                                 @PathParam("libelleAnnee") String libelleAnnee,
                                 @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception {

        byte[] wordFile;
        FileInputStream fis = new FileInputStream("src/main/resources/etats/apochePoi/DREN YAMOUSSOUKRO/RAPPORT_1ER_TRIMESTRE.docx");

        try {
            // Lire le fichier dans un tableau de bytes pour pouvoir le réutiliser
            byte[] fileContent = fis.readAllBytes();

            // Créer deux nouveaux flux à partir du tableau de bytes
            ByteArrayInputStream fis1 = new ByteArrayInputStream(fileContent);
            ByteArrayInputStream fis2 = new ByteArrayInputStream(fileContent);

            // Générer le fichier Word avec les données dynamiques
            wordFile = wordTempProcessor.generateWordFile(idEcole, libelleAnnee, libelleTrimetre, fis1);

            // Préparer les en-têtes pour la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=Rapport Pouls-Scolaire.docx");

            // Retourner la réponse avec le fichier Word
            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(wordFile);

        } finally {
            fis.close(); // Assurez-vous de fermer le FileInputStream après utilisation
        }

    }
}