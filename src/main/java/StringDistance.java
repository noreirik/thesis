/*
 * Given two strings, return a number between 0 and 1 as a degree of similarity between the two strings,
 * where 1 means that the strings are identical  
**/ 

public interface StringDistance {
	
	/**
	 * 
	 * @param s1 a string
	 * @param s2 another string which s1 will measure its distance to 
	 * @return a number between 0 and 1 representing the distance between s1 and s2,
	 * and where a value of 1 means that the two strings are identical 
	 */
	public float distance(String s1, String s2);
	
}
