import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Driver {
	
	private static final Logger logger = LogManager.getLogger(Driver.class);
	
	public static void main(String[] args) {
		
		String datadir; // Data set directory, TODO: Move to configuration file probably
		datadir = args.length == 1 ? args[0] : "/home/eirik/Dropbox/Universitetsarbeid/Masteroppgave/datasets/workingset";
		File dir = new File(datadir);
		
		EntityParser entityParser = new EntityParser();
		List<Entity> entities = new ArrayList<Entity>();
		
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().toLowerCase().endsWith("csv")) {
				entities.addAll(entityParser.parse(f.getAbsolutePath()));
			}
		}
		
		// Load gazetteers
		GazetteerParser gazetteerParser = new GazetteerParser();
		Map<String, HashSet<String>> gazetteers = new Hashtable<String,HashSet<String>>();
		gazetteers.put("firstName", (HashSet<String>) gazetteerParser.parse("/home/eirik/Dropbox/Universitetsarbeid/Masteroppgave/datasets/gazetters/gazetteer-person-first-name-cp1252.csv"));
		gazetteers.put("lastName", (HashSet<String>) gazetteerParser.parse("/home/eirik/Dropbox/Universitetsarbeid/Masteroppgave/datasets/gazetters/gazetteer-person-last-name-cp1252.csv"));
		
		EntityResolver resolver = new ProbabilisticEntityResolver(gazetteers);
		Map<Entity, LinkedList<Entity>> resolvedEntities = resolver.resolve(entities);
		
		// dump results to file
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		EntityWriter writer = new PersonEntityWriter(String.format("/home/eirik/Dropbox/Universitetsarbeid/Masteroppgave/datasets/entities-PERSON-%s.lbat", timeStamp));
		writer.open();
		writer.write(resolvedEntities);
		writer.close();
		System.out.println(resolvedEntities.entrySet().size());
		
		System.exit(0);
	}
	
	public static <T> void printCollection(Collection<T> collection) {
		for (T t : collection) {
			System.out.println(t);
		}
	}
}