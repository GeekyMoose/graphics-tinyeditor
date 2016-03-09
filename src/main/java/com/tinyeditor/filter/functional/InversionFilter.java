package main.java.com.tinyeditor.filter.functional;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

/**
 * Define an Inversion Filter.
 *
 * @Since	March 9, 2016
 * @Author	Constantin MASSON
 */
public class InversionFilter extends FunctionalFilter{
	// ************************************************************************
	// Override function from FunctionalFilter class
	// ************************************************************************
	@Override
	protected Color getFilterColor(PixelReader pixelReader, int x, int y, int width, int height){
		Color color = pixelReader.getColor(x,y);
		return color.invert();
	}
}
