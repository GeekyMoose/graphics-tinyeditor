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
public class BrightnessFilter{
	// ************************************************************************
	// Attributes
	// ************************************************************************
	private int coef;
	private int coefBefore; //before moving slide


	// ************************************************************************
	// Initialization
	// ************************************************************************
	public void BrightnessFilter(){
		this.coef = 0;
		this.coefBefore = 0;
	}
	public void BrightnessFilter(int value){
		this.coef = value;
		this.coefBefore = value;
	}


	// ************************************************************************
	// Initialization
	// ************************************************************************
	public WritableImage applyFilter(Image image){
		Image img = image;
		int applyCoef = this.coef - this.coefBefore;
		//int applyCoef = this.coef;

		//Set the pixel reader and writer
		PixelReader		pixelReader	= img.getPixelReader();
		WritableImage	wImg		= new WritableImage((int)img.getHeight(), (int)img.getWidth());
		PixelWriter		pixelWriter = wImg.getPixelWriter();

		//Process each pixel
		for(int y=0; y<img.getHeight(); y++){
			for(int x=0; x<img.getWidth(); x++){
				Color color = pixelReader.getColor(x,y);
				int r, g, b;

				r = (int)(color.getRed()*255) + applyCoef;
				g = (int)(color.getGreen()*255) + applyCoef;
				b = (int)(color.getBlue()*255) + applyCoef;

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



	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	public void setCoef(int value){
		this.coefBefore = this.coef;
		this.coef = value;
	}
}
