package com.vieecoles.steph.ressources;

import javax.ws.rs.Produces;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.vieecoles.steph.dto.MultipartBody;

@Path("/file")
public class FileResource {

	@Path("/send")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response setFile(@MultipartForm MultipartBody file) {
		/**
		 * NB: FICHIER A ENREGISTRER SI BESOIN
		 */
		try {
			System.out.println(String.format("Fichier reçu : %s bytes minimum", file.content.available()));
		} catch (IOException e) {
			System.out.println(String.format(String.format("%s : %s", e.getClass().getName(), e.getCause().getMessage())));
		}
		return Response.ok("Fichier uploaded").build();
	}
}
