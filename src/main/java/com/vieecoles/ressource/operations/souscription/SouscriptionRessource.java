package com.vieecoles.ressource.operations.souscription;

import com.vieecoles.dto.*;
import com.vieecoles.entities.matiere;
import com.vieecoles.entities.profil;
import com.vieecoles.entities.operations.personnel;
import com.vieecoles.entities.operations.sous_attent_personn;
import com.vieecoles.entities.operations.sousc_atten_etabliss;
import com.vieecoles.services.connexion.connexionService;
import com.vieecoles.services.profilService;
import com.vieecoles.services.personnels.PersonnelService;
import com.vieecoles.services.souscription.FileStorageService;
import com.vieecoles.services.souscription.SouscPersonnelService;
import com.vieecoles.steph.entities.AnneeScolaire;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.services.AnneeService;
import com.vieecoles.steph.services.PersonnelMatiereClasseService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
// import jakarta.servlet.http.HttpServletRequest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
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
  @Inject
  connexionService myconnexionService ;
  @Inject
  AnneeService anneeService;
  @Inject
  PersonnelMatiereClasseService persMatClasService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<sous_attent_personn> getAllSouscription() {
        return souscPersonnelService.getAllSouscriptionPersonnels() ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("personnel/{idEcole}")
    public List<personnel> personnelParEcole(@PathParam("idEcole") Long idEcole ) {
    return souscPersonnelService.findAllPersonneParEcole(idEcole) ;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("personnelById/{idpersonnel}")
    public sous_attent_personn personnelByIdPersonnel(@PathParam("idpersonnel") Long idpersonnel) {
        return souscPersonnelService.findPersonnelById(idpersonnel) ;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("classe-par-prof/{idpersonnel}/{idAnnee}")
    public List<ClassesTenuesDto> personnelByIdPersonnel(@PathParam("idpersonnel") Long idpersonnel,
                                                         @PathParam("idAnnee") Long idAnnee) {
        return souscPersonnelService.getClasseProf(idpersonnel,idAnnee) ;
    }


     @GET
     @Produces(MediaType.APPLICATION_JSON)
     @Path("attente/{status}")
     public List<sous_attent_personn> getAllSouscriptionAvalider(@PathParam("status") String status) {
         return souscPersonnelService.findAllSouscriptionAvaliderDto(status) ;
     }

     @GET
     @Produces(MediaType.APPLICATION_JSON)
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
  @Path("creer-professeurs-vie-ecoles/{codeVieEcole}/{idNiveauEnseignement}/{idAnnee}")
  public Response   RecruterVieEcole(@PathParam("codeVieEcole") String codeVieEcole,@PathParam("idNiveauEnseignement") Long idNiveauEnseignement,
                                  @PathParam("idAnnee") Long idAnnee,
                                   List<PersonnelVieEcoleDto> personnelList) throws IOException, SQLException {
    List<String> resultats = new ArrayList<>();
    List<String> erreurs = new ArrayList<>();

    // Vérification de l'école une seule fois
    System.out.println("Entrée Pour créer un professeur **** ");
    Ecole ecole = Ecole.find("identifiantVieEcole =?1 and niveauEnseignement.id=?2",codeVieEcole,idNiveauEnseignement).firstResult();
    System.out.println("ecole **** "+ecole);
    if (ecole == null) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity(Map.of("erreur", "Ecole introuvable dans Pouls-Pro"))
          .build();
    }

    Long idEcole = ecole.getId();

    // Traitement de chaque personnel
    for (int i = 0; i < personnelList.size(); i++) {
      PersonnelVieEcoleDto personnelDto = personnelList.get(i);
      try {
        try {
          String resultat = traiterUnPersonnel(personnelDto, idEcole, i + 1, idAnnee);
          resultats.add(resultat);
        } catch (RuntimeException e) {
          e.printStackTrace();
        }

      } catch (Exception e) {
        String erreur = String.format("Erreur pour le personnel %d (%s %s): %s",
            i + 1,
            personnelDto.getSous_attent_personn_prenom(),
            personnelDto.getSous_attent_personn_nom(),
            e.getMessage());
        erreurs.add(erreur);
      }
    }

    // Construction de la réponse
    Map<String, Object> response = new HashMap<>();
    response.put("total_traite", personnelList.size());
    response.put("succes", resultats.size());
    response.put("echecs", erreurs.size());
    response.put("resultats", resultats);

    if (!erreurs.isEmpty()) {
      response.put("erreurs", erreurs);
    }

    // Retourner le statut approprié
    if (erreurs.isEmpty()) {
      return Response.ok(response).build();
    } else if (resultats.isEmpty()) {
      return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    } else {
      return Response.status(Response.Status.PARTIAL_CONTENT).entity(response).build();
    }

  }

  @POST
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.APPLICATION_JSON)
  //@Transactional
  @Path("affecter-matiere-professeur-vie-ecole")
  public Response   affecterMatiereProfesseur(@QueryParam("codeVieEcole") String codeVieEcole,
                                            @QueryParam("codeClasse") String codeClasse ,
                                            @QueryParam("codeMatiere") String codeMatiere,
                                            @QueryParam("login") String login,
                                              @QueryParam("idNiveauEnseignement") Long  idNiveauEnseignement) throws IOException, SQLException {
    // return     souscPersonnelService.CreerSousCriperson(mySouscrip) ;
    String classeCode=null;
    String matiereCode=null;
    Ecole ecole = Ecole.find("identifiantVieEcole =?1 and niveauEnseignement.id=?2",codeVieEcole,idNiveauEnseignement).firstResult();
    if (ecole == null) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("École introuvable dans Pouls-Pro").build();
    }
    Classe classe = Classe.find("identifiantVieEcole =?1 and ecole.id=?2",codeClasse,ecole.getId()).firstResult();
    matiere mat = matiere.find("code_vie_ecole =?1",codeMatiere).firstResult();

    AnneeScolaire anneeScolaire= new AnneeScolaire() ;
    anneeScolaire=anneeService.findMainAnneeByEcole(ecole) ;
    PersonnelMatiereClasse personnelMatiereClasse= new PersonnelMatiereClasse() ;
    sous_attent_personn  mysouscripPersonn1 = new sous_attent_personn() ;
    mysouscripPersonn1= souscPersonnelService.getSouscripByEmail(login) ;

    if (mysouscripPersonn1 == null) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Utilisateur  introuvable dans Pouls-Pro").build();
    }

    if (classe == null) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Classe introuvable dans Pouls-Pro").build();
    }

    if (mat == null) {
      return Response.status(Response.Status.NOT_FOUND)
          .entity("Matière introuvable dans Pouls-Pro").build();
    }

    try {
       personnelMatiereClasse = souscPersonnelService
          .prepareProfMatiereClasseDto(login, ecole, classe, mat, anneeScolaire);
      System.out.println("Saving and display ...");
      persMatClasService.save(personnelMatiereClasse);
    } catch(Exception e) {
      e.printStackTrace();
      return Response.serverError().entity(e).build();
    }
    return Response.ok().entity(persMatClasService.getByMatiereAndClasseDispo(personnelMatiereClasse).get(0)).build();

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


  private String traiterUnPersonnel(PersonnelVieEcoleDto personnelDto, Long idEcole, int index,Long idAnnee)
      throws IOException, SQLException {

    // Logique de mapping du sexe
    String sexe = switch (personnelDto.getSous_attent_personn_sexe()) {
      case "Mr", "M.", "Monsieur" -> "MASCULIN";
      case "Mme", "Mlle", "Madame", "Mademoiselle" -> "FEMININ";
      default -> "MASCULIN";
    };

    // Logique de mapping du type de compte
    Long typeCompteCode = switch (personnelDto.getTypecompte()) {
      case "Professeur" -> 1L;
      case "Educateur" -> 2L;
      case "Fondateur" -> 3L;
      default -> 1L;
    };

    // Construction de l'objet DTO
    sous_attent_personnDto mySouscrip = new sous_attent_personnDto();
    mySouscrip.setSous_attent_personn_nom(personnelDto.getSous_attent_personn_nom());
    mySouscrip.setSous_attent_personn_prenom(personnelDto.getSous_attent_personn_prenom());
    mySouscrip.setSous_attent_personn_email(personnelDto.getSous_attent_personn_email());
    mySouscrip.setSous_attent_personn_sexe(sexe);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    if(personnelDto.getSous_attent_personn_date_naissance()==null||
        personnelDto.getSous_attent_personn_date_naissance().isEmpty()||personnelDto.getSous_attent_personn_date_naissance().equalsIgnoreCase("null")){
      String date = "01/01/1900";
      LocalDate localDate = LocalDate.parse(date, formatter);
      LocalDate localDateNaiss = localDate;

      mySouscrip.setSous_attent_personn_date_naissance(localDateNaiss);
    } else {
      String date =personnelDto.getSous_attent_personn_date_naissance();
      LocalDate localDate = LocalDate.parse(date, formatter);
      LocalDate localDateNaiss = localDate;

      mySouscrip.setSous_attent_personn_date_naissance(localDateNaiss);
    }

    mySouscrip.setSous_attent_personn_login(personnelDto.getSous_attent_personn_login());
    mySouscrip.setSous_attent_personn_password(personnelDto.getSous_attent_personn_password());
    mySouscrip.setIdentifiantdomaine_formation(5L);
    mySouscrip.setNiveau_etudeIdentifiant(26L);
    mySouscrip.setFonctionidentifiant(typeCompteCode);
    mySouscrip.setType_autorisation_idtype_autorisationid(1L);

    // Création du personnel
    sous_attent_personn messageRetour = souscPersonnelService.creerProfesseurVieEcole(mySouscrip, idEcole);

    if (messageRetour == null) {
      throw new RuntimeException("Échec de la création du personnel");
    }

    // Recrutement de l'agent
    souscPersonnelService.recruterUnAgentVieecole(idEcole, messageRetour,idAnnee);

    // Vérification de l'existence du personnel
    personnel personnel = souscPersonnelService.verifExistancePersonnel(messageRetour.getSous_attent_personnid(), idEcole);

    if (personnel == null) {
      throw new RuntimeException("Personnel créé mais non trouvé lors de la vérification");
    }

    // Affectation du profil utilisateur
    AffecterProfilUtilisateurDto myUtilisaDto = new AffecterProfilUtilisateurDto();
    myUtilisaDto.setEcole_ecoleid(idEcole);
    myUtilisaDto.setPersonnel_personnelid(personnel.getPersonnelid());

    profil p = new profil();
    p.setProfilid(8L);
    List<profil> profils = new ArrayList<>();
    profils.add(p);
    myUtilisaDto.setListProfil(profils);

    LocalDate dateActuelle = LocalDate.now();
    LocalDate dateDans9Mois = dateActuelle.plusMonths(9);
    myUtilisaDto.setUtilisateur_has_person_date_fin(dateDans9Mois);

    String responseMess = myconnexionService.affecterProfilUtilisateur(myUtilisaDto);

    return String.format("Personnel %d (%s %s): %s",
        index,
        personnelDto.getSous_attent_personn_prenom(),
        personnelDto.getSous_attent_personn_nom(),
        responseMess != null ? responseMess : "Créé avec succès");
  }


}
