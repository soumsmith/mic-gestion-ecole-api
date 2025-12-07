package com.vieecoles.steph.ressources;

import com.vieecoles.steph.entities.Branche;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.services.BrancheService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/branche")
public class BrancheResource {

	@Inject
	BrancheService brancheService;

	@GET
	@Path("/list")
	@Operation(description = "Obtenir la liste des branches", summary = "")
	@Tag(name = "Branche")
	public Response list() {
		return Response.ok().entity(brancheService.getList()).build();
	}

	@GET
	@Path("/get-by-niveau-enseignement")
	@Operation(description = "Obtenir la liste des branches", summary = "")
	@Tag(name = "Branche")
	public Response getByNiveauEnseignement(@QueryParam("ecole") Long ecoleId) {
		return Response.ok().entity(brancheService.findByNiveauEnseignementViaEcole(ecoleId)).build();
	}
	@GET
	@Path("/get-by-niveau-enseignement-vie-ecole")
	@Operation(description = "Obtenir la liste des branches avec l'identifiant vie-ecole", summary = "")
	@Tag(name = "Branche")
	public Response getByNiveauEnseignementvieecole(@QueryParam("ecole") String codeEcole) {
		return Response.ok().entity(brancheService.findByNiveauEnseignementViaVie_Ecole(codeEcole)).build();
	}

	@GET
	@Path("/get-by-niveau-enseignement-only")
	@Operation(description = "Obtenir la liste des branches", summary = "")
	@Tag(name = "Branche")
	public Response getByNiveauEnseignementOnly(@QueryParam("id") Long id) {
		return Response.ok().entity(brancheService.findByNiveauEnseignement(id)).build();
	}

	@GET
	@Path("/get-by-niveau-enseignement-projection")
	@Operation(description = "Obtenir la liste des branches", summary = "")
	@Tag(name = "Branche")
	public Response getByNiveauEnseignementProjection(@QueryParam("niveau") Long niveau) {
		return Response.ok().entity(brancheService.findByNiveauEnseignementProjection(niveau)).build();
	}

	@GET
	@Path("/get-by-programme-niveau")
	@Operation(description = "Obtenir la liste (dto) des branches par programme et niveau d'enseignement", summary = "")
	@Tag(name = "Branche")
	public Response getByNiveauEnseignementOnly(@QueryParam("programme") String programme,@QueryParam("niveau") String niveau) {
		return Response.ok().entity(brancheService.findByProgrammeAndNiveauEnseignement(programme, niveau)).build();
	} 

	@GET
	@Path("/get-by-classe-vie-ecole")
	@Tag(name = "Branche")
	public Response listByClassevieEcole(@QueryParam("classeId") String classeCode, @QueryParam("codeVieEcole") String codeVieEcole,
	@QueryParam("idNiveauEnseignement") Long idNiveauEnseignement) {
     Ecole ecole = Ecole.find("identifiantVieEcole =?1 and niveauEnseignement.id=?2",codeVieEcole,idNiveauEnseignement).firstResult();
	
		if (ecole == null) {
			return Response.status(Response.Status.NOT_FOUND)
				.entity("École introuvable dans Pouls-Pro").build();
			}

			Classe classe = Classe.find("identifiantVieEcole =?1 and ecole.id=?2",classeCode,ecole.getId()).firstResult();
			if(classe == null) {
				return Response.status(Response.Status.NOT_FOUND).build();
			} else {
				Long brancheId = classe.getBranche().getId();
				Branche branche =new Branche();
				branche = Branche.findById(brancheId);
				return Response.ok().entity(branche).build();
			}

	}

}
