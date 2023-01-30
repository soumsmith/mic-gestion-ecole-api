package com.vieecoles.ressource.operations.etats;


import com.mysql.cj.Query;
import com.vieecoles.entities.profil;
import com.vieecoles.projection.BulletinSelectDto;
import com.vieecoles.projection.IdentiteSpiderDto;
import com.vieecoles.services.profilService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/rapport-global-pouls")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class rapportSpiderRessource {
    @Inject
    EntityManager em;
   
   @Transactional
   @GET
   @Path("/details-rapport-global-pouls-infos/{type}/{matricule}")
    public List<IdentiteSpiderDto>  detailBulletinInfos(@PathParam("type") String type,@PathParam("matricule") String matricule) throws Exception, JRException {
        List<IdentiteSpiderDto>  detailsBull = new ArrayList<>() ;
        TypedQuery<IdentiteSpiderDto> q = em.createQuery( "SELECT new com.vieecoles.projection.IdentiteSpiderDto(f.fon_nomPrenoms,f.fon_fonction,f.fon_adresse,f.fon_telephone,f.fon_cellulaire,f.fon_email,f.fon_code_etablissement, e.etab_denomination,e.etab_num_decision_ouverture,e.etab_code_etablissement,e.etab_situation_geographique,e.etab_adresse,e.etab_telephone,e.etab_fax,e.etab_email,d.direct_adresse,d.direct_telephone,d.direct_cellulaire,d.direct_email,d.direct_numero_autorisation_enseigner,d.direct_nom_prenom,d.direct_code_etablissement) from r_info_fondateur f ,r_info_etablissement e ,r_directeur_des_etudes d where f.fon_code_etablissement=:code and e.etab_code_etablissement=:code and d.direct_code_etablissement=:code ", IdentiteSpiderDto.class);
        detailsBull = q.setParameter("code", matricule).getResultList() ;
System.out.print("detailsBull "+detailsBull);
       return detailsBull ;
    
      
      
    }


  
    @GET
    @Path("/details-bulletin/{type}/{matricule}/{idEcole}/{libelleAnnee}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getdetailsBulletin(@PathParam("type") String type,@PathParam("matricule") String matricule,@PathParam("idEcole") Long idEcole,@PathParam("libelleAnnee") String libelleAnnee) throws Exception, JRException {
        List<BulletinSelectDto>  detailsBull = new ArrayList<>() ;
       // detailsBull = detailBulletinInfos(matricule,idEcole,libelleAnnee);
 
       TypedQuery<BulletinSelectDto> q = em.createQuery( "SELECT new com.vieecoles.projection.BulletinSelectDto(b.ecoleId,b.nomEcole,b.statutEcole,b.urlLogo,b.adresseEcole,b.telEcole,b.anneeLibelle, b.libellePeriode,b.matricule,b.nom, b.prenoms, b.sexe,b.dateNaissance,b.lieuNaissance,b.nationalite,b.redoublant,b.boursier,b.affecte,b.libelleClasse,b.effectif,b.totalCoef,b.totalMoyCoef,b.nomPrenomProfPrincipal,b.heuresAbsJustifiees,b.heuresAbsNonJustifiees,b.moyGeneral,b.moyMax,b.moyMin,b.moyAvg,b.moyAn,b.rangAn,b.appreciation,b.dateCreation,b.codeQr,b.statut,d.matiereLibelle,d.moyenne,d.rang,d.coef,d.moyCoef,d.appreciation,d.categorie,d.num_ordre,b.rang,d.nom_prenom_professeur) from DetailBulletin  d join d.bulletin b where b.matricule=:matricule order by d.num_ordre ASC  ", BulletinSelectDto.class);
       detailsBull = q.setParameter("matricule", matricule).getResultList() ;
       

System.out.print("soummm"+detailsBull.toString());
        if(type.toUpperCase().equals("PDF")){
            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
            JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/etats/BulletinBean.jrxml"));
            Map<String, Object> map = new HashMap<>();
           // map.put("title",type);
            JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
    //to pdf ;
      byte[] data =JasperExportManager.exportReportToPdf(report);
      HttpHeaders headers= new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=BulletinBean.pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(data);
    } else {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(detailsBull) ;
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/etats/BulletinBean.jrxml"));
        Map<String, Object> map = new HashMap<>();
       // map.put("title", type);
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);
        JRDocxExporter exporter = new JRDocxExporter();    
        exporter.setExporterInput(new SimpleExporterInput(report)); 
       // File exportReportFile = new File("profils" + ".docx"); 
        ByteArrayOutputStream baos = new ByteArrayOutputStream();    
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));    
        exporter.exportReport();
        byte[] data = baos.toByteArray() ;
        HttpHeaders headers= new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=BulletinBean.docx");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }
       
    
      
      
    }



    

}
