package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.dto.DetailProgressionDto;
import com.vieecoles.steph.dto.ProgressionDto;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.DetailProgression;
import com.vieecoles.steph.entities.Message;
import com.vieecoles.steph.services.DetailProgressionService;

@Path("/detail-progression")
public class DetailProgressionResource {
	
	@Inject
	DetailProgressionService detailProgressionService;
	
	@Path("/handle-save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "Enregistrement des progressions", summary = "")
	@Tag(name = "Progression")
	public Response handleSave(DetailProgressionDto dto) {
		Message message = new Message();
		try {
			if(!detailProgressionService.validator(dto)) {
				message.setType(Constants.ERROR_TYPE);
				message.setTitle("Erreur de validation");
				message.setDetail("Veuillez vérifier que les données saisies sont correctement renseignées");
				throw new RuntimeException("Veuillez vérifier que les données envoyées sont correctement renseignés");
			}
			
			message = detailProgressionService.handleSave(dto);
			return Response.ok(message).build();
		} catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(message).build();
		}
	}
	
	@Path("/get-by-progression/{progression}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(description = "Listes des détails par progression", summary = "")
	@Tag(name = "Detail Progression")
	public Response getByAnnee(@PathParam("progression") String progression) {
		List<DetailProgression> list = detailProgressionService.getByProgressionId(progression);
		List<DetailProgressionDto> dtos = new ArrayList<DetailProgressionDto>();
		for(DetailProgression obj : list) {
			dtos.add(detailProgressionService.convertToDto(obj, null));
		}
		return Response.ok(dtos).build();
	}
}
