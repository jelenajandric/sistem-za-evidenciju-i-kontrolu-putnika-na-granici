package org.unibl.etf.serialization;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.model.Terminal;
import org.unibl.etf.soapservice.TerminalService;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.google.gson.Gson;

public class SerializationUtil {
	private static final String BASE_PATH = System.getProperty("user.dir");

	private static String TERMINALS_DIR;
	
	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BASE_PATH + File.separator + File.separator + "CentralRegistry" + File.separator + "resources" + File.separator + "config.properties"));
			TERMINALS_DIR = BASE_PATH + prop.getProperty("TERMINALS_DIR");
		} catch (FileNotFoundException e1) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		} catch (IOException e1) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e1.fillInStackTrace().toString());
		}
	}
	
	public boolean serializeWithGson(Terminal t) {

		Gson gson = new Gson();
		try {
			FileOutputStream out = new FileOutputStream(new File(TERMINALS_DIR + File.separator + t.getId() + "_gson.out"));
			String tekstZaUpis=gson.toJson(t);
			byte[] bytes = tekstZaUpis.getBytes(StandardCharsets.UTF_8);
;			out.write(bytes);
			out.close();
			return true;
		} catch (IOException e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	}

	
	public Terminal deserializeWithGson(int id) {
		Gson gson = new Gson();
		try {
			FileInputStream in = new FileInputStream(new File(TERMINALS_DIR + File.separator + id + "_gson.out"));
			long fileSize = new File(TERMINALS_DIR + "\\" + id + "_gson.out").length();
            byte[] allBytes = new byte[(int) fileSize];
            in.read(allBytes);
            String s=new String(allBytes);
			Terminal t = gson.fromJson(s, Terminal.class);
			in.close();
			return t;
		} catch (IOException e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return new Terminal();
	}
	
	// Kryo
	public boolean serializeWithKryo(Terminal t) {
		try {
			Kryo kryo = new Kryo();
			kryo.register(Terminal.class);
			Output out = new Output(new FileOutputStream(new File(TERMINALS_DIR + File.separator + t.getId() + "_kryo.out")));
			kryo.writeClassAndObject(out, t);
			out.close();
			return true;
		} catch (Exception e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	}

	public Terminal deserializeWithKryo(int id) {
		Kryo kryo = new Kryo();
		kryo.register(Terminal.class);
		try {
			Input in = new Input(new FileInputStream(new File(TERMINALS_DIR+ File.separator + id + "_kryo.out")));
			Terminal t = (Terminal) kryo.readClassAndObject(in);
			in.close();
			return t;
		} catch (Exception e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return new Terminal();
	}
	
	
	// Java
	public boolean serializeWithJava(Terminal t) {
		try {
			FileOutputStream fileOut = new FileOutputStream(TERMINALS_DIR + File.separator + t.getId() + "_java.out");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(t);
			out.close();
			return true;
		} catch (Exception e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	}

	public Terminal deserializeWithJava(int id) {
		try {
			FileInputStream fileIn = new FileInputStream(TERMINALS_DIR + File.separator + id + "_java.out");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Terminal t = (Terminal) in.readObject();
			in.close();
			return t;
		} catch (Exception e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return new Terminal();
	}
	
	// XML
	public boolean serializeWithXML(Terminal t) {
		try {
			XMLEncoder encoder = new XMLEncoder(new FileOutputStream(new File(TERMINALS_DIR+File.separator + t.getId() + "_xml.out")));
			encoder.writeObject(t);
			encoder.close();
			return true;
		} catch (Exception e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return false;
	}

	public Terminal deserializeWithXML(int id) {
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream(new File(TERMINALS_DIR + File.separator + id + "_xml.out")));
			Terminal t = (Terminal) decoder.readObject();
			decoder.close();
			return t;
		} catch (Exception e) {
			Logger.getLogger(TerminalService.class.getName()).log(Level.WARNING, e.fillInStackTrace().toString());
		}
		return new Terminal();
	}
}
