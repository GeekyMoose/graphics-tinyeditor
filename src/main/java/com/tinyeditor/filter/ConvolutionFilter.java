package main.java.com.tinyeditor.filter;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Define a Convolution Filter
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public abstract class ConvolutionFilter{
	protected int matrix[][];
	protected int offset; //Filter offset, usually 0
	protected int divisor; //Divisor, usually 1


	// ************************************************************************
	// General functions
	// ************************************************************************
	/**
	 * Apply convolution filter on an image given in parameter
	 *
	 * @param img	Image to process (Wont be modified)
	 * @return		Return a PixelWriter of the image with filter applied
	 */
	public WritableImage applyFilter(Image img){
		int height	= (int)img.getHeight();
		int width	= (int)img.getWidth();
		//Set the pixel reader and writer
		PixelReader		pixelReader	= img.getPixelReader();
		WritableImage	wImg		= new WritableImage(height, width);
		PixelWriter		pixelWriter = wImg.getPixelWriter();

		//Process each pixel
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				Color color = this.getColorFromMatrix(pixelReader, x, y, height, width);
				pixelWriter.setColor(x,y,color);
			}
		}
		return wImg;
	}

	/**
	 * Return a value limited to 0-255. 
	 *
	 * @param value		Value to process
	 * @return			Value between 0 and 255
	 */
	private int limit255Value(int value){
		if(value > 255)	{ return 255; }
		if(value < 0)	{ return 0; }
		return value;
	}


	// ************************************************************************
	// Matrix management functions
	// ************************************************************************
	/**
	 * Get Color Red/Green/Blue value of pixel after applying matrix filter. 
	 * Pixel outside edge of image are skipped.
	 *
	 * @param pixelReader	PixelReader to process
	 * @param posX			Current X coordinate in pixelReader
	 * @param posY			Current Y coordinate in pixelReader
	 * @param h				Image height
	 * @param w				Image width
	 * @return				New Color calculated from matrix filter
	 */
	private Color getColorFromMatrix(PixelReader pixelReader, int posX, int posY, int h, int w){
		int posMx, posMy; //Position of current matrix element in img
		int sumR = 0, sumG = 0, sumB = 0;

		//Process each element of matrix
		for(int y=0; y<this.getMatrixNbRow(); y++){
			for(int x=0; x<this.getMatrixNbColumn(); x++){
				//Get coordinates of matrix elements relative to Roster
				posMx = posX-(posX%3)+x - this.getMatrixNbColumn()/2; 
				posMy = posY-(posY%3)+y - this.getMatrixNbRow()/2;
				if(posMx < 0 || posMx >= w || posMy < 0 || posMy >= h){
					continue;//If outside edge, we just skip it
				}
				Color color = pixelReader.getColor(posMx, posMy);
				sumR += (int) (255 * color.getRed()		* this.matrix[y][x]);
				sumG += (int) (255 * color.getGreen()	* this.matrix[y][x]);
				sumB += (int) (255 * color.getBlue()	* this.matrix[y][x]);
			}
		}
		//Apply calculation + Limit extreme values (x<0, x>255)
		sumR = limit255Value(this.offset + (sumR/this.divisor));
		sumG = limit255Value(this.offset + (sumG/this.divisor));
		sumB = limit255Value(this.offset + (sumB/this.divisor));
		return Color.rgb(sumR, sumG, sumB);
	}


	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	public int getMatrixNbRow(){
		return this.matrix.length;
	}
	public int getMatrixNbColumn(){
		return this.matrix[0].length;
	}
}
