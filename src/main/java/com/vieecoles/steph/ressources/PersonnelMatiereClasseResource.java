package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.dto.PersonnelMatiereClasseDto;
import com.vieecoles.steph.dto.ProfEducDto;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
import com.vieecoles.steph.projections.GenericPersonelProjectionLongIdFonctionEcole;
import com.vieecoles.steph.services.PersonnelMatiereClasseService;
/**
 * 
 * MODIFIER LE PARAMETRE annee par sa variable
 * @author stephane
 *
 */
@Path("personnel-matiere-classe")
public class PersonnelMatiereClasseResource {
	@Inject
	PersonnelMatiereClasseService persMatClasService;
	
	@GET
    @Path("/list")
    @Tag(name = "PersonnelMatiereClasse")
    public Response list() {
        return Response.ok().entity(persMatClasService.getList()).build();
    }
    
    @GET
    @Path("/{id}")
    @Operation(description = "Obtenir la classe par son id", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public PersonnelMatiereClasse get(@PathParam("id") long id) {
    	PersonnelMatiereClasse classe = persMatClasService.findById(id);
    	if(classe == null) {
    		System.out.println("classe non trouvee avec id = "+id);
    		return new PersonnelMatiereClasse();
    	} else {
    		return classe;
    	}
    }
    
    @GET
    @Path("/get-by-branche")
    @Operation(description = "Obtenir les classes de meme branche", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public List<PersonnelMatiereClasse> getByBranche(@QueryParam("branche") long brancheId) {
    	List<PersonnelMatiereClasse> personnels = persMatClasService.findByBranche(brancheId, 1);
    	return personnels;
    }
    
    
    @GET
    @Path("/get-by-matiere")
    @Operation(description = "Obtenir les classes de meme matiere", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public List<PersonnelMatiereClasse> getByMatiere(@QueryParam("matiere") long matiereId, @QueryParam("annee") long anneeId, @QueryParam("ecole") long ecoleId) {
    	List<PersonnelMatiereClasse> personnels = persMatClasService.findByMatiere(matiereId, anneeId, ecoleId);
    	return personnels;
    }
    
    @GET
    @Path("/get-by-prof")
    @Operation(description = "Obtenir les classes d un prof", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public List<PersonnelMatiereClasse> getByProf(@QueryParam("prof") long profId, @QueryParam("annee") long anneeId, @QueryParam("ecole") long ecoleId) {
    	List<PersonnelMatiereClasse> personnels = persMatClasService.findByProfesseur(profId, anneeId, ecoleId);
    	return personnels;
    }
    
    @GET
    @Path("/get-by-prof-classe")
    @Operation(description = "Obtenir les matieres enseignées d un prof dans une classe", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public List<PersonnelMatiereClasse> findByProfesseurAndClasse(@QueryParam("prof") long profId, @QueryParam("classe") long classe, @QueryParam("annee") long anneeId) {
    	List<PersonnelMatiereClasse> personnels = persMatClasService.findByProfesseurAndClasse(profId,classe, anneeId);
    	return personnels;
    }
    
    @GET
    @Path("/get-by-prof-classe-where-coef-is")
    @Operation(description = "Obtenir les matieres enseignées d un prof dans une classe dont les coefficient ont été définis", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public List<PersonnelMatiereClasse> findByProfesseurAndClasseWhereCoeficientDefine(@QueryParam("prof") long profId, @QueryParam("classe") long classe, @QueryParam("annee") long anneeId) {
    	List<PersonnelMatiereClasse> personnels = persMatClasService.findByProfesseurAndClasseWhereCoefDefine(profId,classe, anneeId);
    	return personnels;
    }
    
    @GET
    @Path("/get-dto-by-prof-classe-where-coef-is")
    @Operation(description = "Obtenir les dto des matières enseignées par un prof dans une classe dont les coefficient ont été définis", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
    public Response findDtoByProfesseurAndClasseWhereCoeficientDefine(@QueryParam("prof") long profId, @QueryParam("classe") long classe, @QueryParam("annee") long anneeId) {
    	List<PersonnelMatiereClasse> personnels = persMatClasService.findByProfesseurAndClasseWhereCoefDefine(profId,classe, anneeId);
    	List<PersonnelMatiereClasseDto> personnelDtos = persMatClasService.findDtoByProfesseurAndClasseWhereCoefDefine(personnels);
    	return Response.ok(personnelDtos).build() ;
    }
    
    @GET
    @Path("/get-by-fonction")
    @Operation(description = "Obtenir les classes assignées à un personnel(prof princ. ou educateur)", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
	public List<PersonnelMatiereClasse> findListByFonction( @QueryParam("annee") int annee,@QueryParam("ecole") Long ecole, @QueryParam("fonctionId") int fonctionId) {
		
		List<PersonnelMatiereClasse> personnels = persMatClasService.findListByFonction(annee,ecole, fonctionId);
    	return personnels;
    	
	}
    
    
    @GET
    @Path("/get-professeur-by-classe")
    @Operation(description = "Obtenir les professeurs enseignant dans une classe", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
	public List<PersonnelMatiereClasse> findListByClasse( @QueryParam("annee") int annee, @QueryParam("classe") long classe) {
		
		List<PersonnelMatiereClasse> personnels = persMatClasService.findListByClasse(annee, classe);
    	return personnels;
    	
	}
    
    
    @GET
    @Path("/get-pp-or-educateur")
    @Operation(description = "Obtenir le personnel (prof princ. ou educateur) assignées à une classe", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
	public PersonnelMatiereClasse getPpOrEduc( @QueryParam("annee") Long annee,@QueryParam("fonction") int fonction, @QueryParam("classe") long classe) {
    	return persMatClasService.getPersonnelByClasseAndAnneeAndFonction(classe, annee,fonction );
	}
    
    @GET
    @Path("/count-prof-by-matiere")
    @Operation(description = "Obtenir le nombre de professeurs enseignant une matiere dans une ecole", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
	public long countProfByMatiere( @QueryParam("ecole") Long ecole,@QueryParam("matiere") long matiere, @QueryParam("annee") long annee) {
    	return persMatClasService.countProfByMatiereAndEcole(ecole, matiere, annee);
	}
    
    @GET
    @Path("/get-personnel-by-classe")
    @Operation(description = "Obtenir les personnels (prof princ. ou educateur) assignées à une classe", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
	public List<PersonnelMatiereClasse> findListByPersonnel( @QueryParam("annee") Long annee,@QueryParam("ecole") int ecole, @QueryParam("classe") long classe) {
		
		List<PersonnelMatiereClasse> personnels = persMatClasService.findListByPersonnel(annee,ecole, classe);
    	return personnels;
    	
	}
    
    @GET
    @Path("/get-pp-and-educ-dto-by-classe")
    @Tag(name = "PersonnelMatiereClasse")
	public Response findEducPPDto( @QueryParam("annee") Long annee,@QueryParam("ecole") int ecole, @QueryParam("classe") long classe) {
		
		ProfEducDto profEduc = persMatClasService.findListByPersonnel_v2(annee,ecole, classe);
    	return  Response.ok().entity(profEduc).build();
    	
	}
    
    @GET
    @Path("/get-professeur-principal")
    @Operation(description = "Obtenir le prof princ assigné à une classe", summary = "")
    @Tag(name = "PersonnelMatiereClasse")
	public Response getPP( @QueryParam("annee") Long annee, @QueryParam("classe") long classe) {
		PersonnelMatiereClasse profPrinc = persMatClasService.findProfPrinc(annee, classe);
    	return  Response.ok().entity(profPrinc).build();
	}
    
    @GET
    @Path("/get-professeur-by-matiere")
    @Operation(description = "Obtenir le professeur enseignant une matière dans une classe", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
	public PersonnelMatiereClasse findProfesseurByMatiereAndClasse( @QueryParam("annee") Long annee,@QueryParam("matiere") Long matiere, @QueryParam("classe") Long classe) {
		
		PersonnelMatiereClasse personnel = persMatClasService.findProfesseurByMatiereAndClasse(annee, classe, matiere);
    	return personnel;
    	
	}
    
    @GET
    @Path("/get-projection-enseignant")
    @Operation(description = "Obtenir le professeur enseignant une matière dans une classe", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
	public Response findProjectionProfesseurByMatiereAndClasse( @QueryParam("annee") Long annee,@QueryParam("matiere") Long matiere, @QueryParam("classe") Long classe) {
		
    	GenericPersonelProjectionLongIdFonctionEcole personnel = persMatClasService.findPersonnelProjectionIdFonctionEcoleByMatiereAndClasse(matiere, annee, classe);
    	return Response.ok(personnel).build();
    	
	}
    
    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "PersonnelMatiereClasse")
    public Response create(PersonnelMatiereClasse personnel) {
    	try {
    		System.out.println("Saving ...");
    		persMatClasService.save(personnel);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity("Erreur de creation").build();
    	 }
    	return Response.ok("Enregistrement reussi").build();
    }
    
    @PUT
    @Path("/delete-by-status")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @Tag(name = "PersonnelMatiereClasse")
    public Response deleteByStatus(PersonnelMatiereClasse pmc) {
    	try {
    		System.out.println("Delete by status ...");
    		persMatClasService.deleteByStatus(pmc);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity(e.getMessage()).build();
    	 }
    	return Response.ok("Suppression effectuée").build();
    }
    
    @POST
    @Path("/saveAndDisplay")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "PersonnelMatiereClasse")
    public Response createAndDisplay(PersonnelMatiereClasse personnel) {
    	try {
    		System.out.println("Saving and display ...");
    		persMatClasService.save(personnel);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity(e).build();
    	 }
    	return Response.ok().entity(persMatClasService.getByMatiereAndClasseDispo(personnel).get(0)).build();
    }
    
    @POST
    @Path("/saveAndDisplayList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "PersonnelMatiereClasse")
    public Response createAndDisplayList(List<PersonnelMatiereClasse> personnels) {
    	List<PersonnelMatiereClasse> pmcList = new ArrayList<PersonnelMatiereClasse>();
    	ProfEducDto peDto = new ProfEducDto();
    			
    	try {
    		System.out.println("Saving and display list ...");
    		for(PersonnelMatiereClasse pers : personnels) {
    			System.out.println("----> output");
    			System.out.println(pers.getPersonnel().getId());
    			persMatClasService.saveForEducAndProf(pers);
    		}
    		for(PersonnelMatiereClasse pers : personnels) {
    			pmcList = persMatClasService.getListProfOrEducByAnneeAndClasse(pers.getAnnee().getId(), pers.getClasse().getId());
    			break;
    		}
    		
    		for(PersonnelMatiereClasse p : pmcList) {
    			if(p.getTypeFonction()!= null && p.getTypeFonction().equals(Constants.PROFESSEUR_PRINCIPAL)) {
    				peDto.setClasse(p.getClasse());
    				peDto.setProf(p.getPersonnel());
    			}else if(p.getTypeFonction()!= null && p.getTypeFonction().equals(Constants.EDUCATEUR_CLASSE))  {
    				peDto.setClasse(p.getClasse());
    				peDto.setEducateur(p.getPersonnel());
    			}
    		}
    		
//    		System.out.println(personnels);
    	} catch(Exception e) {
    		 e.printStackTrace();
    		 return Response.serverError().entity(new PersonnelMatiereClasse()).build();
    	 }
    	return Response.ok().entity(peDto).build();
    }
    
    @GET
    @Path("/get-list-prof-princ-or-educ-by-classe-and-annee")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "PersonnelMatiereClasse")
    public List<PersonnelMatiereClasse> getListProfPrincOrEduc(@QueryParam("annee") Long annee, @QueryParam("classe") Long classe){
    	return persMatClasService.getListProfOrEducByAnneeAndClasse(annee, classe);
    }
    
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "PersonnelMatiereClasse")
    public PersonnelMatiereClasse update(PersonnelMatiereClasse personnel) {
    	
    	PersonnelMatiereClasse c = persMatClasService.update(personnel);
		if(c==null) {
			throw new NotFoundException();
		}
    	return c;
    }
    
    @POST
    @Path("/update-display")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "PersonnelMatiereClasse")
    public Response updateAndDisplay(PersonnelMatiereClasse personnel) {
    	
    	PersonnelMatiereClasse c = persMatClasService.updateAndDisplay(personnel);
		if(c==null) {
			throw new NotFoundException();
		}
//		System.out.println(new Gson().toJson(c));
    	return Response.ok().entity(c).build();
    }
    
}
