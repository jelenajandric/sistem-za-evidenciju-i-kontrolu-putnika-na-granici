package org.unibl.etf.fileserver.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class FileService {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String PATH_TO_FILES_DIR;
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator  + "FajlServer" + File.separator + "resources" + File.separator + "config.properties"));
			PATH_TO_FILES_DIR = BASE_PATH +File.separator +  "FajlServer" + prop.getProperty("PATH_TO_FILES_DIR");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(APIService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(APIService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	private static List <String> fileList=new ArrayList < String > ();
		  

    public static void zipItAll(String zipFile) {
    	File file1=new File(zipFile);
    	if(file1.exists())  {
    		file1.delete();
    	}
        byte[] buffer = new byte[1024];
        String source = new File(PATH_TO_FILES_DIR).getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            //System.out.println("Output to Zip : " + zipFile);
            FileInputStream in = null;
            for (String file: fileList) {
                //System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(source +File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(PATH_TO_FILES_DIR +File.separator+file);
                    int len;
                    while ((len = in .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                } catch (Exception e) {
        			Logger.getLogger(APIService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
				}
                finally {
                    in.close();
                }
            }

            zos.closeEntry();
        } catch (IOException ex) {
			Logger.getLogger(APIService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
    			Logger.getLogger(APIService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
    }
    
    public static void generateFileListAll(File node) {
        if (node.isFile()) {
            fileList.add(generateZipEntryAll(node.toString()));
        }

        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename: subNote) {
                generateFileListAll(new File(node, filename));
            }
        }
    }

    
    private static String generateZipEntryAll(String file) {
        return file.substring((PATH_TO_FILES_DIR).length(), file.length());

    }
    
    public static void isprazniListu() { 
    	fileList.clear();
    }
	
}
