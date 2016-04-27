package com.tinyeditor.filter.asset;

import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;


/**
 * Helper for filter. Contains useful function for filter.
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public class FilterHelper{

	/**
	 * Return a value limited to 0-255. (Generally used for RBG Color)
	 *
	 * @param value		Value to process
	 * @return			Value between 0 and 255
	 */
	public static int limit255Value(int value){
		if(value > 255)	{ return 255; }
		if(value < 0)	{ return 0; }
		return value;
	}


	// ************************************************************************
	// RBG Calculator functions
	// ************************************************************************
	
	/**
	 * Calcul the min value and max value for Red, Green and Blue in image.
	 * RGB Values are on 255. Matrix returned match: lines stand R, G and B and
	 * columns are: min, max.
	 * Note: format must be valid (Max possible value, 255 for instance).
	 *
	 * @param format	RBG value format. (Max possible value for one pixel, 255 for example)
	 * @param height	Image height
	 * @param width		Image width
	 * @return			Matrix 3*2 with min/max values for RGB
	 */
	public static int[][] calculMinMaxRGB(PixelReader input, int height, int width, int format){
		//During the process, value match the getRed/Green/Blue FX representation
		//1 is the max value (See fct doc for further information)
		double rmin = 1, rmax = 0;
		double gmin = 1, gmax = 0;
		double bmin = 1, bmax = 0;
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				Color color = input.getColor(x, y);

				rmin = (color.getRed() < rmin) ? color.getRed() : rmin;
				rmax = (color.getRed() > rmax) ? color.getRed() : rmax;

				gmin = (color.getGreen() < gmin) ? color.getGreen() : gmin;
				gmax = (color.getGreen() > gmax) ? color.getGreen() : gmax;

				bmin = (color.getBlue() < bmin) ? color.getBlue() : bmin;
				bmax = (color.getBlue() > bmax) ? color.getBlue() : bmax;
			}
		}
		return new int[][] {
			{(int)(rmin * format), (int)(rmax * format)},
			{(int)(gmin * format), (int)(gmax * format)},
			{(int)(bmin * format), (int)(bmax * format)}
		};
	}

	public static int[][] calculMinMaxRGB(Image input){
		int			height	= (int)input.getHeight();
		int			width	= (int)input.getWidth();
		PixelReader	pr		= input.getPixelReader();
		return FilterHelper.calculMinMaxRGB(pr, height, width, 255);
	}
}
