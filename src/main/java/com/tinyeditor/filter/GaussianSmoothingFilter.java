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
public class GaussianSmoothingFilter extends ConvolutionFilter{
	/**
	 * Create a new Blur Filter
	 */
	public GaussianSmoothingFilter(){
		int[][] m = {{1,2,1},{2,4,2},{1,2,1}}; //@TODO Allow general rule
		this.matrix		= m;
		this.offset		= 0;
		this.divisor	= 16;
	}
}


