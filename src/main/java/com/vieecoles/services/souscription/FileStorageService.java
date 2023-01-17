package com.vieecoles.services.souscription;


import com.vieecoles.dao.entities.operations.sous_attent_personn;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;




@ApplicationScoped
public class FileStorageService  {
    ResourceLoader resourceLoader ;
    private  Path fileStorageLocation ;
    private String fullDirName = null;
    private String baseDirName =null;


    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    public FileStorageService( ) {
       // baseDirName=fileStorageProperties.getUploadDir();
        setDirName("D:\\BrouillonsReactJS");
    }


    public void setDirName(String fileDirName) {

        if(fullDirName != null)
            this.fullDirName =  baseDirName + "/" + fileDirName;
        else
            this.fullDirName =  fileDirName;

        setFileStorageLocation();
    }

    public void setDirName() {
        this.fullDirName =  baseDirName ;
        setFileStorageLocation();
    }

    public String getDirName() {
        return this.fullDirName;
    }

    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }

    public void setFileStorageLocation() {
        //this.fileStorageLocation = fileStorageLocation;
        this.fileStorageLocation = Paths.get(getDirName())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public boolean deleteRessourceFile(String fileName, String folder) {
        try {
            this.setDirName(folder);
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = resourceLoader.getResource(filePath.toAbsolutePath().toString());
                if(resource.exists()) {
                return  resource.getFile().delete();
            } else {
                //appLogService.save(new AppLog("Erreur lors de la suppression d'une photo ", fileName  + " fichier introuvable", fileName  + " fichier introuvable", AppConstants.STATUS_APP_LOG_ERROR));
                //throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
           // appLogService.save(new AppLog("Erreur lors de la suppression d'une photo ", ex.getMessage(), ex.getStackTrace().toString(), AppConstants.STATUS_APP_LOG_ERROR));
            //throw new MyFileNotFoundException("File not found " + fileName, ex);
        } catch (IOException e) {
           // appLogService.save(new AppLog("Erreur lors de la suppression d'une photo ", e.getMessage(), e.getStackTrace().toString(), AppConstants.STATUS_APP_LOG_ERROR));
            e.printStackTrace();
        }

        return false;
    }


    public boolean renameRessourceFile(String fileName, String newFileName,  String folder) {
        try {
            this.setDirName(folder);
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Path newFilePath = this.fileStorageLocation.resolve(newFileName).normalize();
            Resource resource = resourceLoader.getResource(filePath.toAbsolutePath().toString());
            if(resource.exists()) {

                boolean isrenamed =  resource.getFile().renameTo(new File(newFileName));

                if(!isrenamed){
                    Files.move(filePath, newFilePath,StandardCopyOption.REPLACE_EXISTING);
                }

            } else {
                logger.info("File don't exist in folder");
              //  appLogService.save(new AppLog("Erreur lors de la renommage d'une photo ", fileName  + " fichier introuvable", fileName  + " fichier introuvable", AppConstants.STATUS_APP_LOG_ERROR));
                //throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
          //  appLogService.save(new AppLog("Erreur lors du renommage  d'une photo ", ex.getMessage(), ex.getStackTrace().toString(), AppConstants.STATUS_APP_LOG_ERROR));
            //throw new MyFileNotFoundException("File not found " + fileName, ex);
        } catch (IOException e) {
           // appLogService.save(new AppLog("Erreur lors du renommage d'une photo ", e.getMessage(), e.getStackTrace().toString(), AppConstants.STATUS_APP_LOG_ERROR));
            e.printStackTrace();
        }

        return false;
    }

   /* //@Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }*/





    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = resourceLoader.getResource(filePath.toAbsolutePath().toString());

            System.out.print("Chemin "+filePath.toAbsolutePath().toString());
            if(resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (RuntimeException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }

    public String convertBase64ToImage(String fileName, String base64, String folder) {
        //convert base64 string to binary data
        String fileNameExtension = fileName + ".png";
        byte[] data = DatatypeConverter.parseBase64Binary(base64);
        this.setDirName(folder);
        Path targetLocation = this.fileStorageLocation.resolve(fileNameExtension);
        //"C:\\Users\\Ene\\Desktop\\test_image." + extension;

        File file = new File(targetLocation.toString());
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
         //   appLogService.save(new AppLog("Erreur lors de la convertion de la photo de base64 en png ", e.getMessage(), e.getStackTrace().toString(), AppConstants.STATUS_APP_LOG_ERROR));
            fileNameExtension = base64;
            e.printStackTrace();
        }

        return fileNameExtension;
    }

}
