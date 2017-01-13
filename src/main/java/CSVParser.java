import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

/*
 *  TODO: Find a way to skip lines before header line. Right now header line is manually set to the first line
 */
public class CSVParser {

	public List<String[]> parse(String filename) {

		List<String[]> records = new ArrayList<String[]>();
		// tab is delimiter, double quotes as quote
		try (CSVReader reader = new CSVReader(new FileReader(filename), '\t' , '"' )) {
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
