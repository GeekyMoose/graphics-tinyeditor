package com.tinyeditor.filter.functional;

import com.tinyeditor.filter.asset.FilterHelper;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;


/**
 * Define a Contrast Filter.
 *
 * @Since	March 9, 2016
 * @Author	Constantin MASSON
 */
public class ContrastFilter extends FunctionalFilter{
	// ************************************************************************
	// Attributes
	// ************************************************************************
	private double coef;


	// ************************************************************************
	// Initialization
	// ************************************************************************
	public ContrastFilter(){
		this.coef = 0;
	}


	// ************************************************************************
	// Override function from FunctionalFilter class
	// ************************************************************************
	@Override
	protected Color getFilterColor(PixelReader pixelReader,int x, int y, int width, int height){
		Color color = pixelReader.getColor(x,y);

		int r = this.getContrastValue(color.getRed()	* 255);
		int g = this.getContrastValue(color.getGreen()	* 255);
		int b = this.getContrastValue(color.getBlue()	* 255);

		r = FilterHelper.limit255Value(r);
		g = FilterHelper.limit255Value(g);
		b = FilterHelper.limit255Value(b);

		return Color.rgb(r,g,b);
	}

	/**
	 * @deprecated Currently not used (Valid before deletion)
	 * Process one RBG value by contrast filter form. 
	 * Use a 255 RGB representatin.
	 * If new pixel reach limit (inf 0 or sup 255), then 0 or 255 is returned
	 *
	 * @param pixel		Current Pixel value to process (Can be an R, G or B, 255 format)
	 * @return			New value for this color pixel (int between 0-255)
	 */
	private int getContrastValue(double pixel){
		return (int) ( ((pixel-(255/2)) * this.coef) + (255/2) );
	}


	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	public void setCoef(double value){
		this.coef = value;
	}
}
