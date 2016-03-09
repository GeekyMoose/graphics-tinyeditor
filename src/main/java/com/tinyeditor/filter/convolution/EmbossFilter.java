package main.java.com.tinyeditor.filter.convolution;

/**
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public class EmbossFilter extends ConvolutionFilter{
	/**
	 * Create a new Blur Filter
	 */
	public EmbossFilter(){
		int[][] m = {{-1,0,1},{-1,1,1},{-1,0,1}}; //@TODO Allow general rule
		this.matrix		= m;
		this.offset		= 0;
		this.divisor	= 1;
	}
}


