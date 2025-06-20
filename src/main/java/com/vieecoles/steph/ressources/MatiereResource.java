package com.vieecoles.steph.ressources;

import com.vieecoles.steph.dto.MatiereDto;
import com.vieecoles.steph.entities.Ecole;
import com.vieecoles.steph.entities.Matiere;
import com.vieecoles.steph.services.MatiereService;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/matiere")
@Tag(name = "Matiere")
public class MatiereResource {

	@Inject
	MatiereService matiereService;

	// Logger logger = Logger.getLogger(MatiereResource.class.getName());

	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response list() {
		List<Matiere> matieres = matiereService.getList();
		List<MatiereDto> matieresDto = new ArrayList<MatiereDto>();
		for (Matiere m : matieres) {
			matieresDto.add(matiereService.buildEntityToDto(m));
		}

		return Response.ok().entity(matieresDto).build();
	}

	@GET
	@Path("/get-by-id")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getById(@QueryParam("id") long id) {

		return Response.ok().entity(matiereService.buildEntityToDto(matiereService.getById(id))).build();
	}

	// Quand le niveau d enseignement sera pris en compte dans les variables d env
	// alors remplacer le param ecole par niveau enseign
	@GET
	@Path("/get-by-niveau-enseignement")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByNiveau(@QueryParam("id") Long ecoleId) {
		Ecole ecole = Ecole.findById(ecoleId);
		return Response.ok().entity(matiereService.getByNiveauEnseignement(ecole.getNiveauEnseignement().getId()))
				.build();
	}

	@GET
	@Path("/get-by-niveau-enseignement-projection")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByNiveauProjection(@QueryParam("niveau") Long niveau) {
		return Response.ok().entity(matiereService.getByNiveauEnseignementProjection(niveau))
				.build();
	}

	@POST
	@Path("/update-display")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public Response updateAndDisplay(MatiereDto matiereDto) {
		try {
			Matiere ev = matiereService.updateAndDisplay(matiereService.buildDtoToEntity(matiereDto));
			if (ev == null) {
				throw new NotFoundException();
			}
			return Response.ok().entity(matiereService.buildEntityToDto(ev)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(new String("Erreur dans la mise à jour de matière : "+e.getMessage())).build();
		}
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(Matiere matiere) {
		try {
			// logger.info("Saving ...");
			matiereService.create(matiere);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity("Erreur de creation").build();
		}
		return Response.ok("Enregistrement reussi").build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response delete(@PathParam("id") String id) {
		try {
			matiereService.delete(Long.parseLong(id));
			return Response.ok("Suppression Matiere id = " + id).build();
		} catch (RuntimeException re) {
			re.printStackTrace();
			return Response.serverError().entity("Erreur lors de la suppression").build();
		}

	}

	@POST
	@Path("/saveAndDisplay")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createAndDisplay(MatiereDto matiereDto) {
		try {
			// logger.info("Saving and display ...");
			Matiere matiere = matiereService.createMatiereInEcole(matiereService.buildDtoToEntity(matiereDto));

			Matiere entity = Matiere.findById(matiere.getId());
			// Créér la matiere dans chaque ecole
			return Response.ok().entity(matiereService.buildEntityToDto(entity)).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
