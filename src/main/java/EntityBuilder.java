public class EntityBuilder {
	
	private Entity entity;
	public EntityBuilder() {
		entity = new Entity();
	}
	public EntityBuilder fullName(String fullName)	{ 
		this.entity.setFullName(fullName);
		return this;
	}
	public EntityBuilder firstName(String firstName) {
		this.entity.setFirstName(firstName);
		return this;
	}
	public EntityBuilder lastName(String lastName) {
		this.entity.setLastName(lastName);
		return this;
	}
	public EntityBuilder id(String id) {
		this.entity.setId(id);
		return this;
	}
	public EntityBuilder jobTitle(String jobTitle) {
		this.entity.setJobTitle(jobTitle);
		return this;
	}
	public Entity build() { return this.entity; }
}
