package no.uis.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityParser {

	private static final Logger logger = LogManager.getLogger(EntityParser.class);
	
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
	
	private String format(String s) {
		return s.toLowerCase().trim();
	}
	
	private Entity createEntity(String[] record) {
		String id, name, firstName, lastName, jobTitle;
		id = name = firstName = lastName = jobTitle = "";
		if (idIdx != -1) {
			id = format(record[idIdx]);
		}
		if (nameIdx != -1) {
			name = format(record[nameIdx]);
		}
		if (lastNameIdx != -1) {
			lastName = format(record[lastNameIdx]);
		}
		if (firstNameIdx != -1) {
			firstName = format(record[firstNameIdx]);
		}
		if (jobTitleIdx != -1) {
			jobTitle = format(record[jobTitleIdx]);
		}
		
		return new	EntityBuilder()
					.id(id)
					.fullName(name)
					.firstName(firstName)
					.lastName(lastName)
					.jobTitle(jobTitle)
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
