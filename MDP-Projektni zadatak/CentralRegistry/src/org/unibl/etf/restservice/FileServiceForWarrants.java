package org.unibl.etf.restservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileServiceForWarrants {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static List <String> fileList = new ArrayList <String>();
	private static String OSOBE_SA_POTJERNICE_DIR;
		
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + File.separator + "CentralRegistry" + File.separator + "resources" + File.separator + "config.properties"));
			OSOBE_SA_POTJERNICE_DIR = BASE_PATH + prop.getProperty("OSOBE_SA_POTJERNICE");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(WarrantsApiService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(WarrantsApiService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	 
	public static void zipIt(String zipFile) {
    	File file1 = new File(zipFile);
    	if(file1.exists()) {
    		file1.delete();
    	}
        byte[] buffer = new byte[1024];
        File f = new File(OSOBE_SA_POTJERNICE_DIR);
        if(!f.exists()) {
        	f.mkdir();
        }
        String source = f.getName();
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
            fos = new FileOutputStream(zipFile);
            zos = new ZipOutputStream(fos);

            //System.out.println("Output to Zip : " + zipFile);
            FileInputStream in = null;
            for (String file: fileList) {
                //System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(source + File.separator + file);
                zos.putNextEntry(ze);
                try {
                    in = new FileInputStream(OSOBE_SA_POTJERNICE_DIR + File.separator + file);
                    int len; 
                    while ((len = in .read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
                catch (Exception e) {
        			Logger.getLogger(WarrantsApiService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());					
                } finally {
                    in.close();
                }
            }
            zos.closeEntry();
        } catch (IOException ex) {
			Logger.getLogger(WarrantsApiService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        } finally {
            try {
                zos.close();
            } catch (IOException e) {
				Logger.getLogger(WarrantsApiService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
            }
        }
	 }

    public static void generateFileList(File node) {
        if (node.isFile()) {
            fileList.add(generateZipEntry(node.toString()));
        }
        if (node.isDirectory()) {
            String[] subNote = node.list();
            for (String filename: subNote) {
                generateFileList(new File(node, filename));
            }
        }
    }

    private static String generateZipEntry(String file) {
		String string = file.substring(OSOBE_SA_POTJERNICE_DIR.length(), file.length());
        return string;
    }
    
    public static void isprazniListu() {
    	fileList.clear();
    }

}
