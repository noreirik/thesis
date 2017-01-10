public class Entity {
	
	private final String UNKNOWN_FIELD = "unknown";
	private String fullName;
	private String firstName;
	private String lastName;
	private String id;
	private String jobTitle;
	
	public Entity(String fullName, String id, String firstName, String lastName, String jobTitle) {
		setId(id);
		setJobTitle(jobTitle);
		setFirstName(firstName);
		setLastName(lastName);
		// if both firstName and lastName fields are set, use that as the best representation of a full name
		if ( isFieldKnown( getFirstName() ) && isFieldKnown( getLastName() ) )
			this.setFullName( String.format("%s %s", getFirstName(), getLastName() ) );
		else setFullName(fullName);
	}
	
	public boolean isFieldKnown(String value) { return !(value == UNKNOWN_FIELD); }
	
	public String retrieveFieldValue(String s) { 
		return s == null || s.isEmpty() ? UNKNOWN_FIELD : s;
	}
	
	public void setFullName(String fullName)	{ this.fullName = retrieveFieldValue(fullName); 	}
	public void setFirstName(String firstName)	{ this.firstName = retrieveFieldValue(firstName);	}
	public void setLastName(String lastName)	{ this.lastName = retrieveFieldValue(lastName);		}
	public void setId(String id)				{ this.id = retrieveFieldValue(id);					}
	public void setJobTitle(String jobTitle)	{ this.jobTitle = retrieveFieldValue(jobTitle);		}

	public String getFullName() 	{ return fullName; }
	public String getFirstName()	{ return firstName; }
	public String getLastName() 	{ return lastName; }
	public String getId() 			{ return id; }
	public String getJobTitle()		{ return jobTitle; }
}
