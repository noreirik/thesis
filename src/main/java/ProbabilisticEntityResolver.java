import java.util.ArrayList;
import java.util.List;

public class ProbabilisticEntityResolver implements EntityResolver {
	
	// 
	private boolean match(Entity x, Entity y) {
		if (x == null) return x == y;
		if (y == null) return false;
		
		boolean sharesId =			!x.getId().isEmpty() &&
									x.getId().toLowerCase().equals(y.getId().toLowerCase()); 
		boolean sharesFullName =	!x.getFullName().isEmpty() &&
									x.getFullName().toLowerCase().equals(y.getFullName().toLowerCase());
		boolean sharesFirstName =	!x.getFirstName().isEmpty() &&
									x.getFirstName().toLowerCase().equals(y.getFirstName().toLowerCase());
		boolean sharesLastName =	!x.getLastName().isEmpty() &&
									x.getLastName().toLowerCase().equals(y.getLastName().toLowerCase());
		
		return sharesId || sharesFullName || (sharesFirstName && sharesLastName);
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
