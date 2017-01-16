import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class EntityWriter {
	
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
