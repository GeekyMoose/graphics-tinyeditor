package com.tinyeditor.modules.draw;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Draw a circle using Midpoint Line Algorithm.
 *
 * @since   Apr 27, 2016
 * @author  Constantin MASSON
 */
public class MidpointCircle {

	/**
	 * Draw a Circle on the given image using Midpoint Circle Algorithm.
	 *
	 * @param image     Image where to draw.
	 * @param x0        Center position X.
	 * @param y0        Center position Y.
	 * @param radius    Radius of the cercle.
	 * @param color     Color to apply.
	 * @return          The new image.
	 */
	public static WritableImage draw(Image image, int x0, int y0, int radius, Color color){
		int height  = (int) image.getHeight();
		int width   = (int) image.getWidth();
		//Set the pixel reader and writer
		PixelReader     pixelReader = image.getPixelReader();
		WritableImage   wimage      = new WritableImage(width, height);
		PixelWriter     pixelWriter = wimage.getPixelWriter();

		//Copy the image in the new Writable Image before drawing (Otherwise, loose it)
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				pixelWriter.setColor(i, j, pixelReader.getColor(i, j));
			}
		}

		//Draw the circle
		MidpointCircle.applyAlgo(pixelWriter, x0, y0, radius, color);
		return wimage;
	}

	private static void applyAlgo(PixelWriter pw, int x0, int y0, int radius, Color color){
		int x = 0, y = radius;
		int dp = 1 - radius;
		do {
			if (dp < 0) {
				dp = dp + 2 * (++x) + 3;
			}
			else {
				dp = dp + 2 * (++x) - 2 * (--y) + 5;
			}

			pw.setColor(x0 + x, y0 + y, color);
			pw.setColor(x0 - x, y0 + y, color);
			pw.setColor(x0 + x, y0 - y, color);
			pw.setColor(x0 - x, y0 - y, color);
			pw.setColor(x0 + y, y0 + x, color);
			pw.setColor(x0 - y, y0 + x, color);
			pw.setColor(x0 + y, y0 - x, color);
			pw.setColor(x0 - y, y0 - x, color);

		} while (x < y);
	}
}
