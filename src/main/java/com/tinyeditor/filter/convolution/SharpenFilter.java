package main.java.com.tinyeditor.filter.convolution;

/**
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public class SharpenFilter extends ConvolutionFilter{
	/**
	 * Create a new Blur Filter
	 */
	public SharpenFilter(){
		int[][] m = {{0,-1,0},{-1,5,-1},{0,-1,0}}; //@TODO Allow general rule
		this.matrix		= m;
		this.offset		= 0;
		this.divisor	= 1;
	}
}


