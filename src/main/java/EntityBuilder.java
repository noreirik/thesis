public class EntityBuilder {
	
	private String fullName, firstName, lastName, id, jobTitle;
	
	public EntityBuilder() { }
	public EntityBuilder fullName(String fullName)	{ 
		this.fullName = fullName;
		return this;
	}
	public EntityBuilder firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	public EntityBuilder lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	public EntityBuilder id(String id) {
		this.id = id;
		return this;
	}
	public EntityBuilder jobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
		return this;
	}
	public Entity build() { 
		Entity e = new Entity();
		e.setFirstName(firstName);
		e.setFullName(fullName);
		e.setLastName(lastName);
		e.setId(id);
		e.setJobTitle(jobTitle);
		return e;
	}
}
