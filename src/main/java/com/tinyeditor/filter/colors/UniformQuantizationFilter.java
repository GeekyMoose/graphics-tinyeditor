package main.java.com.tinyeditor.filter.colors;

import main.java.com.tinyeditor.filter.asset.ImageFilter;
import main.java.com.tinyeditor.filter.asset.FilterHelper;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class UniformQuantizationFilter implements ImageFilter{
	private int rPallet;
	private int gPallet;
	private int bPallet;

	public UniformQuantizationFilter(){
		this.setRedK(42);
		this.setGreenK(42);
		this.setBlueK(42);
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

		int r,g,b;
		//Process each pixel
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				Color color = pixelReader.getColor(x,y);

				r = (int)(color.getRed() * 255);
				g = (int)(color.getGreen() * 255);
				b = (int)(color.getBlue() * 255);

				r = this.findClosestPalletColor(r, this.rPallet);
				g = this.findClosestPalletColor(g, this.bPallet);
				b = this.findClosestPalletColor(b, this.gPallet);

				pixelWriter.setColor(x,y,Color.rgb(r,g,b));
			}
		}
		return wimage;
	}

	/**
	 * Find value that is close to the one in Pallet
	 */
	private int findClosestPalletColor(int value, int pallet){
		int k = (int)Math.round(value/pallet);
		int z = value - k;
		if(z > (pallet/2)){
			return FilterHelper.limit255Value((k+1) * pallet);
		}
		return FilterHelper.limit255Value(k * pallet);
	}

	/**
	 * Split 256 values into K values
	 */
	private int makeNewRange(int k, int pallet){
		return pallet / k;
	}


	// ************************************************************************
	// Getters - Setters
	// ************************************************************************
	public void setRedK(int value){
		this.rPallet = 255/value;
	}
	public void setGreenK(int value){
		this.gPallet = 255/value;
	}
	public void setBlueK(int value){
		this.bPallet = 255/value;
	}
}

