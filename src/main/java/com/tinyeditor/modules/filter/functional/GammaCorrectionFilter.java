package com.tinyeditor.modules.filter.functional;

import com.tinyeditor.modules.filter.asset.FilterHelper;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;


/**
 * Define one GamaCorrection Filter.
 *
 * @since	March 9, 2016
 * @author	Constantin MASSON
 */
public class GammaCorrectionFilter extends FunctionalFilter{
	// ************************************************************************
	// Attributes
	// ************************************************************************
	private double coef;


	// ************************************************************************
	// Initialization
	// ************************************************************************
	public GammaCorrectionFilter(){
		this.coef = 0;
	}

	/**
	 * Process one RBG value with Gamma form.
	 *
	 * @return	Value after gamma process
	 */
	private int getGammaValue(double current){
		return (int)( 255 * (Math.pow(current, this.coef)) );
	}


	// ************************************************************************
	// Override function from FunctionalFilter class
	// ************************************************************************
	@Override
	protected Color getFilterColor(PixelReader pixelReader, int x, int y, int width, int height){
		Color color = pixelReader.getColor(x,y);

		int r = this.getGammaValue(color.getRed());
		int g = this.getGammaValue(color.getGreen());
		int b = this.getGammaValue(color.getBlue());

		r = FilterHelper.limit255Value(r);
		g = FilterHelper.limit255Value(g);
		b = FilterHelper.limit255Value(b);

		return Color.rgb(r, g, b);
	}


	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	public void setCoef(double value){
		this.coef = value;
	}
}
