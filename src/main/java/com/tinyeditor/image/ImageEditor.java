package main.java.com.tinyeditor.image;

import javafx.scene.image.Image;

/**
 * Represente the image to edit
 *
 * @since	March 9, 2016
 * @author	Constantin MASSON
 */
public class ImageEditor{
	private	Image	rawImage;
	private	Image	processImage;

	public ImageEditor(){
		//@TODO Brut version
		this.rawImage		= new Image("/tmp/testimg.jpg");
		this.processImage	= new Image("/tmp/testimg.jpg");
	}

	public Image getProcessImage(){
		return this.processImage;
	}
	public Image getRawImage(){
		return this.rawImage;
	}
}
