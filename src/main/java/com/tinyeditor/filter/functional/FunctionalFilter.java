package com.tinyeditor.filter.functional;

import com.tinyeditor.filter.asset.FilterHelper;
import com.tinyeditor.filter.asset.ImageFilter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;


/**
 * Define a functional filter.
 * All functional filters must extends this abstract class.
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public abstract class FunctionalFilter implements ImageFilter{

	@Override
	public WritableImage applyFilter(Image image){
		int height	= (int)image.getHeight();
		int width	= (int)image.getWidth();
		int r,g,b;
		//Set the pixel reader and writer
		PixelReader		pixelReader	= image.getPixelReader();
		WritableImage	wimage		= new WritableImage(width, height);
		PixelWriter		pixelWriter = wimage.getPixelWriter();

		//Process each pixel
		for(int y=0; y<image.getHeight(); y++){
			for(int x=0; x<image.getWidth(); x++){
				Color color = this.getFilterColor(pixelReader, x, y, width, height);
				pixelWriter.setColor(x,y,color);
			}
		}
		return wimage;
	}

	/**
	 * Get the new color at position x,y processed by the filter.
	 * Each filter define its own way to process.
	 *
	 * @param pixelReader	PixelReader of image
	 * @param x				Current X position in image
	 * @param y				Current y position in image
	 * @param width			Image width
	 * @param height		Image height
	 * @return				New color for this position
	 */
	protected abstract Color getFilterColor(PixelReader pixelReader, int x, int y, int width, int height);
}
