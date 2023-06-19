package org.unibl.etf.fileserver.rest;

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

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/dokumentipotjernice")
public class APIService {
	private static final String BASE_PATH = System.getProperty("user.dir");

	
	public static Handler handler;
	static {
		try {
			java.nio.file.Path p = Paths.get("logs/fileservice.log");
			if (!Files.exists(p.getParent())) {
	            Files.createDirectory(p.getParent());
	         }
			handler = new FileHandler(p.toString());
			Logger.getLogger(APIService.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String PATH_TO_FILES_DIR;
	private static String OUTPUT_ZIP_FILE;
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "FajlServer" + File.separator  + "resources" + File.separator + "config.properties"));
			PATH_TO_FILES_DIR = BASE_PATH + File.separator + "FajlServer" + prop.getProperty("PATH_TO_FILES_DIR");
			OUTPUT_ZIP_FILE = BASE_PATH + File.separator + "FajlServer" + prop.getProperty("OUTPUT_ZIP_FILE1");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(APIService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(APIService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getFilesPersons() {
		FileService.isprazniListu();
		FileService.generateFileListAll(new File(PATH_TO_FILES_DIR));
	    FileService.zipItAll(OUTPUT_ZIP_FILE+"zipp"+".zip");
		
		
		File file = new File(OUTPUT_ZIP_FILE+"zipp"+".zip"); 
		  return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
		      .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"" )
		      .build();
	}
	
	

}
