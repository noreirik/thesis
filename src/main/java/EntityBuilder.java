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
		if (id == null) 		id = "";
		if (fullName == null)	fullName = "";
		if (firstName == null)	firstName = "";
		if (lastName == null)	lastName = "";
		if (jobTitle == null)	jobTitle = "";
		
		return new Entity(id, fullName, firstName, lastName, jobTitle);
	}
}
