package com.vieecoles.ressource.operations;

import com.vieecoles.entities.tenant;
import com.vieecoles.services.tenantService;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

@Path("/jasper")
@Produces(MediaType.MULTIPART_FORM_DATA)
@Consumes(MediaType.APPLICATION_JSON)
public class jasperRessource {
    @Inject
    tenantService matService ;
    @GET
    public Response jasper() throws IOException {
       return connecJasper();
    }




    public Response  connecJasper() throws IOException {
        String retourMessage="";
        Response MyResponse = null;
        int responseCode;
        URL urlForGetRequest = new URL("http://localhost:83/jasperserver/rest_v2/login?j_username=jasperadmin&j_password=jasperadmin");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        responseCode = conection.getResponseCode();

        URL urlForGetRequest2 = new URL("http://localhost:83/jasperserver/rest_v2/reports/Reports/Documents_administratifs/Certificat_de_scolarite.pdf?codeEcole=01&matriculeEleve=7487486P");
        String readLine2 = null;
        HttpURLConnection conection2 = (HttpURLConnection) urlForGetRequest2.openConnection();
        conection2.setRequestMethod("GET");



        MyResponse= Response.status(conection2.getResponseCode()).entity("Message Suss").build();
        return MyResponse;

    }



    public Response  lOadCerficate() throws IOException {
        connecJasper() ;
        String retourMessage="";
        Response MyResponse = null;
        int responseCode;
        URL urlForGetRequest = new URL("http://localhost:83/jasperserver/rest_v2/reports/Reports/Documents_administratifs/Certificat_de_scolarite.pdf?codeEcole=01&matriculeEleve=7487486P");
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        responseCode = conection.getResponseCode();

        MyResponse= Response.status(conection.getResponseCode()).entity("Message Suss").build();
        return MyResponse;

    }


    }


