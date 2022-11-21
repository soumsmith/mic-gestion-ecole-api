package com.vieecoles.ressource.operations.souscription;

import com.vieecoles.dao.entities.operations.personnel;
import com.vieecoles.dto.sous_attent_personnDto;
import com.vieecoles.dto.souscriptionValidationDto;
import com.vieecoles.dao.entities.operations.sous_attent_personn;
import com.vieecoles.services.personnels.PersonnelService;
import com.vieecoles.services.souscription.SouscPersonnelService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.Desktop;
//FileUpload
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Tag(name = "Souscription", description = "mes Souscriptions")
 @Path("/souscription-personnel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SouscriptionRessource {
    private static String UPLOAD_DIR = "D:\\BrouillonsReactJS\\";
    @Inject
    SouscPersonnelService souscPersonnelService ;
    @Inject
    EntityManager em;

    @Inject
    PersonnelService personnelService ;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<sous_attent_personn> getAllSouscription() {
        return souscPersonnelService.getAllSouscriptionPersonnels() ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("personnel/{idEcole}")
    public List<personnel> personnelParEcole(@PathParam("idEcole") Long idEcole) {
    return souscPersonnelService.findAllPersonneParEcole(idEcole) ;
    }



     @GET
     @Produces(MediaType.APPLICATION_JSON)
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("attente/{status}")
     public List<sous_attent_personn> getAllSouscriptionAvalider(@PathParam("status") String status) {
         return souscPersonnelService.findAllSouscriptionAvaliderDto(status) ;
     }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("email/{email}")
    public sous_attent_personn getSouscriptionByEmail(@PathParam("email") String email) {
        return souscPersonnelService.getSouscripByEmail(email) ;
    }

     @GET
     @Produces(MediaType.MULTIPART_FORM_DATA)
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("ouvrir-fichier/{path}")
     public void   lireFichier(@PathParam("path") String path) throws IOException {
         Desktop desktop = Desktop.getDesktop();
         File file = new File(UPLOAD_DIR+path);
         System.out.println("Path"+UPLOAD_DIR+path);
         try {
             if (file.exists()){
       desktop.open(file);
             } else {
  System.out.println("Not file found");
             }
         } catch (Exception e) {
             e.printStackTrace();
         }

     }


    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public String   CreerSouscription(sous_attent_personnDto  mySouscrip) throws IOException, SQLException {
    return     souscPersonnelService.CreerSousCriperson(mySouscrip) ;

    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public sous_attent_personn UpdateSouscripPersonne(sous_attent_personnDto mySouscrip) {
     return    souscPersonnelService.modifierSousCriptionPersonnel(mySouscrip) ;
    }

    @POST
    @Path("recruter/{idEcole}/{idsouscrip}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String recruterPersonnel(@PathParam("idEcole") Long idEcole ,
                                  @PathParam("idsouscrip") Long idsouscrip ) {
   return      souscPersonnelService.recruterUnAgent(idEcole,idsouscrip) ;
    }



    @DELETE
    @Path("/{identifiantSouscrip}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)

    public void deletePersonnel(@PathParam("identifiantSouscrip") Long identifiantSouscrip) {
        souscPersonnelService.deleteSousCriptionPersonnel(identifiantSouscrip);
    }

    @POST
    @Path("/files")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response handleFileUploadForm(@MultipartForm MultipartFormDataInput input) {

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<String> fileNames = new ArrayList<>();

        List<InputPart> inputParts = uploadForm.get("file");
        System.out.println("inputParts size: " + inputParts.size());
        String fileName = null;
        for (InputPart inputPart : inputParts) {
            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                fileNames.add(fileName);
                System.out.println("File Name: " + fileName);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                File customDir = new File(UPLOAD_DIR);
                fileName = customDir.getAbsolutePath() + File.separator + fileName;
                Files.write(Paths.get(fileName), bytes, StandardOpenOption.CREATE_NEW);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String uploadedFileNames = String.join(", ", fileNames);
        return Response.ok().entity("All files " + uploadedFileNames + " successfully.").build();
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }


     @PUT
     @Produces(MediaType.TEXT_PLAIN)
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/valider-souscription-personnel/")
     @Transactional
     public Response validerSouscription(souscriptionValidationDto sousValid) {

         souscPersonnelService.validerSouscription(sousValid);
         return   Response.ok(String.format("Inscription  %s mis à jour",sousValid.getStatuts())).build();
     }



}
