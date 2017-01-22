import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opencsv.CSVReader;

public class ERCSVReader {

	private static final Logger logger = LogManager.getLogger(ERCSVReader.class);
	
	Character separator, quoteChar;
	
	public ERCSVReader() {
		separator = ','; // default to comma
		quoteChar = '"'; // default to double quotes
	}
	
	public ERCSVReader(Character separator, Character quoteChar) {
		this.separator = separator;
		this.quoteChar = quoteChar;
	}
	
	public List<String[]> read(String filename) {
		
		List<String[]> records = new ArrayList<String[]>();
		// tab is delimiter, double quotes as quote
		try (CSVReader reader = new CSVReader(new FileReader(filename), separator , quoteChar )) {
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				if (nextLine != null) records.add(nextLine);
			}
		} catch (FileNotFoundException fileEx) {
			// TODO: Logging
			fileEx.printStackTrace();
		} catch (IOException ioEx) {
			// TODO: LOgging
			ioEx.printStackTrace();
		}
		return records;
	}
}
