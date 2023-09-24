package com.vieecoles.ressource;


import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.smallrye.common.annotation.Blocking;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sendEmail")
public class SendMailService {
    @Inject
    Mailer mailer ;
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String sendEmail(@QueryParam("destinataire") String destinataire ,@QueryParam("objet") String objet ,@QueryParam("message") String message  ) {
    Mail mail=   Mail.withText(destinataire, objet, message);
        mailer.send(mail);
        System.out.println("Email envoyé avec succès!") ;
      return "Email envoyé avec succès!";
    }
}
