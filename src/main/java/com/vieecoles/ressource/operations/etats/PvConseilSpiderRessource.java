package com.vieecoles.ressource.operations.etats;


import com.vieecoles.dto.*;
import com.vieecoles.services.etats.MatriceClasseBilanServices;
import com.vieecoles.services.etats.MatriceClasseEtMoyennesServices;
import com.vieecoles.services.etats.MatriceClasseServices;
import com.vieecoles.services.etats.PvConseilDeclasseServices;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.util.*;

@Path("/imprimer-rapport-pv-conseil-classe")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class PvConseilSpiderRessource {
    @Inject
    EntityManager em;
    @Inject
    PvConseilDeclasseServices resultatsServices ;
    @Inject
    MatriceClasseServices matriceClasseServices ;

    @Inject
    MatriceClasseBilanServices matriceBilanClasseServices ;

    @Inject
    MatriceClasseEtMoyennesServices matriceClasseEtMoyennesServices ;


    private static String UPLOAD_DIR = "/data/";


    @GET
    @Transactional
    @Path("/pouls-Conseil-classe/{idEcole}/{libelleAnnee}/{libelleTrimetre}/{classe}/{anneeId}/{idclasse}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libelleAnnee") String libelleAnnee,@PathParam("libelleTrimetre")
    String libelleTrimetre ,@PathParam("classe") String classe ,@PathParam("anneeId") Long anneeId,@PathParam("idclasse") Long idclasse ) throws Exception, JRException {
        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/Spider_Book_PV_Conseil_classe.jrxml");

        spiderPvDto detailsBull= new spiderPvDto() ;
        List<ConseilClasseDto>  dspsDto = new ArrayList<>() ;

        List<matriceClasseDto> detailsBull1= new ArrayList<>() ;
        List<matiereMoyenneBilanDto> detailsBull2= new ArrayList<>() ;
        detailsBull2=  matriceBilanClasseServices.getInfosBilanMatriceClasse(idEcole ,libelleAnnee ,libelleTrimetre ,anneeId, idclasse) ;

        detailsBull1=   matriceClasseServices.getInfosMatriceClasse(idEcole ,libelleAnnee ,libelleTrimetre ,anneeId, idclasse) ;
        dspsDto= resultatsServices.RecapCalculResultatsEleveAffecte(idEcole ,libelleAnnee,libelleTrimetre,classe) ;

        detailsBull.setMatiereMoyenneBilanDto(detailsBull2);
        detailsBull.setMatriceClasseDto(detailsBull1);
        detailsBull.setDspsDto(dspsDto);

        System.out.println("detailsBull "+ detailsBull);

        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Collections.singleton(detailsBull)) ;
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        // map.put("title", type);

        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beanCollectionDataSource);

        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=PvConseil.pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);



    }

    @GET
    @Transactional
    @Path("/infos/pouls-rapport-pv/{idEcole}/{libelleAnnee}/{libelleTrimetre}/{classe}/{anneeId}/{idclasse}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public  ResponseEntity<byte[]>   getDtoRapport2(@PathParam("idEcole") Long idEcole , @PathParam("libelleAnnee") String libelleAnnee, @PathParam("libelleTrimetre") String libelleTrimetre
        , @PathParam("classe") String classe ,@PathParam("anneeId") Long anneeId,@PathParam("idclasse") Long idclasse ) throws Exception, JRException {

        InputStream myInpuStream ;
        /*myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/BulletinBean.jrxml");*/
        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/matriceClassePvNew.jrxml");

        List<matriceClasseDto> detailsBull1= new ArrayList<>() ;
        detailsBull1=   matriceClasseServices.getInfosMatriceClasse(idEcole ,libelleAnnee ,libelleTrimetre ,anneeId, idclasse) ;

        ByteArrayOutputStream excelOutputStream = new ByteArrayOutputStream();

        createExcelDocument(excelOutputStream,detailsBull1);

        // Convertir le contenu en tableau d'octets
        byte[] excelFile = excelOutputStream.toByteArray();

        // Préparer les en-têtes pour la réponse
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=Rapport_Pouls_Scolaire.xlsx");

        // Retourner la réponse avec le fichier Excel
        return ResponseEntity.ok()
            .headers(headers)
            .contentType(org.springframework.http.MediaType.APPLICATION_OCTET_STREAM)
            .body(excelFile);

    }


    private void createExcelDocument(ByteArrayOutputStream outputStream,List<matriceClasseDto> detailsBull1) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Moyennes des élèves");

// Style des cellules pour l'en-tête (facultatif)
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

// Récupération unique des libellés des matières
        Set<String> libellesMatieres = new LinkedHashSet<>();
        for (matriceClasseDto eleve : detailsBull1) {
            for (matiereMoyenneDto matiereMoyenne : eleve.getMatiereMoyenneDto()) {
                libellesMatieres.add(matiereMoyenne.getLibelleMatiere());
            }
        }

// Création de l'en-tête
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Nom");
        headerRow.createCell(1).setCellValue("Prénoms");
        headerRow.createCell(2).setCellValue("Matricule");

        int columnIndex = 3;
        for (String libelleMatiere : libellesMatieres) {
            Cell cell = headerRow.createCell(columnIndex);
            cell.setCellValue(libelleMatiere);
            cell.setCellStyle(headerStyle);
            columnIndex++;
        }

        headerRow.createCell(columnIndex).setCellValue("Moyenne Générale");
        headerRow.createCell(columnIndex + 1).setCellValue("Rang");
        headerRow.createCell(columnIndex + 2).setCellValue("Appréciation");
        headerRow.createCell(columnIndex + 3).setCellValue("Signature");

// Ajout des données des élèves
        Map<String, Double> totauxMatieres = new HashMap<>();
        Map<String, Integer> comptesMatieres = new HashMap<>();
        int rowIndex = 1;
        for (matriceClasseDto eleve : detailsBull1) {
            Row row = sheet.createRow(rowIndex);

            // Ajout des informations générales de l'élève
            row.createCell(0).setCellValue(eleve.getNom());
            row.createCell(1).setCellValue(eleve.getPrenoms());
            row.createCell(2).setCellValue(eleve.getMatricule());

            // Ajout des moyennes pour chaque matière
            Map<String, Double> moyennesParMatiere = new HashMap<>();
            for (matiereMoyenneDto matiereMoyenne : eleve.getMatiereMoyenneDto()) {
                moyennesParMatiere.put(matiereMoyenne.getLibelleMatiere(), matiereMoyenne.getMoyMatiere());
            }

            columnIndex = 3;
            for (String libelleMatiere : libellesMatieres) {
                Double moyenne = moyennesParMatiere.get(libelleMatiere);
                if (moyenne >=0) {
                    row.createCell(columnIndex).setCellValue(moyenne);
                    // Ajouter au total et incrémenter le compteur
                    totauxMatieres.put(libelleMatiere, totauxMatieres.getOrDefault(libelleMatiere, 0.0) + moyenne);
                    comptesMatieres.put(libelleMatiere, comptesMatieres.getOrDefault(libelleMatiere, 0) + 1);
                } else if(moyenne==-1){
                    row.createCell(columnIndex).setCellValue("NC");
                }
                else {
                    row.createCell(columnIndex).setCellValue(""); // Cellule vide si aucune moyenne
                }
                columnIndex++;
            }





            // Moyenne générale, rang, et appréciation
            row.createCell(columnIndex).setCellValue(eleve.getMoyenTrimes());
            row.createCell(columnIndex + 1).setCellValue(eleve.getRang());
            row.createCell(columnIndex + 2).setCellValue(eleve.getAppreciation());
            row.createCell(columnIndex + 3).setCellValue("");

            rowIndex++;
        }

        // Création du style pour les cellules avec deux chiffres après la virgule
        CellStyle numericStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        numericStyle.setDataFormat(dataFormat.getFormat("0.00"));

        // Ajout de la ligne Moyenne Générale
        Row moyenneRow = sheet.createRow(rowIndex);
        Cell moyenneCell = moyenneRow.createCell(0);
        moyenneCell.setCellValue("Moyenne Générale");
        moyenneCell.setCellStyle(headerStyle);

// Fusion des cellules pour l'étiquette
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 2));

        columnIndex = 3;
        for (String libelleMatiere : libellesMatieres) {
            Double total = totauxMatieres.getOrDefault(libelleMatiere, 0.0);
            Integer count = comptesMatieres.getOrDefault(libelleMatiere, 0);
            Cell cell = moyenneRow.createCell(columnIndex);

            if (count > 0) {
                double moyenne = total / count;
                cell.setCellValue(moyenne);
                cell.setCellStyle(numericStyle);
            } else {
                cell.setCellValue("NC");
            }
            columnIndex++;
        }

// Auto-ajustement des colonnes
        for (int i = 0; i <= columnIndex + 2; i++) {
            sheet.autoSizeColumn(i);
        }

// Écriture dans le flux de sortie
        workbook.write(outputStream);
        workbook.close();


    }




}
