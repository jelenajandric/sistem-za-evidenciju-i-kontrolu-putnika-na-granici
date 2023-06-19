package org.unibl.etf.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.unibl.etf.main.Main;

public class SavePersonsDocsService {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String DEST_DIR_CR_PARENT;
	private static String BASE_URL_FS;
	private static String DEST_DIR_FS;
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			BASE_URL_FS = prop.getProperty("BASE_URL_FS");
			DEST_DIR_CR_PARENT = BASE_PATH + prop.getProperty("DEST_DIR_CR_PARENT");
			DEST_DIR_FS = BASE_PATH + prop.getProperty("DEST_DIR_FS");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public boolean savePersonsDocsFromFileServer() {
		try {
			InputStream is = new URL(BASE_URL_FS).openStream();
			unzip(is, DEST_DIR_FS);
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			return false;
		}
		return true;
	}
	
	private static void unzip(InputStream is, String destDir) {
        File dir = new File(destDir);
        if(dir.exists())
        	dir.delete();
        if(!dir.exists()) dir.mkdirs();

        byte[] buffer = new byte[1024];
        try {
            ZipInputStream zis = new ZipInputStream(is);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                String newFileFilename = DEST_DIR_CR_PARENT + File.separator + fileName;
                if(destDir.equals(DEST_DIR_FS)) {
                	newFileFilename = DEST_DIR_FS + File.separator + fileName;
                }
                File newFile = new File(newFileFilename);
                //System.out.println("Unzipping to "+newFile.getAbsolutePath());
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
        }
        
    }
}
