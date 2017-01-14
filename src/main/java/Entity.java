public class Entity {

	private String fullName;
	private String firstName;
	private String lastName;
	private String id;
	private String jobTitle;
	
	public Entity() { }
	
	public void setFullName(String fullName)	{ this.fullName = fullName; 	}
	public void setFirstName(String firstName)	{ this.firstName = firstName;	}
	public void setLastName(String lastName)	{ this.lastName = lastName;		}
	public void setId(String id)				{ this.id = id;					}
	public void setJobTitle(String jobTitle)	{ this.jobTitle = jobTitle;		}

	public String getFullName() 	{ return fullName; }
	public String getFirstName()	{ return firstName; }
	public String getLastName() 	{ return lastName; }
	public String getId() 			{ return id; }
	public String getJobTitle()		{ return jobTitle; }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		String id = getId();
		String name = getFullName();
		String firstName = getFirstName();
		String lastName = getLastName();
		String jobTitle = getLastName();
		
		if (verify(id)) sb.append(id+":");
		else sb.append("NULL:");
		if (verify(name)) sb.append(name+":");
		else sb.append("NULL:");
		if (verify(firstName)) sb.append(firstName+":");
		else sb.append("NULL:");
		if (verify(lastName)) sb.append(lastName+":");
		else sb.append("NULL:");
		if (verify(jobTitle)) sb.append(jobTitle);
		else sb.append("NULL");
		
		return sb.toString();		
	}
	
	public boolean verify(String s) {
		return !(s.isEmpty() || s == null);
	}
}
