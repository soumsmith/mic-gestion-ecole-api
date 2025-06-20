package com.vieecoles.steph.ressources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.dto.BulletinDto;
import com.vieecoles.steph.services.BulletinService;

@Path("/bulletin")
public class BulletinResource {

	@Inject
	BulletinService bulletinService;

	@GET
	@Path("/handle-save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(description = "Calcul des moyennes des élève d'une classe", summary = "")
	@Tag(name = "Bulletins")
	public Response handleSave(@QueryParam("classe") String classe ,@QueryParam("annee") String  annee, @QueryParam("periode") String periode) {
		int nbreEleve = 0;
//		List<Bulletin> list = new ArrayList<>();
		try {
			long startTime = System.nanoTime();
			nbreEleve = bulletinService.handleSave(classe, annee, periode);
			long endTime = System.nanoTime();
			long durationInSeconds = (endTime - startTime) / 1000000000;
			 System.out.println("Temps d'exécution Total: " + durationInSeconds + " secondes");
//			Classe cl = Classe.findById(Long.parseLong(classe));
//			System.out.println(cl.getEcole().getId());
//			try {
//			list = Bulletin.find("ecoleId =?1", cl.getEcole().getId()).list();
//			}catch(RuntimeException r) {
//				list = null;
//				r.printStackTrace();
//			}
//			System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(nbreEleve == 0 ? "Aucun bulletin sauvegardé" : String.format("%s Bulletin(s) sauvegardé(s)",nbreEleve)).build();
	}

	@GET
	@Path("/get-bulletins-eleve-annee")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "Obtenir la liste des bulletins au cours d'une année scolaire", summary = "")
	@Tag(name = "Bulletins")
	public Response getBulletinsElevesByAnnee(@QueryParam("annee") Long annee ,@QueryParam("matricule") String  matricule, @QueryParam("classe") Long classe) {
		return Response.ok(bulletinService.getBulletinsEleveByAnnee(annee, matricule, classe)).build();
	}

	@GET
	@Path("/get-bulletin-eleve-annee-periode")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "Obtenir le bulletin d'un élève", summary = "")
	@Tag(name = "Bulletins")
	public Response getBulletinsElevesByAnneeAndPeriode(@QueryParam("annee") Long annee ,@QueryParam("matricule") String  matricule, @QueryParam("classe") Long classe, @QueryParam("periode") Long periode) {
		return Response.ok(bulletinService.getBulletinsEleveByAnneeAndPeriode(annee, matricule, classe, periode)).build();
	}

	@POST
	@Path("/apply-moyenne-adjustment")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Operation(description = "Appliquer la modification de moyenne", summary = "")
	@Tag(name = "Bulletins")
	public Response saveAdjustMoyenne(BulletinDto bulletinDto) {
		try {
			bulletinService.handleAdjustmentMoyenne(bulletinDto);
			return Response.ok("Opération bien effectuée").build();
		}catch (RuntimeException e) {
			return Response.serverError().entity(e).build();
		}
	}
}
