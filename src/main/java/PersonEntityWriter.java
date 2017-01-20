import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PersonEntityWriter extends EntityWriter {
		
	public PersonEntityWriter(String fileName) {
		super(fileName);
	}
	
	@Override
	public void open() {
		this.output.add("# Entities of type PERSON\n");
		super.open();
	}
	
	// an entity and the entities from which it was derived
	private void write(Map.Entry<Entity,LinkedList<Entity>> entity) {
		StringBuilder sb = new StringBuilder();
		write(entity.getKey());
		
		if (!entity.getValue().isEmpty()) {
			String mergedString = this.output.get(this.output.size()-1);
			this.output.remove(mergedString);
			sb.append(mergedString.substring(0, mergedString.length()-1)); // remove newline
			sb.append(" FROM ");
			
			for (int i = 0; i < entity.getValue().size(); i++) {
				sb.append(entity.getValue().get(i).toString());
				if (i == entity.getValue().size()-1) ;
				else sb.append(" AND ");
			}
			
			sb.append("\n");
			this.output.add(sb.toString());
		}
	}

	@Override
	public void write(Entity entity) {
		String firstName = ""; String lastName = "";
		boolean defined = false;
		
		if (!entity.getFirstName().isEmpty()) {
			firstName = entity.getFirstName();
			defined = true;
		}
		if (!entity.getLastName().isEmpty()) {
			lastName = entity.getLastName();
			defined = true;
		}
		String s = "";
		if (defined) {
			// create_entity PERSON PERSON_FIRST_NAME Gunilla "Gunilla Andreasson" PERSON_LAST_NAME "Andreasson Steen" Steen
			s = String.format("create_entity PERSON PERSON_FIRST_NAME " +
									"%s PERSON_LAST_NAME %s\n", firstName, lastName);
		} else {
			s = String.format("create entity PERSON PERSON_NAME %s\n", entity.getFullName());
			defined = false;
		}
		
		this.output.add(s);
	}
	
	@Override
	public void close() {
		this.output.add("\n");
		this.output.add("# final clean up to merge any duplicates\n");
		this.output.add("cleanup_entities");
		this.output.add("\n");
		super.close();
	}

	@Override
	public void write(Map<Entity, LinkedList<Entity>> entities) {
		for (Map.Entry<Entity, LinkedList<Entity>> resolvedEntity : entities.entrySet()) {
			write(resolvedEntity);
		}
	}
	
	
	
}