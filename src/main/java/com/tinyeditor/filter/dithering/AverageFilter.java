package main.java.com.tinyeditor.filter.diphering;

import main.java.com.tinyeditor.filter.asset.ImageFilter;


/**
 * A Average Dithering filter.
 *
 * @since	Apr 5, 2016
 * @author	Constantin MASSON
 */
public class AverageFilter implements ImageFilter{
	protected int	k; //K value for diphering
	//List of thresholds for each color
	protected int[]	rThresholds;
	protected int[]	gThresholds;
	protected int[]	gThresholds;

	/**
	 * Create a DipheringFilter with default values.
	 * K default value is 2
	 */
	public DipheringFilter(){
		int k = 2;
		this.rThresholds = new in[1];
		this.rThresholds = new in[1];
		this.rThresholds = new in[1];
	}


	// ************************************************************************
	// Override functions from Image Filter
	// ************************************************************************
	@Override
	public WritableImage applyFilter(Image image){
		int height	= (int)image.getHeight();
		int width	= (int)image.getWidth();
		//Set the pixel reader and writer
		PixelReader		pixelReader	= image.getPixelReader();
		WritableImage	wimage		= new WritableImage(width, height);
		PixelWriter		pixelWriter = wimage.getPixelWriter();

		//@TODO to optimize, getting the thresholds is long.
		this.initThresholdValues(pixelReader, height, width);

		//Process each pixel
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				//Color color = this.getColorFromMatrix(pixelReader, x, y, width, height);
				pixelWriter.setColor(x,y,color);
			}
		}
		return wimage;
	}


	// ************************************************************************
	// Functions
	// ************************************************************************
	/**
	 * Initialize the threshold values for the given image.
	 * Matrix size if linked with current k value: 3 lines / k-1 column.
	 *
	 * @param input		Image to process (PixelReader)
	 * @param height	Image height
	 * @param width		Image width
	 */
	protected void initThresholdValues(PixelReader input, int height, int width){
		int[][] minmax = FilterHelper.calculMinMaxRGB(input, height, width, 255);

		// Range is the value max - min (Nb values covered by pixels)
		// Step is the nb of pixel in within the range of one threshold.
		int redRange, redStep;
		int greenRange, greenStep;
		int blueRange, blueStep;

		//Set the range values
		redRange	= minmax[0][1] - minmax[0][0];
		greenRange	= minmax[1][1] - minmax[1][0];
		blueRange	= minmax[2][1] - minmax[2][0];

		//Set step values
		redStep		= redRange/this.k;
		greenStep	= greenRange/this.k;
		blueStep	= blueRange/this.k;

		//Initialize the threshold array attributes
		this.rThresholds = new int[this.k];
		this.gThresholds = new int[this.k];
		this.bThresholds = new int[this.k];

		//Calculate all
		for(int k=0; k<this.k; k++){
			//Threshold k is positioned k time step away from the origin. 
			//Since the origin can be a not zero number, we have to shift by min value.
			//Then, the threshold is actually in the middle the current step -(step/2)
			this.rThresholds[k] = ((redStep * k)	+ minmax[0][0]) - (redStep/2);
			this.gThresholds[k] = ((greenStep * k)	+ minmax[1][0]) - (greenStep/2);
			this.bThresholds[k] = ((blueStep * k)	+ minmax[2][0]) - (blueStep/2);
		}
	}


	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	public int getK(){
		return this.k;
	}

	/**
	 * Set the K value for this diphering filter.
	 * If K is not valid, nothing is done. K must be more than 2 and even number.
	 *
	 * @param value	The K value
	 */
	public void setK(int value){
		if(value>1 && value<255 && value%2==0){
			this.k = value;
		}
	}
}
