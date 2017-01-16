public class PersonEntityWriter extends EntityWriter {
		
	public PersonEntityWriter(String fileName) {
		super(fileName);
	}
	
	@Override
	public void open() {
		this.output.add("# Entities of type PERSON\n");
		super.open();
	}

	// only writes entities where first name or last name is defined for now
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
		}
		if (defined) {
			// create_entity PERSON PERSON_FIRST_NAME Gunilla "Gunilla Andreasson" PERSON_LAST_NAME "Andreasson Steen" Steen
			String s = String.format("create_entity PERSON PERSON_FIRST_NAME " +
									"%s PERSON_LAST_NAME %s\n", firstName, lastName);
			this.output.add(s);
		}
	}
	
	@Override
	public void close() {
		this.output.add("# final clean up to merge any duplicates\n");
		this.output.add("cleanup_entities");
		this.output.add("\n");
		super.close();
	}
	
}