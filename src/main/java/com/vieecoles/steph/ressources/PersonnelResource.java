package com.vieecoles.steph.ressources;

import com.vieecoles.steph.dto.AnneeDto;
import com.vieecoles.steph.services.AnneeService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.entities.Personnel;
import com.vieecoles.steph.services.PersonnelService;

@Path("/personnels")
public class PersonnelResource {
	@Inject
	AnneeService anneeService;
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
	@Path("/get-by-ecole-and-profil")
	@Tag(name = "Personnel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listByEcoleAndProfil(@QueryParam("ecole") Long ecoleId, @QueryParam("profil") int profilId) {
		List<Personnel> personnels = new ArrayList<>();
		AnneeDto  anneeDto= new AnneeDto() ;
		anneeDto=anneeService.getOpenAnneeByEcoleDto(ecoleId) ;
		try {
			personnels = personnelService.getByEcoleAndProfil_v2_Anneeid(ecoleId, profilId,anneeDto.getAnneeOuverteCentraleId());
			logger.log(Level.INFO, "Param - listProf size ::: {0} ", personnels.size());
		} catch (RuntimeException e) {
			logger.log(Level.WARNING, "Error - listProf :::{0} ", e);
		}
		return Response.ok().entity(personnels.stream().sorted((x,y) -> x.getNom().compareTo(y.getNom())).collect(Collectors.toList())).build();
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
