package main.java.com.tinyeditor.filter.dithering;

import main.java.com.tinyeditor.filter.asset.ImageFilter;
import main.java.com.tinyeditor.filter.asset.FilterHelper;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class OrderedDitheringFilter implements ImageFilter{
	private int[][] bayerMatrix;

	public OrderedDitheringFilter(){
		this.bayerMatrix = new int[][]{
			{3,7,4},
			{6,1,9},
			{2,8,5}
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

		int r,g,b; //tmp values used for process
		int[] thresholds = new int[]{127};
		//Process each pixel
		for(int y=0; y<height; y++){
			for(int x=0; x<width; x++){
				Color color = pixelReader.getColor(x,y);
				r = (int)(color.getRed() * 255);
				g = (int)(color.getGreen() * 255);
				b = (int)(color.getBlue() * 255);

				r += (this.bayerMatrix[y%3][x%3]/3) * r;
				g += (this.bayerMatrix[y%3][x%3]/3) * r;
				b += (this.bayerMatrix[y%3][x%3]/3) * r;

				r = thresholdTransform(r, thresholds, 127);
				g = thresholdTransform(r, thresholds, 127);
				b = thresholdTransform(r, thresholds, 127);

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
}
