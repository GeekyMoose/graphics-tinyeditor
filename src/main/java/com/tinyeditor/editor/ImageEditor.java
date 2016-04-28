package com.tinyeditor.editor;

import javafx.scene.image.Image;

/**
 * ImageEditor is an image that can be edited.
 *
 * @since	March 9, 2016
 * @author	Constantin MASSON
 */
public class ImageEditor{
	private	Image	rawImage;
	private	Image	processImage;

	public ImageEditor(){
		this.rawImage		= null;
		this.processImage	= null;
	}

	/**
	 * Reset the image to its first and original state.
	 *
	 * @return
	 */
	public Image reset(){
		this.processImage = this.rawImage;
		return this.processImage;
	}

	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	public void setImage(Image image){
		this.rawImage		= image;
		this.processImage	= image;
	}
	public Image getProcessImage(){
		return this.processImage;
	}
	public Image getRawImage(){
		return this.rawImage;
	}
}
