package com.tinyeditor.filter.dithering;

import com.tinyeditor.filter.asset.ImageFilter;
import com.tinyeditor.filter.asset.FilterHelper;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class ErrorDiffusionFilter implements ImageFilter{
	protected int[][] bayerMatrix;
	protected int	k; //K value for diphering
	//List of thresholds for each color
	protected int[]	rThresholds;
	protected int[]	gThresholds;
	protected int[]	bThresholds;
	//Radius are nb of pixel for one color
	protected int	rRadius;
	protected int	gRadius;
	protected int	bRadius;

	public ErrorDiffusionFilter(){
		this.k = 2;
		this.bayerMatrix = new int[][]{
			{0,0,0},
			{0,0,7},
			{3,5,1}
		};
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

		int[][] visited	= new int[height][width];//0 by default
		int[][][] values	= new int[height][width][3]; //previous values RGB

		int oldR, oldG, oldB;
		int r,g,b; //tmp values used for process
		int errR, errG, errB;
		int[] thresholds = new int[]{127};
		int mSize = this.bayerMatrix.length;
		int mVal = mSize*mSize + 1;
		//Process each pixel
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				if(visited[y][x] == 1){
					r = oldR = values[y][x][0];
					g = oldG = values[y][x][1];
					b = oldB = values[y][x][2];
				}
				else{
					Color color = pixelReader.getColor(x,y);
					r = oldR = (int)(color.getRed() * 255);
					g = oldG = (int)(color.getGreen() * 255);
					b = oldB = (int)(color.getBlue() * 255);
				}

				r = thresholdTransform(r, thresholds, this.rRadius);
				g = thresholdTransform(g, thresholds, this.gRadius);
				b = thresholdTransform(b, thresholds, this.bRadius);

				pixelWriter.setColor(x,y,Color.rgb(r,g,b));

				errR = oldR - r;
				errG = oldG - g;
				errB = oldB - b;

				setColorNeighbor(pixelReader, pixelWriter, visited, values, x+1, y  , width, height, errR, errG, errB, 16, 7);
				setColorNeighbor(pixelReader, pixelWriter, visited, values, x-1, y+1, width, height, errR, errG, errB, 16, 3);
				setColorNeighbor(pixelReader, pixelWriter, visited, values, x  , y+1, width, height, errR, errG, errB, 16, 5);
				setColorNeighbor(pixelReader, pixelWriter, visited, values, x+1, y+1, width, height, errR, errG, errB, 16, 1);
			}
		}
		return wimage;
	}

	private boolean neighborExists(int x, int y, int xlim, int ylim){
		return (x>=0 && x<xlim) && (y>=0 && y<ylim);
	}

	private void setColorNeighbor(PixelReader pr, PixelWriter pw, int[][]visited, int[][][]values, int x, int y, int xlim, int ylim, int errR, int errG, int errB, int div, int mPos){
		if(x<0 || y <0 || x>=xlim || y>=ylim){
			return;
		}
		Color color = pr.getColor(x,y);

		int r = (int)(color.getRed() * 255);
		int g = (int)(color.getGreen() * 255);
		int b = (int)(color.getBlue() * 255);

		r = FilterHelper.limit255Value(r + errR * mPos/div);
		g = FilterHelper.limit255Value(g + errG * mPos/div);
		b = FilterHelper.limit255Value(b + errB * mPos/div);

		pw.setColor(x,y,Color.rgb(r,g,b));
		visited[y][x] = 1;
		values[y][x][0] = r;
		values[y][x][1] = g;
		values[y][x][2] = b;
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
}
