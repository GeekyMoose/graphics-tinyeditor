package com.tinyeditor.filter.dithering;

import com.tinyeditor.filter.asset.ImageFilter;
import com.tinyeditor.filter.asset.FilterHelper;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class OrderedDitheringFilter implements ImageFilter{
	protected int[][] bayerMatrix;
	protected int	k; //K value for diphering
	protected int	n; //n * n bayer matrix
	//List of thresholds for each color
	protected int[]	rThresholds;
	protected int[]	gThresholds;
	protected int[]	bThresholds;
	//Radius are nb of pixel for one color
	protected int	rRadius;
	protected int	gRadius;
	protected int	bRadius;

	public OrderedDitheringFilter(){
		this.k = 2;
		this.setN(2);
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

		int r,g,b; //tmp values used for process
		int[] thresholds = new int[]{127};
		int mSize = this.bayerMatrix.length;
		int mVal = mSize*mSize + 1;
		//Process each pixel
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				Color color = pixelReader.getColor(x,y);
				r = (int)(color.getRed() * 255);
				g = (int)(color.getGreen() * 255);
				b = (int)(color.getBlue() * 255);

				r = (int)(r + (this.bayerMatrix[y%mSize][x%mSize] * this.rRadius/mVal));
				g = (int)(g + (this.bayerMatrix[y%mSize][x%mSize] * this.gRadius/mVal));
				b = (int)(b + (this.bayerMatrix[y%mSize][x%mSize] * this.bRadius/mVal));

				r = thresholdTransform(r, thresholds, this.rRadius);
				g = thresholdTransform(g, thresholds, this.gRadius);
				b = thresholdTransform(b, thresholds, this.bRadius);

				pixelWriter.setColor(x,y,Color.rgb(r,g,b));
			}
		}
		return wimage;
	}

	private int thresholdTransform(int value, int[]thresholds, int radius){
		int v = 0;
		for(int k=0; k<thresholds.length; k++){
			if(value > thresholds[k]){
				continue;
			}
			if(value <= thresholds[k]){
				v = thresholds[k] - radius;
				return v >= 0 ? v : 0;
			}
		}
		return thresholds[thresholds.length - 1]+radius;
	}

	/**
	 * Initialize the threshold values for the given image.
	 *
	 * @param input		Image to process (PixelReader)
	 * @param height	Image height
	 * @param width		Image width
	 */
	private void initThresholdValues(PixelReader input, int height, int width){
		int[][] minmax = FilterHelper.calculMinMaxRGB(input, height, width, 255);
		int nbThresholds = this.k/2;

		//Set the radius values
		//minmax[x][1] - minmax[0] means max - min (Nb values covered by pixels)
		this.rRadius	= (minmax[0][1] - minmax[0][0])/this.k;
		this.gRadius	= (minmax[1][1] - minmax[1][0])/this.k;
		this.bRadius	= (minmax[2][1] - minmax[2][0])/this.k;

		//Initialize the threshold array attributes
		this.rThresholds = new int[nbThresholds];
		this.gThresholds = new int[nbThresholds];
		this.bThresholds = new int[nbThresholds];

		//Calculate all
		for(int k=0; k<(this.k/2); k++){
			//Threshold is 2*radius*k + radius away from origin.
			//Since min is not always 0, position must be shifted by min
			this.rThresholds[k] = minmax[0][0] + (2 * this.rRadius * k) + this.rRadius;
			this.gThresholds[k] = minmax[1][0] + (2 * this.gRadius * k) + this.gRadius;
			this.bThresholds[k] = minmax[2][0] + (2 * this.bRadius * k) + this.bRadius;
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

	public void setN(int value){
		if(value == 2 || value == 3 || value == 4){
			this.n = value;
			this.setBayerMatrix(value);
		}
	}

	private void setBayerMatrix(int n){
		switch(n){
			case 2:
				this.bayerMatrix = new int[][]{
					{1,3},
					{4,2}
				};
				break;
			case 3:
				this.bayerMatrix = new int[][]{
					{3,7,4},
					{6,1,9},
					{2,8,5}
				};
				break;
			case 4:
				this.bayerMatrix = new int[][]{
					{1,9,3,11},
					{13,5,15,7},
					{4,12,2,10},
					{16,8,14,6}
				};
				break;
		}
	}
}
