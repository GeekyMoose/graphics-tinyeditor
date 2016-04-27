package com.tinyeditor.image;

import javafx.scene.image.Image;

/**
 * Represente the image to edit.
 *
 * @since	March 9, 2016
 * @author	Constantin MASSON
 */
public class ImageEditor{
	//@TODO Dev in progress
	private	Image	rawImage;
	private	Image	processImage;

	public ImageEditor(){
		this.rawImage		= null;
		this.processImage	= null;
	}

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
