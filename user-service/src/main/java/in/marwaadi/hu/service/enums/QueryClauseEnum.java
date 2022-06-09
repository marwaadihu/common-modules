package in.marwaadi.hu.service.enums;

/**
 * Enum class to query in database
 * 
 * @author a.anilagrawal5477@gmail.com
 *
 */
public enum QueryClauseEnum {

	/**
	 * Check Equal (==) operator between provided value and column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	EQUAL,

	/**
	 * Check Not Equal (!=) operator between provided value and column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	NOT_EQUAL,

	/**
	 * Check Less then (<) operator between provided value and column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	LESS_THEN,

	/**
	 * Check Less then or equal(<=) operator between provided value and column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	LESS_THEN_OR_EQUAL_TO,

	/**
	 * Check Greater then (>) operator between provided value and column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	GREATER_THEN,

	/**
	 * Check Greater then or equal (>=) operator between provided value and column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	GREATER_THEN_OR_EQUAL_TO,

	/**
	 * Check IN (IN) operator between provided value and column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	IN,

	/**
	 * Check Not In (NOT IN) operator between provided value and column
	 * 
	 * @author a.anilagrawal5477@gmail.com
	 */
	NOT_IN;
}
