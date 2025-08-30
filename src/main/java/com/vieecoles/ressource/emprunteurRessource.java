package com.vieecoles.ressource;

import com.vieecoles.entities.emprunteur;
import com.vieecoles.services.emprunteurService;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/emprunteur")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class emprunteurRessource {
    @Inject
    emprunteurService matService ;
    @GET
    public List<emprunteur> list() {
        return matService.getListemprunteur();
    }

    @GET
    @Path("/{id}")
    public emprunteur get(@PathParam("id") Long id) {
        return matService.findById(id);
    }
    @POST
    @Transactional
    public Response create(emprunteur mat) {
        return matService.createemprunteur(mat);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public emprunteur update(@PathParam("id") Long id, emprunteur mat) {
    return matService.updateemprunteur(id,mat);

    }
    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        matService.deleteemprunteur(id);
    }

    @GET
    @Path("/search/{nom}")
    public List<emprunteur> search(@PathParam("nom") String libelle) {
        return matService.search(libelle);
    }

    @GET
    @Path("/count")
    public Long count() {
        return matService.count();
    }


   /* @Path("/reports")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public static class reportRessource {
        @Inject
        cycleService matService ;
      //  @GET
     //   @Path("/reports/effectifParStatutsEtSexe")
*//*        public String  reportParStatutEtSexe() throws FileNotFoundException, JRException {
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Arrays.asList(

                new ReportEffectifParSexeStatut("6ième1","6ième", 12,45,63,54,52,
                        16,45,52),
                    new ReportEffectifParSexeStatut("6ième2","6ième", 12,45,63,54,52,
                            16,45,52),
                    new ReportEffectifParSexeStatut("6ième3","6ième", 12,45,63,54,52,
                            16,45,52),
                    new ReportEffectifParSexeStatut("5ième1","5ième", 12,45,63,54,52,
                            16,45,52),
                    new ReportEffectifParSexeStatut("5ième1","5ième", 12,45,63,54,52,
                            16,45,52),
                    new ReportEffectifParSexeStatut("5ième2","5ième", 12,45,63,54,52,
                            16,45,52),
                    new ReportEffectifParSexeStatut("5ième3","5ième", 12,45,63,54,52,
                            16,45,52)


        ), false);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("ecoleName", "SoumEcole");

            JasperReport compileReport = JasperCompileManager
                .compileReport(new FileInputStream("src/main/resources/BilanDesEffectifs.jrxml"));

        JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, parameters, beanCollectionDataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, System.currentTimeMillis() + ".pdf");
    return  "generated ";
        }*//*


    }*/
}
