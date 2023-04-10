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

import com.vieecoles.steph.entities.ClasseEleveMatiere;
import com.vieecoles.steph.entities.Message;
import com.vieecoles.steph.services.ClasseEleveMatiereService;

@Path("/Classe-eleve-matiere")
public class ClasseEleveMatiereResource {
	
	@Inject
	ClasseEleveMatiereService classeEleveMatiereService;

	@Path("/list")
	@Tag(name = "ClasseEleveMatiere")
	@GET
	public Response getList() {
		List<ClasseEleveMatiere> classeEleveMatieres= new ArrayList<ClasseEleveMatiere>();
		try {
			classeEleveMatieres= classeEleveMatiereService.getList();
			return Response.ok(classeEleveMatieres).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(classeEleveMatieres).build();
		}
	}

	
	@Path("get-by-id")
	@GET
	@Tag(name = "ClasseEleveMatiere")
	public Response findById(@QueryParam("id") String id) {
		ClasseEleveMatiere classeEleveMatiere  = new ClasseEleveMatiere();
		try {
			classeEleveMatiere = classeEleveMatiereService.getById(id);
			return Response.ok(classeEleveMatiere).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(classeEleveMatiere).build();
		}
	}

	@Path("/get-by-classe-eleve-matiere-annee/{classe}/{matiere}/{eleve}/{annee}/{periode}")
	@Tag(name = "ClasseEleveMatiere")
	@GET
	public Response findByClasseAndMatiereAndEleveAndAnnee(@PathParam("classe") long classe, @PathParam("matiere") long matiere, @PathParam("eleve") long eleve, @PathParam("annee") long annee, @PathParam("periode") long periode) {
		ClasseEleveMatiere classeEleveMatiere = new ClasseEleveMatiere();
		try {
			classeEleveMatiere = classeEleveMatiereService.findByClasseAndMatiereAndEleveAndAnneeAndPeriode(classe,matiere, eleve, annee, periode);
			return Response.ok(classeEleveMatiere).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(classeEleveMatiere).build();
		}
	}

	@Path("/create")
	@Tag(name = "ClasseEleveMatiere")
	@POST
	public Response create(ClasseEleveMatiere classeEleveMatiere) {
		try {
			classeEleveMatiereService.create(classeEleveMatiere);
			return Response.ok(classeEleveMatiere).header("id-generate", classeEleveMatiere.getId()).header("info", "Operation bien effectuée").build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getCause().toString()).header("info", "Erreur lors de l'opération d'enregistrement").build();
		}
	}

	@Path("/update/{id}")
	@Tag(name = "ClasseEleveMatiere")
	@POST
	public Response update(@PathParam("id") String id, ClasseEleveMatiere classeEleveMatiere) {
		try {
			classeEleveMatiereService.update(id, classeEleveMatiere);
			return Response.ok(new Message("Info","updating","opération bien effectuée")).build();
		}catch (RuntimeException e) {
			e.printStackTrace();
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
}
