package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.vieecoles.steph.entities.Bulletin;
import com.vieecoles.steph.entities.Classe;
import com.vieecoles.steph.services.BulletinService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
			nbreEleve = bulletinService.handleSave(classe, annee, periode);
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
}
