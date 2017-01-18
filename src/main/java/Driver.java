import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Driver {
	
	public static void main(String[] args) {
		
		String datadir; // Data set directory, TODO: Move to configuration file probably
		datadir = args.length == 1 ? args[0] : "/home/eirik/Dropbox/Universitetsarbeid/thesis/datasets/workingset";
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
		Set<String> firstNameGazetteer = gazetteerParser.parse("/home/eirik/Dropbox/Universitetsarbeid/thesis/datasets/gazetters/gazetteer-person-first-name-cp1252.csv");
		Set<String> lastNameGazetteer = gazetteerParser.parse("/home/eirik/Dropbox/Universitetsarbeid/thesis/datasets/gazetters/gazetteer-person-last-name-cp1252.csv");
		
		EntityResolver resolver = new ProbabilisticEntityResolver();
		List<Entity> resolvedEntities = resolver.resolve(entities);
		
		// dump results to file
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		EntityWriter writer = new PersonEntityWriter(String.format("/home/eirik/Dropbox/Universitetsarbeid/thesis/datasets/entities-PERSON-%s.lbat", timeStamp));
		writer.open();
		for (Entity e : resolvedEntities) {
			writer.write(e);
		}
		writer.close();
		
		System.exit(0);
	}
	
	public static <T> void printCollection(Collection<T> collection) {
		for (T t : collection) {
			System.out.println(t);
		}
	}
}