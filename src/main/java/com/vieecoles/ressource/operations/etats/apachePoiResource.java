package com.vieecoles.ressource.operations.etats;

import com.vieecoles.services.etats.WordService;
import com.vieecoles.services.etats.WordTemplateProcessor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/apachePoi")
public class apachePoiResource {
    @Inject
    WordTemplateProcessor wordService;
   // @Inject
   // WordService wordService;

    @GET
    @Path("/download/{idEcole}/{libelleAnnee}/{libelleTrimetre}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]> downloadWord(@PathParam("idEcole") Long idEcole ,
                                 @PathParam("libelleAnnee") String libelleAnnee,
                                 @PathParam("libelleTrimetre") String libelleTrimetre) throws Exception {

            // Générer le fichier Word avec les données dynamiques
            byte[] wordFile = wordService.generateWordFile(idEcole,libelleAnnee,libelleTrimetre);
            HttpHeaders headers= new HttpHeaders();
            // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport Pouls-Scolaire.docx");
            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(wordFile);

    }
}
