package org.unibl.etf.restservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/potjernice")
public class WarrantsApiService {
	private static final String BASE_PATH = System.getProperty("user.dir");
	
	public static Handler handler;
	static {
		try {
			java.nio.file.Path p = Paths.get("logs/centralregistry_warrantsservice.log");
			if (!Files.exists(p.getParent())) {
	            Files.createDirectory(p.getParent());
	         }
			handler = new FileHandler(p.toString());
			Logger.getLogger(WarrantsApiService.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String OUTPUT_ZIP_FILE;
    private static String OSOBE_SA_POTJERNICE_DIR; 
	public WarrantsApiService() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + File.separator + "CentralRegistry" + File.separator + "resources" + File.separator + "config.properties"));
			OSOBE_SA_POTJERNICE_DIR = BASE_PATH + prop.getProperty("OSOBE_SA_POTJERNICE");
			OUTPUT_ZIP_FILE = BASE_PATH + prop.getProperty("OUTPUT_ZIP_FILE");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(WarrantsApiService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(WarrantsApiService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	} 
	
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFile() {
		FileServiceForWarrants.isprazniListu();
		FileServiceForWarrants.generateFileList(new File(OSOBE_SA_POTJERNICE_DIR));
		FileServiceForWarrants.zipIt(OUTPUT_ZIP_FILE);
		File file = new File(OUTPUT_ZIP_FILE); // Initialize this to the File path you want to serve.
		return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
	      .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" ) //optional
	      .build();
	}
}
