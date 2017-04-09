package no.uis.main;

import java.util.List;
import java.util.Map;
import java.util.LinkedList;

public interface EntityResolver {
	
	public Map<Entity, LinkedList<Entity>> resolve(List<Entity> entities);
	
}
