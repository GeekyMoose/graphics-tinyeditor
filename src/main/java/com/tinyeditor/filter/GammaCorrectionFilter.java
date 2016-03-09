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
public class GammaCorrectionFilter{
	// ************************************************************************
	// Attributes
	// ************************************************************************
	private double coef;


	// ************************************************************************
	// Initialization
	// ************************************************************************
	public void GammaCorrectionFilter(){
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

				r = this.getGammaValue(color.getRed());
				g = this.getGammaValue(color.getGreen());
				b = this.getGammaValue(color.getBlue());

				r = limit255Value(r);
				g = limit255Value(g);
				b = limit255Value(b);

				color = Color.rgb(r, g, b);
				pixelWriter.setColor(x,y,color);
			}
		}
		return wImg;
	}

	/**
	 * Return the contrast coef for current pixel position
	 */
	private int getGammaValue(double current){
		return (int)( 255*(Math.pow(current, this.coef)));
		//double gammaCorrection = 1/this.coef;
		//return (int)(255 * Math.pow((current / 255), gammaCorrection));
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
	// Getters - Setters
	// ************************************************************************
	public void setCoef(double value){
		this.coef = value;
	}
}
