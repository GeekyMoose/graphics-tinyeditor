package main.java.com.tinyeditor.filter.convolution;

/**
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public class EdgeDetectionFilter extends ConvolutionFilter{
	/**
	 * Create a new Blur Filter
	 */
	public EdgeDetectionFilter(){
		int[][] m = {{0,-1,0},{0,1,0},{0,0,0}}; //@TODO Allow general rule
		this.matrix		= m;
		this.offset		= 0;
		this.divisor	= 1;
	}
}

