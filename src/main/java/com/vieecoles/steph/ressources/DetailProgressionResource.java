package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.dto.DetailProgressionDto;
import com.vieecoles.steph.entities.Constants;
import com.vieecoles.steph.entities.DetailProgression;
import com.vieecoles.steph.entities.Message;
import com.vieecoles.steph.services.DetailProgressionService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
	
	@Path("/handle-delete")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(description = "Suppression des details", summary = "")
	@Tag(name = "Detail Progression")
	public Response handleDelete(List<String> ids) {
		String message = "";
		try {
			message = detailProgressionService.handleDelete(ids);
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
		return Response.ok(message).build();
	}
}
