package com.tinyeditor.filter.functional;

import com.tinyeditor.filter.asset.FilterHelper;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;


/**
 * Define a BrightnessFilter
 *
 * @Since	March 9, 2016
 * @Author	Constantin MASSON
 */
public class BrightnessFilter extends FunctionalFilter{
	// ************************************************************************
	// Attributes
	// ************************************************************************
	private int coef;
	private int coefOld; //before moving slide


	// ************************************************************************
	// Initialization
	// ************************************************************************
	public BrightnessFilter(){
		this.coef		= 0;
		this.coefOld	= 0;
	}


	// ************************************************************************
	// Override function from FunctionalFilter class
	// ************************************************************************
	@Override
	protected Color getFilterColor(PixelReader pixelReader, int x, int y, int width, int height){
		int applyCoef = this.coef - this.coefOld;
		Color color = pixelReader.getColor(x,y);

		int r = (int)(color.getRed()	* 255)	+ applyCoef;
		int g = (int)(color.getGreen()	* 255)	+ applyCoef;
		int b = (int)(color.getBlue()	* 255)	+ applyCoef;

		r = FilterHelper.limit255Value(r);
		g = FilterHelper.limit255Value(g);
		b = FilterHelper.limit255Value(b);

		return Color.rgb(r, g, b);
	}


	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	public void setCoef(int value){
		this.coefOld	= this.coef;
		this.coef		= value;
	}
}
