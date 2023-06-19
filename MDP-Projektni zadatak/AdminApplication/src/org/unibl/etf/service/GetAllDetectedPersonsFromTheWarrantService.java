package org.unibl.etf.service;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.unibl.etf.main.Main;
import org.unibl.etf.model.Person;

public class GetAllDetectedPersonsFromTheWarrantService {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String BASE_URL_CR;
	private static String DEST_DIR_CR;
	private static String DEST_DIR_CR_PARENT;
	private static String DEST_DIR_FS;
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			BASE_URL_CR = prop.getProperty("BASE_URL_CR");
			DEST_DIR_CR = BASE_PATH + prop.getProperty("DEST_DIR_CR");
			DEST_DIR_CR_PARENT = BASE_PATH + prop.getProperty("DEST_DIR_CR_PARENT");
			DEST_DIR_FS = BASE_PATH + prop.getProperty("DEST_DIR_FS");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}

	public ArrayList<Person> getAndSaveDataAboutDetectedPersonsFromTheWarrantFromCentralRegistry() {
		ArrayList<Person> persons = new ArrayList<Person>();
		try {
			InputStream is = new URL(BASE_URL_CR).openStream();
			unzip(is, DEST_DIR_CR);
			persons = readFiles();
		} catch (Exception e) {
			Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return persons;	
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
	
	private static ArrayList<Person> readFiles(){
		ArrayList<Person> persons=new ArrayList<Person>();
		File dir=new File(DEST_DIR_CR);
		File filesList[] = dir.listFiles();
		for(File f:filesList) {
			FileInputStream isr;
			try {
				isr = new FileInputStream(f);
			
				XMLDecoder decoder = new XMLDecoder(isr);
				Person p = (Person) decoder.readObject();
				persons.add(p);
				decoder.close();
			} catch (FileNotFoundException e) {
				Logger.getLogger(Main.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
			}
		}
		return persons;
	}
}
