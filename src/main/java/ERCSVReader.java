import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class ERCSVReader {

	public List<String[]> read(String filename) {

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
