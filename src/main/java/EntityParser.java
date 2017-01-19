import java.util.ArrayList;
import java.util.List;

public class EntityParser {

	private ERCSVReader reader;
	
	public EntityParser() {
		reader = new ERCSVReader('\t','"');
	}
	
	public List<Entity> parse(String filename) {
		List<String[]> records = reader.read(filename);
		List<Entity> entities = retrieveRecords(records);
		return entities;
	}
	
	private int idIdx, nameIdx, lastNameIdx, firstNameIdx,  jobTitleIdx;
	
	// TODO: Normalise entries
	private List<Entity> retrieveRecords(List<String[]> records) {
		List<Entity> entities = new ArrayList<Entity>();
		
		String[] header = records.get(0);
		updateIndices(header);
		
		for (int i = 1; i < records.size(); i++) {
			String[] record = records.get(i);
			entities.add(createEntity(record));
		}
		return entities;
	}
	
	private String quote(String s) {
		if (s.contains(" ") && !s.contains("\"")) return String.format("\"%s\"", s);
		else return s;
	}
	
	private Entity createEntity(String[] record) {
		String id, name, firstName, lastName, jobTitle;
		id = name = firstName = lastName = jobTitle = "";
		if (idIdx != -1) {
			id = record[idIdx];
		}
		if (nameIdx != -1) {
			name = record[nameIdx];
		}
		if (lastNameIdx != -1) {
			lastName = record[lastNameIdx];
		}
		if (firstNameIdx != -1) {
			firstName = record[firstNameIdx];
		}
		if (jobTitleIdx != -1) {
			jobTitle = record[jobTitleIdx];
		}
		
		return new	EntityBuilder()
					.id(id.toLowerCase())
					.fullName(name.toLowerCase())
					.firstName(firstName.toLowerCase())
					.lastName(lastName.toLowerCase())
					.jobTitle(jobTitle.toLowerCase())
					.build();
	}
	
	private void updateIndices(String[] header) {
		idIdx = nameIdx = lastNameIdx = firstNameIdx = jobTitleIdx = -1;
		for (int idx = 0; idx < header.length; idx++) {
			switch(header[idx].toLowerCase()) {
				case "last name":  	lastNameIdx = idx; break;
				case "first name":  firstNameIdx = idx; break;
				case "name": 		nameIdx = idx; break;
				case "title": 		jobTitleIdx = idx; break;
				case "id": 			idIdx = idx; break;
				default: break;
			}
		}
	}
}
