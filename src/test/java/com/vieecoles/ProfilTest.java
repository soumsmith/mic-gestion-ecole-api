package com.vieecoles;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
  
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import io.quarkus.test.junit.QuarkusTest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.Response;
import org.junit.jupiter.api.Test; 
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
  import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.util.Base64;
import javax.crypto.KeyGenerator;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import java.io.File;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
/**
* This is an example on how to get the x/y coordinates and size of each character in PDF
*/
@ApplicationScoped
@QuarkusTest
public class ProfilTest  { 
      @Inject
    EntityManager em;
 static final byte k[] = "soumBulletin1".getBytes();
static SecretKeySpec myDesKey = new SecretKeySpec(k,"DES");
static Cipher desCipher;
    SecretKey GLokey;
    IvParameterSpec Gloiv ;
String CodeMessage1 ;

File folder = new File("C:\\Users\\SOUM\\Desktop\\IMAGE OTP\\MonFichier");
        File[] listOFiles= folder.listFiles();


private ProfilTest() {

	}



// @Test 
    public void getNomFichierFromDossier() throws NoSuchPaddingException, NoSuchAlgorithmException,
    InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException {
       // codeMessage();
        
    /*    for (int i = 0 ;i<listOFiles.length; i++) {
            if(listOFiles[i].isFile()) {
                String FileName= listOFiles[i].getName();
     //String CodeMessage1= codeMessage(listOFiles[i].getName());
                    if(FileName!=null ) {
                        CodeMessage1= codeMessage(FileName.trim());
                        System.out.println(FileName);
                    }
      
    // String CodeMessage2 = decodeMessage(listOFiles[i].getName());
            //System.out.println("File"+listOFiles[i].getName()); 
           
               
           
         // String key =  secretToString(GLokey);
          
          //  System.out.println(key);
               // System.out.println(Gloiv);
            }
            else if (listOFiles[i].isDirectory())   {
            
                System.out.println("Directory"+listOFiles[i].getName()); 
            }
             
        }*/
    }
    
    
    /*@Test
    public void renameAllFiles() throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IOException{
              String path_to_folder = "D:\\API QUARKUS\\MES-BULLETIN\\MonFichier";
                String path_to_folder2="D:\\API QUARKUS\\MES-BULLETIN\\MonFichier\\MonFichier2";
                
                Integer mAnnee,mMois;
                String mMatricule,sSecretKey;
                
      File my_folder = new File(path_to_folder);
      File[] array_file = my_folder.listFiles();
      for (int i = 0; i < array_file.length; i++){
         if (array_file[i].isFile()){
            File my_file = new File(path_to_folder + "\\" + array_file[i].getName());
             String FileName= listOFiles[i].getName();
            PDDocument pdd = PDDocument.load(my_file);
 
            mAnnee=2021;
            mMois=03;
            mMatricule="45844B";
            sSecretKey="4521";
            
            
            
            
        // step 2.Creating instance of AccessPermission
        // class
        AccessPermission ap = new AccessPermission();
 
        // step 3. Creating instance of
        // StandardProtectionPolicy
        StandardProtectionPolicy stpp
            = new StandardProtectionPolicy(sSecretKey, sSecretKey, ap);
 
        // step 4. Setting the length of Encryption key
        stpp.setEncryptionKeyLength(128);
 
        // step 5. Setting the permission
        stpp.setPermissions(ap);
 
        // step 6. Protecting the PDF file
        pdd.protect(stpp);
 
        // step 7. Saving and closing the the PDF Document
        pdd.save("D:\\API QUARKUS\\MES-BULLETIN\\MonFichier\\MonFichier2\\"+FileName+ ".pdf");
        pdd.close();
          
           bulletin_parameter myBull = new bulletin_parameter();
            myBull.setSecretkey(sSecretKey);
            myBull.setAnnee(mAnnee);
            myBull.setMatricule(mMatricule);
            myBull.setMois(mMois);
           
            em.persist(myBull);
        
        
         }
      }
    }*/
    
    
    
    
    
    public static String encrypt(String algorithm, String input, SecretKey key,
    IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
    InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException {
    
    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
    byte[] cipherText = cipher.doFinal(input.getBytes());
    return Base64.getEncoder()
        .encodeToString(cipherText);
} 
    
    public  String secretToString(SecretKey key){
        String stringKey = null;

        stringKey = Base64.getEncoder().encodeToString(key.getEncoded());
        return  stringKey ;
    }
    
 
    
    
    
    
      public  SecretKey StringTosecret(byte[] key){
        

        SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");
        return  originalKey ;
    }
    
    
    
    
  
   // @Test 
 public static String decrypt(String algorithm, String cipherText, SecretKey key,
    IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
    InvalidAlgorithmParameterException, InvalidKeyException,
    BadPaddingException, IllegalBlockSizeException {
    
    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(Cipher.DECRYPT_MODE, key, iv);
    byte[] plainText = cipher.doFinal(Base64.getDecoder()
        .decode(cipherText));
       
    return new String(plainText);
}
    
 public  String  codeMessage(String input)throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
    BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException{
     // String input = myInput;
    //SecretKey key = generateKey(128);
    GLokey = generateKey(128);
    //IvParameterSpec ivParameterSpec = generateIv();
     Gloiv= generateIv();
    String algorithm = "AES/CBC/PKCS5Padding";
    String cipherText = encrypt(algorithm, input, GLokey, Gloiv);
    
     

     return cipherText ;
     
 } 
 
 
 
 
 
 
 
 
 
  
  public  String decodeMessage(String cipherText)throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
    BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException{
      SecretKey key = generateKey(128);
    //SecretKey key=javax.crypto.spec.SecretKeySpec@179eb ;
    IvParameterSpec ivParameterSpec = generateIv();
    String algorithm = "AES/CBC/PKCS5Padding";
      String plainText = decrypt(algorithm, cipherText, key, ivParameterSpec);
     
      
  return plainText ;
 }
 
 
 
 
 
 
 
 
 
 
void givenString_whenEncrypt_thenSuccess(String myinput )
    throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
    BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException { 
    
    String input = myinput;
    SecretKey key = generateKey(128);
    IvParameterSpec ivParameterSpec = generateIv();
    String algorithm = "AES/CBC/PKCS5Padding";
    String cipherText = encrypt(algorithm, input, key, ivParameterSpec);
    String plainText = decrypt(algorithm, cipherText, key, ivParameterSpec);
    Assertions.assertEquals(input, plainText);
}
    
    
      public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }
    
    public static IvParameterSpec generateIv() {
         byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
      // byte[] iv = { 0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8 };
       // IvParameterSpec ivspec = new IvParameterSpec(iv);
       // return ivspec;
    } 
    
     
  /*  @Test
      public void ConvertirFichier (String FileName) throws IOException {
                      
                      // TODO Auto-generated method stub
		//final String FILENAME="C:\\Users\\SOUM\\Downloads\\Documents\\MATENIN.pdf";
                final String FILENAME= FileName;
               String BrutDuMois="",BrutImposableDuMois="",NumeroCompte;
		
		PDDocument pd = PDDocument.load(new File(FILENAME));
		
		int totalPages = pd.getNumberOfPages();
		System.out.println("Total Pages in Document: "+totalPages);
		
		ObjectExtractor oe = new ObjectExtractor(pd);
		SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
		Page page = oe.extract(1);
		
		// extract text from the table after detecting
		List<technology.tabula.Table> table = sea.extract(page);
		for(technology.tabula.Table tables: table) {
			List<List<RectangularTextContainer>> rows = tables.getRows();
                        
			
			for(int i=0; i<rows.size(); i++) {
				
				List<RectangularTextContainer> cells = rows.get(i);
				
				for(int j=0; j<cells.size(); j++) {
                                     if((i==1) && (j==0)){
                                         BrutDuMois=BrutDuMois+"|"+ cells.get(j).getText()+ "|"+"N°"+i+"Cell"+" "+j;
                                       
                                     } else if ((i==2) && (j==0)) {
                                         BrutImposableDuMois=BrutImposableDuMois+"|"+ cells.get(j).getText()+ "|"+"N°"+i+"Cell"+" "+j;
                                     
                                     }
                                    
                                    
                                     
					//System.out.print(cells.get(j).getText()+"|"+"N°"+i+"Cell"+" "+j);
				}

				//System.out.println();
                                
			}
		}
                       System.out.print(BrutDuMois);
                       System.out.print(BrutImposableDuMois);
                  }  */
      

     
      
      
}
