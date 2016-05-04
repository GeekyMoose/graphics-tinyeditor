package com.tinyeditor.modules.draw;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Draw a Line using Midpoint Line Algorithm.
 *
 * @since   Apr 27, 2016
 * @author  Constantin MASSON
 */
public class MidpointLine {

	/**
	 * Draw one line in the given image using Midpoint line Algorithm.
	 *
	 * @param image Image where to draw.
	 * @param x1    Start point.
	 * @param y1    Start point.
	 * @param x2    End point.
	 * @param y2    End point.
	 * @param color Color to apply.
	 * @return      The modified image.
	 */
	public static WritableImage draw(Image image, int x1, int y1, int x2, int y2, Color color){
		int height	= (int)image.getHeight();
		int width	= (int)image.getWidth();
		//Set the pixel reader and writer
		PixelReader     pixelReader = image.getPixelReader();
		WritableImage   wimage      = new WritableImage(width, height);
		PixelWriter     pixelWriter = wimage.getPixelWriter();

		//Copy the image in the new Writable Image before drawing (Otherwise, loose it)
		for(int j=0; j<height; j++){
			for(int i=0; i<width; i++){
				pixelWriter.setColor(i,j,pixelReader.getColor(i,j));
			}
		}

		//Draw on the image and return it.
		MidpointLine.applyAlgo(pixelWriter, x1, y1, x2, y2, color);
		return wimage;
	}

	/* Apply the algorithm on the image */
	private static void applyAlgo(PixelWriter pw, int x1, int y1, int x2, int y2, Color color){
		int dy = y2-y1;
		int dx = x2-x1;
		int p = dy-dx/2;
		int y=y1;
		for (int x=x1; x <=x2; x++) {
			pw.setColor(x,y, color);
			if(p > 0) {
				y++;
				p += dy-dx;
			}
			else {
				p += dy;
			}
		}
	}
}
