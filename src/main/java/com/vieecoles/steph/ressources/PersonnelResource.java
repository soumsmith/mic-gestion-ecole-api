package com.vieecoles.steph.ressources;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.print.attribute.standard.MediaSize.NA;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.entities.Personnel;
import com.vieecoles.steph.services.PersonnelService;

@Path("/personnels")
public class PersonnelResource {

	@Inject
	PersonnelService personnelService;

	Logger logger = Logger.getLogger(this.getClass().getName());

	@GET
	@Path("/get-by-id/{id}")
	@Tag(name = "Personnel")
	public Response getById(@PathParam("id") Long id) {
		System.out.println(personnelService.getById(id));
		return Response.ok().entity(personnelService.getById(id)).build();
	}

	@GET
	@Path("/list")
	@Tag(name = "Personnel")
	public Response list() {
		return Response.ok().entity(personnelService.getList()).build();
	}

	@GET
	@Path("/get-by-fonction")
	@Tag(name = "Personnel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listProf(@QueryParam("fonction") int fonctionId, @QueryParam("ecole") Long ecoleId) {
		List<Personnel> personnels = null;
		try {
			logger.log(Level.INFO, "Param - listProf ::: {0} ", fonctionId+" "+ecoleId);
			personnels = personnelService.getListByFonction(fonctionId, ecoleId);
			logger.log(Level.INFO, "Param - listProf size ::: {0} ", personnels.size());
		} catch (RuntimeException e) {
			logger.log(Level.WARNING, "Error - listProf :::{0} ", e);
		}
		return Response.ok().entity(personnels).build();
	}

	@GET
	@Path("/get-by-fonction-and-classe")
	@Tag(name = "Personnel")
	public Response listProfByClasse(@QueryParam("fonction") Long fonctionId, Long classeId) {
		return Response.ok().entity(personnelService.getListByFonctionAndClasse(fonctionId, classeId)).build();
	}

	@GET
	@Path("/get-by-user-id/{id}")
	@Tag(name = "Personnel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByUserId(@PathParam("id") Long userId) {
		return Response.ok().entity(personnelService.getByUserId(userId)).build();
	}
	
	@GET
	@Path("/get-by-user-ecole/{userId}/{ecoleId}")
	@Tag(name = "Personnel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByUserAndEcole(@PathParam("userId") Long userId, @PathParam("ecoleId") Long ecoleId) {
		return Response.ok().entity(personnelService.getByUserAndEcole(userId, ecoleId)).build();
	}
	
	@GET
	@Path("/get-by-ecole/{ecoleId}")
	@Tag(name = "Personnel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByEcole(@PathParam("ecoleId") Long ecoleId) {
		return Response.ok().entity(personnelService.getByEcole(ecoleId)).build();
	}
	
	@GET
	@Path("/count-by-ecole/{ecoleId}")
	@Tag(name = "Personnel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response countByEcole(@PathParam("ecoleId") Long ecoleId) {
		return Response.ok().entity(personnelService.countByEcole(ecoleId)).build();
	}
	
	@GET
	@Path("/count-by-ecole-genre/{ecoleId}")
	@Tag(name = "Personnel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response countByEcoleAndGenre(@PathParam("ecoleId") Long ecoleId) {
		return Response.ok().entity(personnelService.countByEcole(ecoleId)).build();
	}
}
