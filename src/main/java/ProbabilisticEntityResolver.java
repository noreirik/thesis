import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ProbabilisticEntityResolver implements EntityResolver {
	
	private Map<String,HashSet<String>> gazetteers;
	
	public ProbabilisticEntityResolver(Map<String,HashSet<String>> gazetteers) {
		this.gazetteers = gazetteers;
	}
	
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
		// if both first and last name field of Y can be found in full name of X
		boolean firstNameAndLastNameInFullNameInX =
									!x.getFullName().isEmpty() && !y.getFirstName().isEmpty() && !y.getLastName().isEmpty() &&
									x.getFullName().toLowerCase().contains(y.getFirstName().toLowerCase()) &&
									x.getFullName().toLowerCase().contains(y.getLastName().toLowerCase());
		// if both first and last name field of X can be found in full name of Y
		boolean firstNameAndLastNameInFullNameInY =
									!y.getFullName().isEmpty() && !x.getFirstName().isEmpty() && !x.getLastName().isEmpty() &&
									y.getFullName().toLowerCase().contains(x.getFirstName().toLowerCase()) &&
									y.getFullName().toLowerCase().contains(x.getLastName().toLowerCase());
		
		return	sharesId || sharesFullName || (sharesFirstName && sharesLastName) ||
				firstNameAndLastNameInFullNameInX || firstNameAndLastNameInFullNameInY;
	}
	
	// abc, afd -> abc afd
	// abc, afd eee -> abc "afd eee"
	private String mergeStrings(String s1, String s2) {
		StringBuilder sb = new StringBuilder();
		
		if (!s1.isEmpty()) {
			sb.append(s1);
		}
		if (!s2.isEmpty() && !s2.equalsIgnoreCase(s1)) {
			if (sb.toString().isEmpty()) sb.append(" ");
			sb.append(s2);
		}
		return sb.toString();
	}
	
	private Entity merge(Entity x, Entity y) {
		
		EntityBuilder z = new EntityBuilder();
		
		if (!x.getId().isEmpty()) z.id(x.getId());
		else if (!y.getId().isEmpty()) z.id(y.getId());
		
		if (!x.getFullName().isEmpty()) z.fullName(x.getFullName());
		else if (!y.getFullName().isEmpty()) z.fullName(y.getFullName());
		
		if (!x.getJobTitle().isEmpty()) z.jobTitle(x.getJobTitle());
		else if (!y.getJobTitle().isEmpty()) z.jobTitle(y.getJobTitle());
		
		z.firstName	( mergeStrings(	x.getFirstName(),	y.getFirstName	()	));
		z.lastName	( mergeStrings(	x.getLastName(),	y.getLastName	()	));

		return z.build();
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
