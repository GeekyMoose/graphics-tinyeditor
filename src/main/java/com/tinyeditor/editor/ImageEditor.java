package com.tinyeditor.editor;

import javafx.scene.image.Image;

/**
 * ImageEditor is an image that can be edited.
 * It keep all edit data like the original image, the current process image etc.
 *
 * @since	March 9, 2016
 * @author	Constantin MASSON
 */
public class ImageEditor{
	private	Image	rawImage;
	private	Image	processImage;


	// ************************************************************************
	// Constructor - Initialization
	// ************************************************************************
	public ImageEditor(){
		this.rawImage		= null;
		this.processImage	= null;
	}


	// ************************************************************************
	// Main Methods
	// ************************************************************************
	/**
	 * Reset the image to its first and original state.
	 *
	 * @return
	 */
	public Image reset(){
		this.processImage = this.rawImage;
		return this.processImage;
	}

	/**
	 * Update the image.
	 * Raw image is not modified, only process image is changed.
	 *
	 * @param newProcessImage   new image
	 * @return                  the new process image
	 */
	public Image update(Image newProcessImage){
		this.processImage = newProcessImage;
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
