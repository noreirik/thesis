import java.io.File;
import java.util.ArrayList;
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
		
		EntityResolver resolver = new EntityResolver();
		List<Entity> resolvedEntities = resolver.resolve(entities);
		
		System.exit(0);
	}
	
	private static void printRecords(List<Entity> l) {
		for (int i = 0; i < l.size(); i++) {
			System.out.println(l.get(i));
		}
	}
}