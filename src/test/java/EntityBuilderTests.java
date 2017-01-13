import org.junit.Test;

public class EntityBuilderTests {

	@Test
	public void EntityBuilderSimpleTest() {
		Entity entity1 = new Entity();
		entity1.setFirstName("eirik");
		entity1.setLastName("norheim");
		entity1.setFullName("eirik norheim");
		entity1.setId("abc1234");
		entity1.setJobTitle("programmer");
		Entity entity2 = new EntityBuilder()
							.firstName("eirik")
							.lastName("norheim")
							.fullName("eirik norheim")
							.id("abc1234")
							.jobTitle("programmer")
							.build();
		boolean firstName = entity1.getFirstName().equals(entity2.getFirstName());
		boolean lastName = entity1.getLastName().equals(entity2.getLastName());
		boolean fullName = entity1.getFullName().equals(entity2.getFullName());
		boolean id = entity1.getId().equals(entity2.getId());
		boolean jobTitle = entity1.getJobTitle().equals(entity2.getJobTitle());
		
		assert(firstName && lastName && fullName && id && jobTitle);
	}

}
