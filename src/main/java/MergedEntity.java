import java.util.LinkedHashSet;

/**
 * 
 * @author Eirik S. Norheim
 * 
 */
public class MergedEntity {
	
	private Entity mergedEntity;
	// entities from which entity is derived. LinkedHashSet because want to retain temporal ordering.
	private LinkedHashSet<Entity> derivedEntities; 
	
	public MergedEntity(Entity e) {
		this.mergedEntity = e;
	}
	
	@Override
	public String toString() {
		return mergedEntity.toString();
	}
	
	//
	public void addDerivedEntity(Entity e) {
		derivedEntities.add(e);
	}

	public Entity getMergedEntity() {
		return mergedEntity;
	}
	
	// determine whether the merged entity was partial derived from some entity
	public boolean isDerivedFrom(Entity e) {
		return derivedEntities.contains(e);
	}
	
}
