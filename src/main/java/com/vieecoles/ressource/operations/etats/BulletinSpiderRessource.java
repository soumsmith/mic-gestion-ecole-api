package com.vieecoles.ressource.operations.etats;



import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.vieecoles.dto.DspsDto;
import com.vieecoles.dto.parametreDto;
import com.vieecoles.dto.spiderBulletinDto;
import com.vieecoles.dto.spiderDspsDto;
import com.vieecoles.entities.InfosPersoBulletins;
import com.vieecoles.entities.parametre;
import com.vieecoles.entities.operations.ecole;
import com.vieecoles.entities.operations.eleve;
import com.vieecoles.services.etats.BulletinRapportServices;
import com.vieecoles.services.etats.BulletinSpiderMatriculeServices;
import com.vieecoles.services.etats.BulletinSpiderServices;
import com.vieecoles.services.etats.DpspServices;
import com.vieecoles.services.souscription.SousceecoleService;
import com.vieecoles.steph.entities.Classe;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Blob;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.util.*;

@Path("/imprimer-bulletin-list")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)

public class BulletinSpiderRessource {
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/ecoleviedbv2";
    @Inject
    @ConfigProperty(name = "JDBC_DRIVER")
    private String JDBC_DRIVER ;
    @Inject
    @ConfigProperty(name = "DB_URL")
    private String DB_URL ;
    @Inject
    @ConfigProperty(name = "USER")
    private String USER ;
    @Inject
    @ConfigProperty(name = "PASS")
    private String PASS ;
    Connection dbConnection = null;
    @Inject
    BulletinSpiderMatriculeServices bulletinSpiderMatriculeServices ;

    @Inject
    EntityManager em;
    @Inject
    BulletinRapportServices dpspServices ;

    @Inject
    BulletinSpiderServices bulletinSpider ;
    @Inject
    SousceecoleService sousceecoleService ;


    private static String UPLOAD_DIR = "/data/";

    @GET
    @Path("/process-image")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response processImage(@QueryParam("url") String imageUrl) {

        try {
            // Télécharger l'image depuis l'URL
            BufferedImage originalImage = ImageIO.read(new URL(imageUrl));
            if (originalImage == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Impossible de lire l'image à partir de l'URL fournie.")
                    .build();
            }

            // Lire les métadonnées EXIF pour vérifier l'orientation
            Metadata metadata = ImageMetadataReader.readMetadata(new URL(imageUrl).openStream());
            int orientation = 1; // Orientation par défaut (normale)

            // Extraire les informations d'orientation
            ExifIFD0Directory exifIFD0 = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (exifIFD0 != null && exifIFD0.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
                orientation = exifIFD0.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            }

            // Corriger l'orientation si nécessaire
            BufferedImage orientedImage = correctOrientation(originalImage, orientation);

            // Dimensions originales
            int originalWidth = orientedImage.getWidth();
            int originalHeight = orientedImage.getHeight();

            // Calculer les nouvelles dimensions
            int newWidth = originalWidth;
            int newHeight = originalHeight;

            if (originalWidth > 1000 || originalHeight > 1000) {
                float aspectRatio = (float) originalWidth / originalHeight;
                if (aspectRatio > 1) {
                    newWidth = 1000;
                    newHeight = (int) (1000 / aspectRatio);
                } else {
                    newHeight = 1000;
                    newWidth = (int) (1000 * aspectRatio);
                }
            }

            // Redimensionner l'image
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(orientedImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            // Convertir l'image en tableau de bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", baos);
            byte[] imageBytes = baos.toByteArray();

            // Convertir les bytes en BLOB
            Blob imageBlob = new SerialBlob(imageBytes);

            // Retourner le BLOB
            return Response.ok(imageBlob.getBinaryStream())
                .header("Content-Disposition", "attachment; filename=processed_image.png")
                .build();

        } catch (IOException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erreur lors du traitement de l'image : " + e.getMessage())
                .build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Erreur lors de la conversion en BLOB : " + e.getMessage())
                .build();
        } catch (ImageProcessingException e) {
            throw new RuntimeException(e);
        } catch (MetadataException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @Path("/process-image-classe/{idEcole}/{libellePeriode}/{libelleAnnee}/{idClasse}")
    @Produces(MediaType.TEXT_PLAIN)
    public String processImageForClasse(@PathParam("idEcole") Long idEcole
        ,@PathParam("libellePeriode") String libellePeriode
        ,@PathParam("libelleAnnee") String libelleAnnee, @PathParam("idClasse") Long idClasse)
        throws ImageProcessingException, SQLException, IOException, MetadataException {
        return   bulletinSpider.getAndProcessImage(idClasse,libelleAnnee,libellePeriode,idEcole) ;

    }

    @GET
    @Path("/spider-bulletin/{idEcole}/{libellePeriode}/{libelleAnnee}/{idClasse}/{compress}/{niveauEnseign}/{positionLogo}/{filigranne}/{infoAmoirie}/{pivoter}/{modelePoincarre}/{distinct}/{modelelmd}/{testLourd}/{bulletinArabe}/{piedPage}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee , @PathParam("idClasse") Long libelleClasse ,@PathParam("compress") Boolean compress ,
                                                 @PathParam("niveauEnseign") Long niveauEnseign ,@PathParam("positionLogo") boolean positionLogo ,@PathParam("filigranne") boolean filigranne, @PathParam("infoAmoirie") boolean infoAmoiri,
                                                 @PathParam("pivoter") boolean pivoter ,
                                                 @PathParam("modelePoincarre") boolean modelePoincarre
        ,
                                                 @PathParam("distinct") boolean distinct ,
                                                 @PathParam("modelelmd") boolean modelelmd,
                                                 @PathParam("testLourd") boolean testLourd,
                                                 @PathParam("bulletinArabe") boolean bulletinArabe,
                                                 @PathParam("piedPage") boolean piedPage
    ) throws Exception, JRException {




        InputStream myInpuStream = null;
        Classe classe= new Classe() ;
        classe = Classe.findById(libelleClasse) ;

        if(!compress) {
            if(niveauEnseign==2) {

                System.out.println("Libelle Periode "+libellePeriode);
                if(testLourd){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTroisLourd.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTroisLourd.jrxml");

                    }
                }
                else if(!pivoter) {
                    System.out.println("Entree Pivot ");
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        System.out.println("callSpiderNobel90Troissoummmm.jrxml") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobel90Trois.jrxml");
                    }
                    else  {
                        System.out.println("callSpiderNobel90QQQQ.jrxml") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobel90.jrxml");

                    }

                }
                else if(piedPage) {
                    System.out.println("piedPage ");
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        System.out.println("callSpiderNobelPiedPageTrois.jrxml") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelPiedPageTrois.jrxml");
                    }
                    else  {
                        System.out.println("callSpiderNobel_par_interval.jrxml") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobel.jrxml");

                    }

                }


                else {
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        System.out.println("callSpiderNobelTrois.jrxmlMouuuuu") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTrois.jrxml");

                    }
                    else {
                        System.out.println("callSpiderNobel.jrxml") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobel.jrxml");

                    }


                }


            } else if (niveauEnseign==1) {
               if (bulletinArabe){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderArabeTrois.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderArabe.jrxml");

                    }
                }

                else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressTrois.jrxml");


                    else
                    {myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompress.jrxml");
                        System.out.println("callSpiderNobelDecompress") ;
                    }
                }


            }   else if (niveauEnseign==4) {
                if(!pivoter){
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                } else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                }


            }
            else if (niveauEnseign==5||niveauEnseign==6) {
                if(!pivoter) {
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTechniqueTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelTechnique.jrxml");
                } else {
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTechniqueTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelTechnique.jrxml");
                }


            }

            else if (niveauEnseign==3) {
                if(modelePoincarre){
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelBTSPointCarreTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelBTSPointCarre.jrxml");

                } else if (modelelmd){
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelLMDTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelLMD.jrxml");
                }

                else {
                    if(!pivoter){
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelBTSTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelBTS.jrxml");
                    } else{
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelBTSTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelBTS.jrxml");
                    }

                }


            }
// Fin compresser
        } else {
            if(niveauEnseign==2) {
                System.out.println("Libelle Periode "+libellePeriode);
                if(testLourd){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTroisLourd.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTroisLourd.jrxml");

                    }
                }
                else if (bulletinArabe){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderArabeTrois.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderArabe.jrxml");

                    }
                }
                else  if(!pivoter){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompress90Trois.jrxml");

                    }

                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompress90.jrxml");
                    System.out.println("callSpiderNobelDecompress90") ;
                } else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressTrois.jrxml");


                    else
                    {myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompress.jrxml");
                        System.out.println("callSpiderNobelDecompress") ;
                    }
                }


            } else if (niveauEnseign==4) {
                if(!pivoter) {
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                } else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                }


            }
            else if (niveauEnseign==1) {
               if (bulletinArabe){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderArabeTrois.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderArabe.jrxml");

                    }
                }

                else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressTrois.jrxml");


                    else
                    {myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompress.jrxml");
                        System.out.println("callSpiderNobelDecompress") ;
                    }
                }


            }
            else if (niveauEnseign==5||niveauEnseign==6) {
                if(!pivoter){
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressEtanTechniqueTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanTechnique.jrxml");
                    System.out.println("callSpiderNobelDecompressEtanTechnique.jrxml") ;
                } else {
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressEtanTechniqueTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanTechnique.jrxml");
                    System.out.println("callSpiderNobelDecompressEtanTechnique.jrxml") ;
                }


            }
            else if (niveauEnseign==3) {
                if(modelePoincarre){
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressEtanBTSPointCarreTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanBTSPointCarre.jrxml");

                } else {
                    if(!pivoter){
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressEtanTechniqueTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanBTS.jrxml");
                    }
                    else if (modelelmd){
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelLMDTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelLMD.jrxml");
                    }
                    else {
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressEtanTechniqueTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanBTS.jrxml");
                    }
                }



            }

        }

        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;



        Connection connection = DriverManager.getConnection("jdbc:mysql://db:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        String infos= null ;
        String pdistinct= null ;
        String plogoPosi= null ;
        String psetBg= null ;
        if(distinct){
            pdistinct="1";
        } else{
            pdistinct="0";
        }

        if(infoAmoiri){
            infos="1";
        } else{
            infos="0";
        }
        if(positionLogo){
            plogoPosi="1";
        } else{
            plogoPosi="0";
        }
        if(filigranne){
            psetBg="1";
        } else{
            psetBg="0";
        }
        ecole myEcole= new ecole() ;
        myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
        map.put("classe", classe.getLibelle());
        // map.put("classe", 	"6EME C");
        map.put("idEcole", idEcole);
        map.put("libelleAnnee", libelleAnnee);
        map.put("libellePeriode", libellePeriode);
        map.put("infosAmoirie", infos);
        map.put("distinctin", pdistinct);
        map.put("codeEcole", myEcole.getEcolecode());
        // map.put("codeEcole", "myEcole.getEcolecode()");
        map.put("positionLogo", plogoPosi);
        map.put("setBg", psetBg);


        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Bulletin-spider"+libelleClasse+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }
    @GET
    @Path("/spider-bulletin/{idEcole}/{libellePeriode}/{libelleAnnee}/{idClasse}/{compress}/{niveauEnseign}/{positionLogo}/{filigranne}/{infoAmoirie}/{pivoter}/{modelePoincarre}/{distinct}/{modelelmd}/{testLourd}/{bulletinArabe}/{debutImpression}/{finImpression}/{piedPage}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapportparIntervalle(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                              @PathParam("libelleAnnee") String libelleAnnee , @PathParam("idClasse") Long libelleClasse ,@PathParam("compress") Boolean compress ,
                                                              @PathParam("niveauEnseign") Long niveauEnseign ,@PathParam("positionLogo") boolean positionLogo ,@PathParam("filigranne") boolean filigranne, @PathParam("infoAmoirie") boolean infoAmoiri,
                                                              @PathParam("pivoter") boolean pivoter ,
                                                              @PathParam("modelePoincarre") boolean modelePoincarre
        ,
                                                              @PathParam("distinct") boolean distinct ,
                                                              @PathParam("modelelmd") boolean modelelmd,
                                                              @PathParam("testLourd") boolean testLourd,
                                                              @PathParam("bulletinArabe") boolean bulletinArabe,
                                                              @PathParam("debutImpression") Integer debutImpression,
                                                              @PathParam("finImpression") Integer finImpression,
                                                              @PathParam("piedPage") boolean piedPage

    ) throws Exception, JRException {




        InputStream myInpuStream = null;
        Classe classe= new Classe() ;
        classe = Classe.findById(libelleClasse) ;
Set<String> moisTrimestre = Set.of(
    "Novembre", "Décembre", "Janvier", "Février", "Mars", "Avril"
);
        if(!compress) {
            if(niveauEnseign==2) {

                System.out.println("Libelle Periode "+libellePeriode);
                if(testLourd){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTroisLourd.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTroisLourd.jrxml");

                    }
                }
                else if(!pivoter) {
                    System.out.println("Entree Pivot ");
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        System.out.println("callSpiderNobel90Troissoummmm.jrxml") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobel90Trois.jrxml");
                    }
                    else  {
                        System.out.println("callSpiderNobel90QQQQ.jrxml") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobel90.jrxml");

                    }

                }

                else if(piedPage) {
                    System.out.println("piedPage ");
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        System.out.println("callSpiderNobelPiedPageTrois.jrxml") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelPiedPageTrois.jrxml");
                    }
                    else  {
                        System.out.println("callSpiderNobel_par_interval.jrxml") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobel_par_interval.jrxml");

                    }

                }

                else {
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        System.out.println("callSpiderNobelTrois.jrxmlMouuuuu") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTrois.jrxml");

                    }
                    else {
                        System.out.println("callSpiderNobel.jrxml") ;
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobel_par_interval.jrxml");

                    }


                }


            } else if (niveauEnseign==1) {
                if (bulletinArabe){
                    if(moisTrimestre.contains(libellePeriode)) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderArabePrimaire.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderArabePrimaire.jrxml");

                    }
                }

                else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderPrimaireAutre.jrxml");


                    else
                    {myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderPrimaireAutre.jrxml");
                        System.out.println("callSpiderNobelDecompress") ;
                    }
                }


            }   else if (niveauEnseign==4) {
                if(!pivoter){
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                } else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/callSpiderMaternelle.jrxml");
                }


            }
            else if (niveauEnseign==5||niveauEnseign==6) {
                if(!pivoter) {
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTechniqueTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelTechnique.jrxml");
                } else {
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTechniqueTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelTechnique.jrxml");
                }


            }

            else if (niveauEnseign==3) {
                if(modelePoincarre){
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelBTSPointCarreTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelBTSPointCarre.jrxml");

                } else if (modelelmd){
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelLMDTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelLMD.jrxml");
                }

                else {
                    if(!pivoter){
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelBTSTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelBTS.jrxml");
                    } else{
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelBTSTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelBTS.jrxml");
                    }

                }


            }

        } else {
            if(niveauEnseign==2) {
                System.out.println("Libelle Periode "+libellePeriode);
                if(testLourd){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTroisLourd.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelTroisLourd.jrxml");

                    }
                }
                else if (bulletinArabe){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderArabeTrois.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderArabe.jrxml");

                    }
                }
                else  if(!pivoter){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompress90Trois.jrxml");

                    }

                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompress90.jrxml");
                    System.out.println("callSpiderNobelDecompress90") ;
                }

                else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressTrois.jrxml");


                    else
                    {myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobel_par_interval.jrxml");
                        System.out.println("callSpiderNobelDecompress") ;
                    }
                }


            } else if (niveauEnseign==4) {
                 if (bulletinArabe){
                    if(libellePeriode.equals("Troisième Trimestre")) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderArabePrimaire.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderArabePrimaire.jrxml");

                    }
                }

                else {
                    if(libellePeriode.equals("Troisième Trimestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderPrimaireAutre.jrxml");


                    else
                    {myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderPrimaireAutre.jrxml");
                        System.out.println("callSpiderNobelDecompress") ;
                    }
                }


            }
            else if (niveauEnseign==1) {
                 if (bulletinArabe){
                    if(moisTrimestre.contains(libellePeriode)) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderArabePrimaire.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderArabePrimaire.jrxml");

                    }
                }

                else {
                    if(moisTrimestre.contains(libellePeriode))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderPrimaireAutre.jrxml");


                    else
                    {myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderPrimaireAutre.jrxml");
                        System.out.println("callSpiderNobelDecompress") ;
                    }
                }

            }
            else if (niveauEnseign==5||niveauEnseign==6) {
                if(!pivoter){
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressEtanTechniqueTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanTechnique.jrxml");
                    System.out.println("callSpiderNobelDecompressEtanTechnique.jrxml") ;
                } else {
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressEtanTechniqueTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanTechnique.jrxml");
                    System.out.println("callSpiderNobelDecompressEtanTechnique.jrxml") ;
                }


            }
            else if (niveauEnseign==3) {
                if(modelePoincarre){
                    if(libellePeriode.equals("Deuxième Semestre"))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressEtanBTSPointCarreTrois.jrxml");
                    else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanBTSPointCarre.jrxml");

                } else {
                    if(!pivoter){
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressEtanTechniqueTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanBTS.jrxml");
                    }
                    else if (modelelmd){
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelLMDTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelLMD.jrxml");
                    }
                    else {
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/callSpiderNobelDecompressEtanTechniqueTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/callSpiderNobelDecompressEtanBTS.jrxml");
                    }
                }



            }

        }

        spiderBulletinDto detailsBull= new spiderBulletinDto() ;
        List<parametreDto>  dspsDto = new ArrayList<>() ;



        Connection connection = DriverManager.getConnection("jdbc:mysql://db:3306/ecoleviedbv2", USER, PASS);
        JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
        //   JasperReport compileReport = (JasperReport) JRLoader.loadObjectFromFile(UPLOAD_DIR+"BulletinBean.jasper");
        Map<String, Object> map = new HashMap<>();
        String infos= null ;
        String pdistinct= null ;
        String plogoPosi= null ;
        String psetBg= null ;
        if(distinct){
            pdistinct="1";
        } else{
            pdistinct="0";
        }

        if(infoAmoiri){
            infos="1";
        } else{
            infos="0";
        }
        if(positionLogo){
            plogoPosi="1";
        } else{
            plogoPosi="0";
        }
        if(filigranne){
            psetBg="1";
        } else{
            psetBg="0";
        }
        ecole myEcole= new ecole() ;
        myEcole=sousceecoleService.getInffosEcoleByID(idEcole);
        map.put("classe", classe.getLibelle());
        // map.put("classe", 	"6EME C");
        map.put("idEcole", idEcole);
        map.put("libelleAnnee", libelleAnnee);
        map.put("libellePeriode", libellePeriode);
        map.put("infosAmoirie", infos);
        map.put("distinctin", pdistinct);
        map.put("codeEcole", myEcole.getEcolecode());
        // map.put("codeEcole", "myEcole.getEcolecode()");
        map.put("positionLogo", plogoPosi);
        map.put("setBg", psetBg);
        map.put("offsetValue", debutImpression);
        map.put("limitValue", finImpression);


        JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
        byte[] data =JasperExportManager.exportReportToPdf(report);

        HttpHeaders headers= new HttpHeaders();
        // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
        headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Bulletin-spider"+libelleClasse+".pdf");
        return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);
    }

    @GET
    @Path("/spider-bulletin-matricule/{idEcole}/{libellePeriode}/{libelleAnnee}/{idClasse}/{matricule}/{compress}/{niveauEnseign}/{positionLogo}/{filigranne}/{infoAmoirie}/{pivoter}/{modelePoincarre}/{distinct}/{modelelmd}/{testLourd}/{bulletinArabe}/{piedPage}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public ResponseEntity<byte[]>  getDtoRapport(@PathParam("idEcole") Long idEcole ,@PathParam("libellePeriode") String libellePeriode ,
                                                 @PathParam("libelleAnnee") String libelleAnnee , @PathParam("idClasse") Long libelleClasse ,@PathParam("matricule") String matricule,@PathParam("compress") Boolean compress,
                                                 @PathParam("niveauEnseign") Long niveauEnseign,@PathParam("positionLogo") boolean positionLogo
        ,@PathParam("filigranne") boolean filigranne ,
                                                 @PathParam("infoAmoirie") boolean infoAmoiri,
                                                 @PathParam("pivoter") boolean pivoter ,
                                                 @PathParam("modelePoincarre") boolean modelePoincarre,
                                                 @PathParam("distinct") boolean distinct ,
                                                 @PathParam("modelelmd") boolean modelelmd,
                                                 @PathParam("testLourd") boolean testLourd,
                                                 @PathParam("bulletinArabe") boolean bulletinArabe,
                                                 @PathParam("piedPage") boolean piedPage
    ) throws Exception, JRException {
        try {


            InputStream myInpuStream = null;
            Classe classe= new Classe() ;
            classe = Classe.findById(libelleClasse) ;
            List<String> moisTrimestre = Arrays.asList(
    "Novembre", "Décembre", "Janvier", "Février", "Mars", "Avril"
);
            if (!compress){
                if(niveauEnseign ==2) {
                    if(testLourd){
                        if(libellePeriode.equals("Troisième Trimestre")) {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderTroisLourd.jrxml");
                        }
                        else {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderTroisLourd.jrxml");

                        }
                    }
                    else if(!pivoter){
                        if(libellePeriode.equals("Troisième Trimestre"))
                        {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpider90Trois.jrxml");

                        }

                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpider90.jrxml");
                    }

                    else if(piedPage){
                        if(libellePeriode.equals("Troisième Trimestre"))
                        {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderAvecPiedTroisV2.jrxml");

                        }

                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpider.jrxml");
                    }


                    else {
                        if(libellePeriode.equals("Troisième Trimestre"))
                        {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderTroisV2.jrxml");

                        }

                        else
                        {
                            System.out.println("Je suis au bon endroit");
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpider.jrxml");
                        }

                    }


                } else if (niveauEnseign ==4) {
                    if(!pivoter){
                        if(libellePeriode.equals("Troisième Trimestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/BulletinMaternelle.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/BulletinMaternelle.jrxml");
                    } else {
                        if(libellePeriode.equals("Troisième Trimestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/BulletinMaternelle.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/PRIMAIRE/BulletinMaternelle.jrxml");
                    }


                }
                else if (niveauEnseign ==1) {
                    if (bulletinArabe){
                    if(moisTrimestre.contains(libellePeriode)) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinArabePrimaire.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinArabePrimaire.jrxml");

                    }
                }

                else {
                    if(moisTrimestre.contains(libellePeriode))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinPrimaireAutre.jrxml");


                    else
                    {myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinPrimaireAutre.jrxml");
                        System.out.println("callSpiderNobelDecompress") ;
                    }
                }


                }
                else if (niveauEnseign==5||niveauEnseign==6) {
                    if(!pivoter){
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderTechniqueTrois.jrxml");
                        else
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderTechnique.jrxml");
                        System.out.println("BulletinNobelSpiderTechnique.jrxml") ;
                    } else {
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderTechniqueTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderTechnique.jrxml");
                        System.out.println("BulletinNobelSpiderTechnique.jrxml") ;
                    }


                }

                else if (niveauEnseign==3) {
                    if(modelePoincarre){
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderBTSPointCareeSupTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderBTSPointCareeSup.jrxml");
                    }
                    else if(modelelmd){
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderBTSLMDTrois.jrxml");
                        else  {
                            System.out.println("BulletinNobelSpiderLMD >>>>>>");
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderLMD.jrxml");

                        }

                    }

                    else {
                        if(!pivoter){
                            if(libellePeriode.equals("Deuxième Semestre"))
                                myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderBTSTrois.jrxml");
                            else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderBTS.jrxml");

                        } else {
                            if(libellePeriode.equals("Deuxième Semestre"))
                                myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderBTSTrois.jrxml");
                            else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderBTS.jrxml");
                        }

                    }


                }
            } else {
                if(niveauEnseign==2) {
                    if(testLourd){
                        if(libellePeriode.equals("Troisième Trimestre")) {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderTroisLourd.jrxml");
                        }
                        else {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderTroisLourd.jrxml");

                        }
                    }
                    else if (bulletinArabe){
                        if(libellePeriode.equals("Troisième Trimestre")) {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinArabeSpiderEtanTrois.jrxml");
                        }
                        else {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinArabeSpiderEtan.jrxml");

                        }
                    }
                    else if(!pivoter){
                        if(libellePeriode.equals("Troisième Trimestre")) {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderEtan90Trois.jrxml");

                        }

                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtan90.jrxml");

                    } else {
                        if(libellePeriode.equals("Troisième Trimestre")) {
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderEtanTroisV2.jrxml");

                        }


                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtan.jrxml");
                    }


                } else if (niveauEnseign==4||niveauEnseign==1) {
                    if (bulletinArabe){
                    if(moisTrimestre.contains(libellePeriode)) {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinArabePrimaire.jrxml");
                    }
                    else {
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinArabePrimaire.jrxml");

                    }
                }

                else {
                    if(moisTrimestre.contains(libellePeriode))
                        myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinPrimaireAutre.jrxml");


                    else
                    {myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinPrimaireAutre.jrxml");
                        System.out.println("callSpiderNobelDecompress") ;
                    }
                }


                }
                else if (niveauEnseign==5||niveauEnseign==6) {
                    if(!pivoter) {
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderEtanTechniqueTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtanTechnique.jrxml");
                    } else{
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderEtanTechniqueTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtanTechnique.jrxml");
                    }


                }
                else if (niveauEnseign==3) {
                    if(modelePoincarre) {
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderEtanBTSPointCarreTrois.jrxml");
                        else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtanBTSPointCarre.jrxml");
                    }
                    else if(modelelmd){
                        if(libellePeriode.equals("Deuxième Semestre"))
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderBTSLMDTrois.jrxml");
                        else  {
                            System.out.println("BulletinNobelSpiderLMD >>>>>>");
                            myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderLMD.jrxml");
                        }

                    } else{
                        if(!pivoter){
                            if(libellePeriode.equals("Deuxième Semestre"))
                                myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderEtanBTS.jrxml");
                            else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtanBTS.jrxml");
                        } else {
                            if(libellePeriode.equals("Deuxième Semestre"))
                                myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/TroixiemeTrimestre/BulletinNobelSpiderEtanBTS.jrxml");
                            else myInpuStream = this.getClass().getClassLoader().getResourceAsStream("etats/spider/BulletinNobelSpiderEtanBTS.jrxml");
                        }
                    }



                }
            }

            spiderBulletinDto detailsBull= new spiderBulletinDto() ;
            List<parametreDto>  dspsDto = new ArrayList<>() ;


            //bulletinSpiderMatriculeServices.bulletinInfos(idEcole ,libelleAnnee ,libellePeriode ,matricule,positionLogo,filigranne) ;

            Connection connection = DriverManager.getConnection("jdbc:mysql://db:3306/ecoleviedbv2", USER, PASS);
            JasperReport compileReport = JasperCompileManager.compileReport(myInpuStream);
            String infos= null ;
            String pdistinct= null ;
            String plogoPosi= null ;
            String psetBg= null ;
            if(distinct){
                pdistinct="1";
            } else{
                pdistinct="0";
            }

            if(infoAmoiri){
                infos="1";
            } else{
                infos="0";
            }
            if(positionLogo){
                plogoPosi="1";
            } else{
                plogoPosi="0";
            }
            if(filigranne){
                psetBg="1";
            } else{
                psetBg="0";
            }

            Map<String, Object> map = new HashMap<>();
            map.put("idEcole", idEcole);
            map.put("annee", libelleAnnee);
            map.put("libellePeriode", libellePeriode);
            map.put("matricule",matricule);
            map.put("infosAmoirie", infos);
            map.put("distinctin", pdistinct);
            //  map.put("codeEcole", myEcole.getEcolecode());
            map.put("positionLogo", plogoPosi);
            map.put("setBg", psetBg);
            map.put("classe", classe.getLibelle());
            JasperPrint report = JasperFillManager.fillReport(compileReport, map, connection);
            byte[] data =JasperExportManager.exportReportToPdf(report);

            HttpHeaders headers= new HttpHeaders();
            // headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Rapport"+myScole.getEcoleclibelle()+".docx");
            headers.set(HttpHeaders.CONTENT_DISPOSITION,"inline;filename=Bulletin-spider-"+matricule+".pdf");
            return ResponseEntity.ok().headers(headers).contentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA).body(data);


        } catch (Exception e) {
            e.printStackTrace();
            return null ;
        }


    }





    @Transactional

    public  void connect(){
        try {/*  ww  w . j a  va2s  .co m*/

            Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(DB_URL, USER, PASS);


        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
    }
    @Transactional
    public  void deleteEmr (String periode, String annee ,Long ecoleId){
        try {
            em.createQuery("delete from DetailBulletin d where  d.matiereCode in ('31') and d.bulletin.id in (select b.id from  Bulletin b where  b.libellePeriode=:periode and b.anneeLibelle=:annee and b.ecoleId=:ecoleId  )  ")

                .setParameter("periode",periode)
                .setParameter("annee",annee)
                .setParameter("ecoleId",ecoleId)
                .executeUpdate() ;
        } catch (NoResultException e){

        }
    }
    private BufferedImage correctOrientation(BufferedImage image, int orientation) {
        AffineTransform transform = new AffineTransform();
        switch (orientation) {
            case 2: // Flip X
                transform.scale(-1, 1);
                transform.translate(-image.getWidth(), 0);
                break;
            case 3: // Rotate 180
                transform.rotate(Math.PI, image.getWidth() / 2.0, image.getHeight() / 2.0);
                break;
            case 4: // Flip Y
                transform.scale(1, -1);
                transform.translate(0, -image.getHeight());
                break;
            case 5: // Rotate 90 CW and Flip X
                transform.rotate(Math.PI / 2, image.getWidth() / 2.0, image.getWidth() / 2.0);
                transform.scale(-1, 1);
                break;
            case 6: // Rotate 90 CW
                transform.rotate(Math.PI / 2, image.getWidth() / 2.0, image.getWidth() / 2.0);
                break;
            case 7: // Rotate 90 CCW and Flip X
                transform.rotate(-Math.PI / 2, image.getWidth() / 2.0, image.getWidth() / 2.0);
                transform.scale(-1, 1);
                break;
            case 8: // Rotate 90 CCW
                transform.rotate(-Math.PI / 2, image.getWidth() / 2.0, image.getWidth() / 2.0);
                break;
            default:
                return image; // Pas besoin de rotation
        }

        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = newImage.createGraphics();
        g2d.drawImage(image, transform, null);
        g2d.dispose();
        return newImage;
    }




}
