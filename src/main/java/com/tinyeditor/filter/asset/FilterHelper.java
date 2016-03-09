package main.java.com.tinyeditor.filter.asset;


/**
 * Helper for filter. Contains useful function for filter.
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public class FilterHelper{

	/**
	 * Return a value limited to 0-255. (Generally used for RBG Color)
	 *
	 * @param value		Value to process
	 * @return			Value between 0 and 255
	 */
	public static int limit255Value(int value){
		if(value > 255)	{ return 255; }
		if(value < 0)	{ return 0; }
		return value;
	}
}
