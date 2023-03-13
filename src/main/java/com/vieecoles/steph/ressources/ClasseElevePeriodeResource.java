package com.vieecoles.steph.ressources;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.vieecoles.steph.entities.ClasseElevePeriode;
import com.vieecoles.steph.entities.Message;
import com.vieecoles.steph.services.ClasseElevePeriodeService;

@Path("/Classe-eleve-periode")
public class ClasseElevePeriodeResource {
	
	@Inject
	ClasseElevePeriodeService classeElevePeriodeService;

	@Path("/list")
	@Tag(name = "ClasseElevePeriode")
	@GET
	public Response getList() {
		List<ClasseElevePeriode> classeElevePeriodes= new ArrayList<ClasseElevePeriode>();
		try {
			classeElevePeriodes= classeElevePeriodeService.getList();
			return Response.ok(classeElevePeriodes).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(classeElevePeriodes).build();
		}
	}

	
	@Path("get-by-id")
	@GET
	@Tag(name = "ClasseElevePeriode")
	public Response findById(@QueryParam("id") String id) {
		ClasseElevePeriode classeElevePeriode  = new ClasseElevePeriode();
		try {
			classeElevePeriode = classeElevePeriodeService.getById(id);
			return Response.ok(classeElevePeriode).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(classeElevePeriode).build();
		}
	}

	@Path("/get-by-classe-eleve-matiere-annee/{classe}/{matiere}/{eleve}/{annee}")
	@Tag(name = "ClasseElevePeriode")
	@GET
	public Response findByClasseAndMatiereAndEleveAndAnnee(@PathParam("classe") long classe, @PathParam("eleve") long eleve, @PathParam("annee") long annee, @PathParam("periode") long periode) {
		ClasseElevePeriode classeElevePeriode = new ClasseElevePeriode();
		try {
			classeElevePeriode = classeElevePeriodeService.findByClasseAndEleveAndAnneeAndPeriode(classe, eleve, annee, periode);
			return Response.ok(classeElevePeriode).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(classeElevePeriode).build();
		}
	}

	@Path("/create")
	@Tag(name = "ClasseElevePeriode")
	@POST
	public Response create(ClasseElevePeriode classeElevePeriode) {
		try {
			classeElevePeriodeService.create(classeElevePeriode);
			return Response.ok(classeElevePeriode).header("id-generate", classeElevePeriode.getId()).header("info", "Operation bien effectuée").build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getCause().toString()).header("info", "Erreur lors de l'opération d'enregistrement").build();
		}
	}

	@Path("/update/{id}")
	@Tag(name = "ClasseElevePeriode")
	@POST
	public Response update(@PathParam("id") String id, ClasseElevePeriode classeElevePeriode) {
		try {
			classeElevePeriodeService.update(id, classeElevePeriode);
			return Response.ok(new Message("Info","updating","opération bien effectuée")).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
