package com.tinyeditor.editor;

/**
 * Represent the Editor content of the application.
 *
 * @since   Apr 28, 2016
 * @author  Constantin MASSON
 */
public class Editor {
	private ImageEditor image;

	public void setImage(ImageEditor img){
		this.image = img;
	}
	public ImageEditor getImage(){
		return this.image;
	}
}
