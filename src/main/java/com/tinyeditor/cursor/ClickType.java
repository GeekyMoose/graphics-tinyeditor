package com.tinyeditor.cursor;

/**
 * Describe a possible kind of ClickDrawMiddleLine behavior.
 *
 * @since   Apr 27, 2016
 * @author  Constantin MASSON
 */
public interface ClickType {

	/**
	 * Clear the click behavior.
	 * Usually called when clickType is changed. This function reset all temporary
	 * data used by the clickType. For example, if a ClickDrawMiddleLine is waiting for a second
	 * click, this will reset.
	 */
	public void clear();
}