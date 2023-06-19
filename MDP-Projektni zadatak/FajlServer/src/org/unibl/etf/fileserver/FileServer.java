package org.unibl.etf.fileserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileServer implements FileServerInterface {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String PATH_TO_FILES_DIR;
	private static String SERVER_POLICY_FILE_PATH;
	private static String FILE_SERVER_NAME;
	private static String FILE_SERVER_PORT;
	
	public static Handler handler;
	static {
		try {
			Path p = Paths.get("logs/fileserver.log");
			if (!Files.exists(p.getParent())) {
	            Files.createDirectory(p.getParent());
	         }
			handler = new FileHandler(p.toString());
			Logger.getLogger(FileServer.class.getName()).addHandler(handler);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
			PATH_TO_FILES_DIR = BASE_PATH + prop.getProperty("PATH_TO_FILES_DIR");
			SERVER_POLICY_FILE_PATH = BASE_PATH + prop.getProperty("SERVER_POLICY_FILE_PATH");
			FILE_SERVER_NAME = prop.getProperty("FILE_SERVER_NAME");
			FILE_SERVER_PORT = prop.getProperty("FILE_SERVER_PORT");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(FileServer.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(FileServer.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public void saveFile(Integer[] readedBytes, int jmb, String fileName) throws RemoteException {
		try {
			File file = new File(PATH_TO_FILES_DIR + jmb);
			if(!file.exists()) {
				file.mkdir();
			}
			OutputStream outputStream = new FileOutputStream(file+File.separator + fileName);
			for (int i : readedBytes) {
				outputStream.write(i);
			}
			outputStream.close();  
		} catch (IOException e) {		
			Logger.getLogger(FileServer.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
	}
	
	public static void main(String args[]) {
//		Properties prop = new Properties();
//		try {
//			prop.load(new FileInputStream(BASE_PATH + File.separator + "resources" + File.separator + "config.properties"));
//			PATH_TO_FILES_DIR = BASE_PATH + prop.getProperty("PATH_TO_FILES_DIR");
//			SERVER_POLICY_FILE_PATH = BASE_PATH + prop.getProperty("SERVER_POLICY_FILE_PATH");
//			FILE_SERVER_NAME = prop.getProperty("FILE_SERVER_NAME");
//			FILE_SERVER_PORT = prop.getProperty("FILE_SERVER_PORT");
//		} catch (FileNotFoundException e1) {
//			Logger.getLogger(FileServer.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
//		} catch (IOException e1) {
//			Logger.getLogger(FileServer.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
//		}
		System.setProperty("java.security.policy", SERVER_POLICY_FILE_PATH);
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			FileServer server = new FileServer();
			FileServerInterface stub = (FileServerInterface) UnicastRemoteObject.exportObject(server,0);
			Registry registry = LocateRegistry.createRegistry(Integer.parseInt(FILE_SERVER_PORT));
			registry.rebind(FILE_SERVER_NAME, stub);
			System.out.println("Server FajlServeer started.");
		} catch (Exception ex) {
			Logger.getLogger(FileServer.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
		}
	}
}
