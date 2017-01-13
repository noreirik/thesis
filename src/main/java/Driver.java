import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Driver {
	
	private List<String[]> records;
	
	public static void main(String[] args) {
		
		Driver driver = new Driver();
		driver.records = new ArrayList<String[]>();
		
		String datadir; // Data set directory, TODO: Move to configuration file probably
		datadir = args.length == 1 ? args[0] : "/home/eirik/Dropbox/Universitetsarbeid/thesis/datasets/workingset";
		File dir = new File(datadir);
		
		CSVParser parser = new CSVParser();
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().toLowerCase().endsWith("csv")) {
				List<String[]> currentRecords = parser.parse(f.getAbsolutePath());
				driver.insertRecords(currentRecords);
			}
		}
		
		System.exit(0);
	}
	
	private void insertRecords(List<String[]> records) {
		// TODO: Create Entity objects from strings and insert into records
		// if both firstName and lastName fields are set, use that as the best representation of a full name
	}
	
	private void printRecords() {
		for (int i = 0; i < records.size(); i++) {
			String[] record = records.get(i);
			for (int j = 0; j < record.length; j++) {
				if (!record[j].isEmpty())
					System.out.print(record[j]);
				else
					System.out.print("NULL");
				if (j != record.length-1) System.out.print(":");
			}
			System.out.println();
		}
	}
}