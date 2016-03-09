package main.java.com.tinyeditor.filter;

import main.java.com.tinyeditor.image.ImageEditor;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


/**
 *
 * @Since	March 9, 2016
 * @Author	Constantin MASSON
 */
public class ContrastFilter{
	// ************************************************************************
	// Attributes
	// ************************************************************************
	private int coef;


	// ************************************************************************
	// Initialization
	// ************************************************************************
	public void ContrastFilter(){
		this.coef = 0;
	}


	// ************************************************************************
	// Initialization
	// ************************************************************************
	public WritableImage applyFilter(Image img){
		//Set the pixel reader and writer
		PixelReader		pixelReader	= img.getPixelReader();
		WritableImage	wImg		= new WritableImage((int)img.getHeight(), (int)img.getWidth());
		PixelWriter		pixelWriter = wImg.getPixelWriter();

		//Process each pixel
		for(int y=0; y<img.getHeight(); y++){
			for(int x=0; x<img.getWidth(); x++){
				Color color = pixelReader.getColor(x,y);
				int r, g, b;

				r = this.getContrastValue(color.getRed()*255);
				g = this.getContrastValue(color.getGreen()*255);
				b = this.getContrastValue(color.getBlue()*255);

				//Val under 0
				r = (r<0)? 0:r;
				g = (g<0)? 0:g;
				b = (b<0)? 0:b;

				//Val above 255
				r = (r>255)? 255:r;
				g = (g>255)? 255:g;
				b = (b>255)? 255:b;

				color = Color.rgb(r, g, b);
				pixelWriter.setColor(x,y,color);
			}
		}
		return wImg;
	}

	/**
	 * Return the contrast coef for current pixel position
	 */
	private int getContrastValue(double current){
		double f = (259 * (this.coef + 255)) / (255 * (259 - this.coef));
		return (int)((f*(current - 128)) + 128);
	}



	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	public void setCoef(int value){
		this.coef = value;
	}
}
