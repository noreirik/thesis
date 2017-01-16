import java.util.ArrayList;
import java.util.List;

public class EntityResolver {
	
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
	
	// Generic R-Swoosh algorithm 
	public List<Entity> resolve(List<Entity> d2) {
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
	
}
