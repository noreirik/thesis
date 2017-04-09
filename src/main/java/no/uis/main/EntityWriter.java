package no.uis.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class EntityWriter {
	
	private static final Logger logger = LogManager.getLogger(EntityWriter.class);
	
	protected List<String> output;
	private String fileName;
	private boolean isOpen;
	
	public EntityWriter(String fileName) {
		this.fileName = fileName;
		output = new ArrayList<String>();
	}
	
	public void open() {
		isOpen = true;
	}
	
	public abstract void write(Map<Entity,LinkedList<Entity>> entities);
	public abstract void write(Entity e);
	
	public void close() {
        try {
        	File file = new File(fileName);
        	file.createNewFile();
        	FileWriter writer;
			writer = new FileWriter(file);
			
			for (String out : output) {
				writer.write(out);
			}
	        writer.flush();
	        writer.close();
		} catch (IOException ioEx) {
			// TODO: Log could not open or write to fileName
			ioEx.printStackTrace();
		} catch (SecurityException sEx) {
			// TODO: Log insufficient permissions
			sEx.printStackTrace();
		} finally {
			isOpen = false;
		}
	}
}
