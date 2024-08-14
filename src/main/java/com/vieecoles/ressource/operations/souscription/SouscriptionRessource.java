package com.vieecoles.ressource.operations.souscription;

import com.vieecoles.dto.*;
import com.vieecoles.entities.profil;
import com.vieecoles.entities.operations.personnel;
import com.vieecoles.entities.operations.sous_attent_personn;
import com.vieecoles.entities.operations.sousc_atten_etabliss;
import com.vieecoles.services.profilService;
import com.vieecoles.services.personnels.PersonnelService;
import com.vieecoles.services.souscription.FileStorageService;
import com.vieecoles.services.souscription.SouscPersonnelService;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
// import javax.servlet.http.HttpServletRequest;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
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
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.http.MediaType.
@Tag(name = "Souscription", description = "mes Souscriptions")
 @Path("/souscription-personnel")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SouscriptionRessource {
    private static String UPLOAD_DIR = "/data/";
    //private static String UPLOAD_DIR = "D:/BrouillonsReactJS/";
    @Inject
    SouscPersonnelService souscPersonnelService ;
    @Inject
    EntityManager em;
    @Inject
    FileStorageService fileStorageService ;
    @Inject
    profilService  profilservice ;

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
    public List<personnel> personnelParEcole(@PathParam("idEcole") Long idEcole ) {
    return souscPersonnelService.findAllPersonneParEcole(idEcole) ;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("personnelById/{idpersonnel}")
    public sous_attent_personn personnelByIdPersonnel(@PathParam("idpersonnel") Long idpersonnel) {
        return souscPersonnelService.findPersonnelById(idpersonnel) ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("classe-par-prof/{idpersonnel}/{idAnnee}")
    public List<ClassesTenuesDto> personnelByIdPersonnel(@PathParam("idpersonnel") Long idpersonnel,
                                                         @PathParam("idAnnee") Long idAnnee) {
        return souscPersonnelService.getClasseProf(idpersonnel,idAnnee) ;
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
     @Path("attente-fondateur/{status}/{fonction}")
     public List<sous_attent_personn> getAllSouscriptionAvaliderFondateur(@PathParam("status") String status,@PathParam("fonction") String fonction) {
         return souscPersonnelService.findAllSouscriptionAvaliderDtoFondateur(status, fonction);
     }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("email/{email}")
    public sous_attent_personn getSouscriptionByEmail(@PathParam("email") String email) {
        return souscPersonnelService.getSouscripByEmail(email) ;
    }

     @GET
     @Produces({"application/pdf", "image/jpeg"})
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("ouvrir-fichier/{fileName}")
     public Response lireFichier(@PathParam("fileName") String fileName) throws IOException {
        String exten = null ;

         System.out.println("Path"+UPLOAD_DIR+fileName);

         System.out.println("File requested is : " + fileName);

         //Put some validations here such as invalid file name or missing file name
         if(fileName == null || fileName.isEmpty())
         {
             Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
             return response.build();
         }

         //Prepare a file object with file to return
         File file = new File(UPLOAD_DIR+fileName);

       //  exten= FilenameUtils.getExtension(UPLOAD_DIR+fileName) ;


         Response.ResponseBuilder response = Response.ok((Object) file);
         response.header("Content-Disposition", "attachment; filename="+UPLOAD_DIR+fileName+"");
         return response.build();

     }

    @GET
    @Path("/ouvrir-image/{fileName}")
    @Produces("image/jpeg")
    public Response getFileInJPEGFormat(@PathParam("fileName") String fileName)
    {
        System.out.println("File requested is : " + fileName);

        //Put some validations here such as invalid file name or missing file name
        if(fileName == null || fileName.isEmpty())
        {
            Response.ResponseBuilder response = Response.status(Response.Status.BAD_REQUEST);
            return response.build();
        }
        //Prepare a file object with file to return
        File file = new File(UPLOAD_DIR+fileName);

        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment; filename="+UPLOAD_DIR+fileName+"");
        return response.build();
    }




     @GET
    // @Produces(MediaType.TEXT_PLAIN)
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("nombre-ecole-valider-par-fondateur/{idSouscrip}")
     public String nombreEcoleValiFonda(@PathParam("idSouscrip") Long idSouscrip) throws IOException {
        List<Long> listEcole ;
        String messageRetour = null ;
      listEcole = souscPersonnelService.getListEcoleBySouscrip(idSouscrip) ;
       if(listEcole.size()>0 ) {
        messageRetour = "1";
       }else {
        messageRetour = "2";
       }

      return messageRetour ;
     }


    @GET
    // @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("nombre-nouvelle-ecoles-valider/{idSouscrip}")
    public String nombreNewEcoleValiFonda(@PathParam("idSouscrip") Long idSouscrip) throws IOException {
        List<Long> listEcole ;
        String messageRetour = null ;
        listEcole = souscPersonnelService.getListNewEcoleBySouscrip(idSouscrip) ;
        if(listEcole.size()>0 ) {
            messageRetour = "1";
        }else {
            messageRetour = "2";
        }

        return messageRetour ;
    }





     @GET
      @Produces(MediaType.APPLICATION_JSON)
      @Consumes(MediaType.APPLICATION_JSON)
      @Path("ecole-valider-par-fondateur/{idSouscrip}")
      public List<Long> EcoleValiFonda(@PathParam("idSouscrip") Long idSouscrip) throws IOException {
         List<Long> listEcole ;
       listEcole = souscPersonnelService.getListEcoleBySouscrip2(idSouscrip) ;
       return listEcole ;
      }



    @GET
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("ouvrir-fichierPDF_url/{path}")
    public String lireFichier2(@PathParam("path") String path) throws IOException {
        Desktop desktop = Desktop.getDesktop();
        File file = new File(UPLOAD_DIR+path);
        System.out.println("Path"+UPLOAD_DIR+path);
        try {
            if (file.exists()){
                return UPLOAD_DIR+path ;
            } else {
                System.out.println("Not file found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return path;
    }
/*     @GET
   // @Produces(MediaType.MULTIPART_FORM_DATA)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("ouvrir-fichierDao/{path}")
    public ResponseEntity<Resource> downloadFile2( @PathParam("path") String path, @Context HttpServletRequest request) {
        // Load file as Resource
 //  fileStorageService.setDirName(dirName);
        Resource resource = fileStorageService.loadFileAsResource(path);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
           // logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.valueOf(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
} */





    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    //@Transactional
    public String   CreerSouscription(sous_attent_personnDto  mySouscrip) throws IOException, SQLException {
   // return     souscPersonnelService.CreerSousCriperson(mySouscrip) ;
       return     souscPersonnelService.CreerSouscripCompteUtilisateur(mySouscrip) ;
    }






    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    //@Transactional
    @Path("compte-candidat")
    public String   CreerCompteCandidat(CreerCompteUtilsateurDto mySouscrip) throws IOException, SQLException {
        // return     souscPersonnelService.CreerSousCriperson(mySouscrip) ;
        return     souscPersonnelService.creerCompteUtilisateur(mySouscrip);
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
     //@Transactional
     public Response validerSouscription(souscriptionValidationDto sousValid) {

         souscPersonnelService.validerSouscription(sousValid);
         return   Response.ok(String.format("Inscription  %s mis à jour",sousValid.getStatuts())).build();
     }

     @PUT
     @Produces(MediaType.TEXT_PLAIN)
     @Consumes(MediaType.APPLICATION_JSON)
     @Path("/valider-souscription-fondateur/")
     //@Transactional
     public String validerSouscriptionFondateur(souscriptionValidationFondatDto sousValid) {
       Long profilId =null ;
       profil myProfil= new profil() ;
       myProfil = profilservice.getIdProfilAdmin("Fondateur");
      profilId= myProfil.getProfilid() ;
      sousValid.setProfilId(profilId);
       return  souscPersonnelService.valideCreerCompteFondateur(sousValid);
       /*   return   Response.ok(String.format("Inscription  %s mis à jour",sousValid.getStatuts())).build(); */
     }





}
