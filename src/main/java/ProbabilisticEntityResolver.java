import java.util.ArrayList;
import java.util.List;

public class ProbabilisticEntityResolver implements EntityResolver {
	
	// TODO: Actual matching
	private boolean match(Entity x, Entity y) {
		if (x == null) return x == y;
		if (y == null) return false;
		
		boolean returnValue =
				!x.getId().isEmpty() &&
				x.getId().toLowerCase().equals(y.getId().toLowerCase());
		if (!x.getFullName().isEmpty() && !y.getFullName().isEmpty())
			returnValue = returnValue && x.getFullName().toLowerCase().equals(y.getFullName());
		return returnValue;
		
	}
	
	// TODO: Actual merging
	private Entity merge(Entity x, Entity y) {
		return x;
	}
	
	private List<Entity> RSwoosh(List<Entity> d2) {
		List<Entity> er = new ArrayList<Entity>();
		List<Entity> d = new ArrayList<Entity>(d2);
		
		while (!d.isEmpty()) {
			Entity currentRecord = d.iterator().next();
			d.remove(currentRecord);
			Entity buddy = null;
			for (Entity r : er) {
				if (match(currentRecord, r)) {
					buddy = r;
					break;
				}
			}
			if (buddy == null) {
				er.add(currentRecord);
			} else {
				Entity merged = merge(currentRecord, buddy);
				er.remove(buddy);
				d.add(merged);
			}
		}
		return er;
	}
	
	// Generic R-Swoosh algorithm 
	public List<Entity> resolve(List<Entity> entities) {
		return RSwoosh(entities);
		
		/*for (int i = 0; i < entities.size(); i++) {
			Entity x = entities.get(i);
			for (int j = 0; j < entities.size(); j++) {
				Entity y = entities.get(j);
				if (match(x, y)) {
					entities.add(merge(x, y));
					entities.remove(x);
					entities.remove(y);
				}
			}
		}
		return entities;*/
	}
	
}
