public class PersonEntityWriter extends EntityWriter {
		
	public PersonEntityWriter(String fileName) {
		super(fileName);
	}
	
	@Override
	public void open() {
		this.output.add("# Entities of type PERSON\n");
		super.open();
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
		if (defined) this.output.add(s);
		else this.output.add(0, s);
	}
	
	@Override
	public void close() {
		this.output.add("\n");
		this.output.add("# final clean up to merge any duplicates\n");
		this.output.add("cleanup_entities");
		this.output.add("\n");
		super.close();
	}
	
}