package com.tinyeditor.modules.filter.asset;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;


/**
 * ImageFilter define any filter that can be applyed on an Image (Javafx Image)
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public interface ImageFilter{

	/**
	 * Apply the filter on a given editor and return an editor processed.
	 *
	 * @param image		The editor to process
	 * @return			WritableImage processed by filter
	 */
	public WritableImage applyFilter(Image image);
}
