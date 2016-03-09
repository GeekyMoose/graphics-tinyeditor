package main.java.com.tinyeditor.filter;

import main.java.com.tinyeditor.image.ImageEditor;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @Since	March 9, 2016
 * @Author	Constantin MASSON
 */
public class InversionFilter{
	// ************************************************************************
	// Attributes
	// ************************************************************************


	// ************************************************************************
	// Initialization
	// ************************************************************************
	public void InversionFilter(){
	}


	// ************************************************************************
	// Initialization
	// ************************************************************************
	public WritableImage applyFilter(Image image){
		//Image img = image.getProcessImage();
		Image img = image;

		//Set the pixel reader and writer
		PixelReader		pixelReader	= img.getPixelReader();
		WritableImage	wImg		= new WritableImage((int)img.getHeight(), (int)img.getWidth());
		PixelWriter		pixelWriter = wImg.getPixelWriter();

		//Process each pixel
		for(int x=0; x<img.getHeight(); x++){
			for(int y=0; y<img.getWidth(); y++){
				Color color = pixelReader.getColor(x,y);
				color = color.invert();
				pixelWriter.setColor(x,y,color);
			}
		}
		return wImg;
	}
}
