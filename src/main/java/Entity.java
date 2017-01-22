import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Entity {

	private static final Logger logger = LogManager.getLogger(Entity.class);
	
	private String fullName;
	private String firstName;
	private String lastName;
	private String id;
	private String jobTitle;
	private boolean isMerged;
	
	public Entity(	String id, String fullName, String firstName,
					String lastName, String jobTitle, boolean isMerged)
	{
		this.id = id;
		this.fullName = fullName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
		this.isMerged = isMerged; 
	}
	
	public void setFullName(String fullName)	{ this.fullName = fullName; 	}
	public void setFirstName(String firstName)	{ this.firstName = firstName;	}
	public void setLastName(String lastName)	{ this.lastName = lastName;		}
	public void setId(String id)				{ this.id = id;					}
	public void setMergedFlag(boolean isMerged)	{ this.isMerged = isMerged;		}
	public void setJobTitle(String jobTitle)	{ this.jobTitle = jobTitle;		}

	public String getFullName() 	{ return fullName; }
	public String getFirstName()	{ return firstName; }
	public String getLastName() 	{ return lastName; }
	public String getId() 			{ return id; }
	public String getJobTitle()		{ return jobTitle; }
	public boolean isMerged() 		{ return isMerged; }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		String id = getId();
		String name = getFullName();
		String firstName = getFirstName();
		String lastName = getLastName();
		String jobTitle = getJobTitle();
		
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((jobTitle == null) ? 0 : jobTitle.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof Entity)) { return false; }
		Entity other = (Entity) obj;
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.toLowerCase().equals(other.firstName.toLowerCase())) {
			return false;
		}
		if (fullName == null) {
			if (other.fullName != null) {
				return false;
			}
		} else if (!fullName.toLowerCase().equals(other.fullName.toLowerCase())) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.toLowerCase().equals(other.id.toLowerCase())) {
			return false;
		}
		if (jobTitle == null) {
			if (other.jobTitle != null) {
				return false;
			}
		} else if (!jobTitle.equals(other.jobTitle.toLowerCase())) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.toLowerCase().equals(other.lastName.toLowerCase())) {
			return false;
		}
		return true;
	}

}
