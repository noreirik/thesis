import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Format of CSV file is "UINTERESSANT,UINTERESSANT,Name"
 */
public class GazetteerParser {

	private static final Logger logger = LogManager.getLogger(GazetteerParser.class);
	
	private ERCSVReader reader;
	
	public GazetteerParser() {
		reader = new ERCSVReader(',','"');
	}
	
	public Set<String> parse(String fileName) {
		List<String[]> records = this.reader.read(fileName);
		Set<String> gazetteer = retrieveGazetteer(records);
		return gazetteer;
	}
	
	private Set<String> retrieveGazetteer(List<String[]> records) {
		Set<String> gazetteer = new HashSet<String>();
		for (String[] s : records) {
			gazetteer.add(s[2]); // actual content is in 3 column
		}
		return gazetteer;
	}
}