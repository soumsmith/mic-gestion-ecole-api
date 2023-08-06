package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.dto.ProfEducDto;
import com.vieecoles.steph.entities.PersonnelMatiereClasse;
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
		
		PersonnelMatiereClasse personnel = persMatClasService.getPersonnelByClasseAndAnneeAndFonction(classe, annee,fonction );
    	return personnel;
    	
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
    @Path("/get-professeur-by-matiere")
    @Operation(description = "Obtenir le professeur enseignant une matière dans une classe", summary = "")
	@Tag(name = "PersonnelMatiereClasse")
	public PersonnelMatiereClasse findProfesseurByMatiereAndClasse( @QueryParam("annee") Long annee,@QueryParam("matiere") Long matiere, @QueryParam("classe") Long classe) {
		
		PersonnelMatiereClasse personnel = persMatClasService.findProfesseurByMatiereAndClasse(annee, classe, matiere);
    	return personnel;
    	
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
    		 return Response.serverError().entity(new PersonnelMatiereClasse()).build();
    	 }
    	return Response.ok().entity(persMatClasService.findById(personnel.getId())).build();
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
    			if(p.getPersonnel().getFonction().getCode().equals("01")) {
    				peDto.setClasse(p.getClasse());
    				peDto.setProf(p.getPersonnel());
    			}else {
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
