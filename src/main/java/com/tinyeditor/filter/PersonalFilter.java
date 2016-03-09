package main.java.com.tinyeditor.filter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


/**
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public class PersonalFilter extends ConvolutionFilter{
	/**
	 * Create a new Blur Filter
	 */
	public PersonalFilter(){
		int[][] m = {{1,0,1,2,2}, {0,1,0,2,2},{0,1,0,2,2}};
		this.matrix		= m;
		this.offset		= 0;
		this.divisor	= 1;
	}
}


