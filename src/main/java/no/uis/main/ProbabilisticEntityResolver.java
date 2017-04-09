package no.uis.main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProbabilisticEntityResolver implements EntityResolver {

	private static final Logger logger = LogManager.getLogger(ProbabilisticEntityResolver.class);
	
	private Map<String,HashSet<String>> gazetteers;

	public ProbabilisticEntityResolver(Map<String,HashSet<String>> gazetteers) {
		this.gazetteers = gazetteers;
		logger.debug("ProbabilisticEntityResolver instantiated");
	}

	// 
	private boolean match(Entity x, Entity y) {
		if (x == null) return x == y;
		if (y == null) return false;

		logger.trace("Comparing {} and {}", x.toString(), y.toString());
		
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

		if (sharesId) logger.debug("Same id of {}\"", x.getId());
		if (sharesFullName) logger.debug("Same name \"{}\"", x.getFullName());
		if (sharesFirstName) logger.debug("Same first name of \"{}\"", x.getFirstName());
		if (sharesLastName) logger.debug("Same last name of \"{}\"", x.getLastName());
		if (firstNameAndLastNameInFullNameInX) logger.debug("\"{}\" and \"{}\" can be found in \"{}\"", y.getFirstName(), y.getLastName(), x.getFullName());
		if (firstNameAndLastNameInFullNameInY) logger.debug("\"{}\" and \"{}\" can be found in \"{}\"", x.getFirstName(), x.getLastName(), y.getFullName());
		
		boolean isMatch =	sharesId || sharesFullName || (sharesFirstName && sharesLastName) ||
								firstNameAndLastNameInFullNameInX || firstNameAndLastNameInFullNameInY; 
		
		if (isMatch) logger.debug("MATCH: {} and {}", x, y);
		else logger.trace("NO MATCH: {} and {}", x, y);
		
		return isMatch;
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

		Entity e = z.build();
		logger.debug("MERGE: {} from {} and {}", e, x, y);
		
		return z.build();
	}

	private Map<Entity,LinkedList<Entity>> RSwoosh(List<Entity> unresolvedEntities) {

		Map<Entity, LinkedList<Entity>> er = new HashMap<Entity,LinkedList<Entity>>();
		Map<Entity, LinkedList<Entity>> d = new HashMap<Entity, LinkedList<Entity>>();

		for (Entity e : unresolvedEntities) { d.put(e, new LinkedList<Entity>()); }

		Iterator<Map.Entry<Entity, LinkedList<Entity>>> x_itr = d.entrySet().iterator();
		Iterator<Map.Entry<Entity, LinkedList<Entity>>> y_itr = er.entrySet().iterator();		

		while (x_itr.hasNext()) {

			Map.Entry<Entity, LinkedList<Entity>> x = x_itr.next();
			Map.Entry<Entity, LinkedList<Entity>> y = null;
			//Entity x = d.keySet().iterator().next();

			while (y_itr.hasNext()) {
				Map.Entry<Entity, LinkedList<Entity>> e = y_itr.next();
				if (match(e.getKey(), x.getKey())) {
					y = e;
					break;
				}
			}

			if (y == null) { // x is unique in er: move to er
				er.put(x.getKey(), x.getValue());
				x_itr.remove();
			} else { // merge x and y; remove x from d and y from er
				Entity z = merge(x.getKey(), y.getKey());
				z.setMergedFlag(true);
				LinkedList<Entity> l = new LinkedList<Entity>();
				
				x_itr.remove();
				y_itr.remove();
				
				if (x.getKey().isMerged()) {
					l.addAll(x.getValue());
					d.put(z, l);
				} else {
					l.add(x.getKey());
					d.put(z, l);
				}
				if (y.getKey().isMerged()) {
					for (Entity e : y.getValue()) {
						if (!d.get(z).contains(e)) d.get(z).add(e);
					}
				} else {
					d.get(z).add(y.getKey());
				}
			}
			
			x_itr = d.entrySet().iterator();
			y_itr = er.entrySet().iterator();
		}
		return er;
	}

	// Generic R-Swoosh algorithm 
	public Map<Entity, LinkedList<Entity>> resolve(List<Entity> unresolvedEntities) {
		return RSwoosh(unresolvedEntities);

	}

}
