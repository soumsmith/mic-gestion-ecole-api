package com.vieecoles.steph.ressources;

import javax.ws.rs.Produces;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.vieecoles.steph.dto.MultipartBody;
import com.vieecoles.steph.util.FileUtils;

@Path("/file")
public class FileResource {

//	@Path("/send")
//	@POST
//	@Produces(MediaType.TEXT_PLAIN)
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	public Response setFile(@MultipartForm MultipartBody file) {
//		/**
//		 * NB: FICHIER A ENREGISTRER SI BESOIN
//		 */
//		try {
//			System.out.println(String.format("Fichier reçu : %s bytes minimum", file.content.available()));
//		} catch (IOException e) {
//			System.out.println(String.format(String.format("%s : %s", e.getClass().getName(), e.getCause().getMessage())));
//		}
//		return Response.ok("Fichier uploaded").build();
//	}

	@Path("/send-files/file")
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response sendFiles(@MultipartForm MultipartBody file) {

		if (file.file == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Le fichier est manquant").build();
		}
		String uri = "";
		String baseRepository = "/var/pouls_scolaire/upload-folder/";
		String fileName = file.fileName;
		try {
//			System.out.println(String.format("Fichier reçu : %s bytes minimum", file.content.available()));

			InputStream inputStream = file.file;
			fileName = FileUtils.generateNewFileName(fileName);
			String prefix = file.prefixUri;
//			System.out.println("prefix : " + prefix);
			if (prefix != null && !prefix.equals("")) {
				if (FileUtils.mkdirs(baseRepository + prefix)) {
					fileName = prefix + "/" + fileName;
				}
			}
			uri = baseRepository + fileName;
			FileOutputStream out = new FileOutputStream(uri);
			System.out.println("uri = " + uri);
			byte[] buffer = new byte[1024];
			int bytesRead;

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(String.format("%s : %s", e.getClass().getName(), e.getCause().getMessage()));
		}
		return Response.ok("Fichier uploaded").header("uri-file", fileName).build();
	}
}
