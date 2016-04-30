package com.tinyeditor.modules.filter.convolution;

import com.tinyeditor.modules.filter.asset.FilterHelper;
import com.tinyeditor.modules.filter.asset.ImageFilter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Define a Convolution Filter.
 * All convolution filters extends this filter.
 *
 * @since	Mar 9, 2016
 * @author	Constantin MASSON
 */
public abstract class ConvolutionFilter implements ImageFilter{
	protected int matrix[][]; //Generaly matrix size 3,5,7,9 (Can be not square)
	protected int offset; //Filter offset, usually 0
	protected int divisor; //Divisor, usually 1


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

		//Process each pixel
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				Color color = this.getColorFromMatrix(pixelReader, x, y, width, height);
				pixelWriter.setColor(x,y,color);
			}
		}
		return wimage;
	}


	// ************************************************************************
	// Matrix management functions
	// ************************************************************************
	/**
	 * Get Color Red/Green/Blue value of pixel after applying matrix filter. 
	 * Pixel outside edge of editor are skipped.
	 *
	 * @param pixelReader	PixelReader to process
	 * @param posX			Current X coordinate in pixelReader
	 * @param posY			Current Y coordinate in pixelReader
	 * @param w				Image width
	 * @param h				Image height
	 * @return				New Color calculated from matrix filter
	 */
	private Color getColorFromMatrix(PixelReader pixelReader, int posX, int posY, int w, int h){
		int posMx, posMy; //Position of current matrix element in editor
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
		sumR = FilterHelper.limit255Value(this.offset + (sumR/this.divisor));
		sumG = FilterHelper.limit255Value(this.offset + (sumG/this.divisor));
		sumB = FilterHelper.limit255Value(this.offset + (sumB/this.divisor));
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
	public int[][] getMatrix(){
		return this.matrix;
	}
	public int getOffset(){
		return this.offset;
	}
	public int getDivisor(){
		return this.divisor;
	}
}
