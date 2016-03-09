package main.java.com.tinyeditor.filter.convolution;

/**
 * Blur filter
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public class BlurFilter extends ConvolutionFilter{
	/**
	 * Create a new Blur Filter
	 */
	public BlurFilter(){
		int[][] m = {{1,1,1},{1,1,1},{1,1,1}}; //@TODO Allow general rule
		this.matrix		= m;
		this.offset		= 0;
		this.divisor	= 9;
	}
}


