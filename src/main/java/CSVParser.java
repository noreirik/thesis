import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.james.mime4j.parser.ContentHandler;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ToXMLContentHandler;
import org.xml.sax.SAXException;

public class CSVParser {

	public List<String[]> parse(String fileName)
	{
	    AutoDetectParser parser = new AutoDetectParser();
	    BodyContentHandler handler = new BodyContentHandler();
	    Metadata metadata = new Metadata();
	    
	    try {
	    	InputStream stream = new FileInputStream(fileName);
	    	parser.parse(stream, handler, metadata);
	    	stream.close();
	    } catch (IOException ioEx) {
	    	// Document stream can not be read
	    	// TODO: Logging
	    } catch (TikaException tikaEx){
	    	// stream can be read but not parsed - corrupt document?
	    	// TODO: Logging
	    } catch (SAXException e) {
			// ContentHandler failed
	    	// TODO: Logging
		}
	    return split(handler.toString());
	}
	
	// Split string into list of records. A record is an array of strings.
	private List<String[]> split(String input) {
	    String[] lines = input.toString().split("\n");
	    List<String[]> records = new ArrayList<String[]>();
	    for (String s : lines) { records.add(s.split("\t")); }
	    return records;
	}
}
