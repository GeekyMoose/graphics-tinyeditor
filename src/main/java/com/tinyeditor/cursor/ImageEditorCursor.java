package com.tinyeditor.cursor;

/**
 * Cursor for ImageEditor.
 *
 * @since   Apr 27, 2016
 * @author  Constantin MASSON
 */
public class ImageEditorCursor {
	private ClickType clickType;

	public ImageEditorCursor(){
		this.clickType = new NoAction();
	}

	/**
	 * Change ClickDrawMiddleLine behavior.
	 * If invalid parameter (null), do nothing.
	 *
	 * @param newType new ClickType to set
	 */
	public void setClickType(ClickType newType){
		if(newType != null){
			this.clickType.clear(); //Important, clear before changing
			this.clickType = newType;
		}
	}
}
