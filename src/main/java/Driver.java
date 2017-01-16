import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Driver {
	
	public static void main(String[] args) {
		
		String datadir; // Data set directory, TODO: Move to configuration file probably
		datadir = args.length == 1 ? args[0] : "/home/eirik/Dropbox/Universitetsarbeid/thesis/datasets/workingset";
		File dir = new File(datadir);
		
		EntityParser parser = new EntityParser();
		List<Entity> entities = new ArrayList<Entity>();
		
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().toLowerCase().endsWith("csv")) {
				entities.addAll(parser.parse(f.getAbsolutePath()));
			}
		}
		
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
}