package com.tinyeditor.draw;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * @since   Apr 27, 2016
 * @author  Constantin MASSON
 */
public class DrawCirle {

	public static WritableImage drawMidPointLine(Image image, int x, int y, int radius) {
		int height = (int) image.getHeight();
		int width = (int) image.getWidth();
		//Set the pixel reader and writer
		PixelReader pixelReader = image.getPixelReader();
		WritableImage wimage = new WritableImage(width, height);
		PixelWriter pixelWriter = wimage.getPixelWriter();

		//Copy image
		for (int j = 0; j < height; j++) {
			for (int i = 0; i < width; i++) {
				pixelWriter.setColor(i, j, pixelReader.getColor(i, j));
			}
		}
		DrawCirle.midpointCircle(pixelWriter, x, y, radius);
		return wimage;
	}

	private static void midpointCircle(PixelWriter pw, int x0, int y0, int radius){
		int x = 0, y = radius;
		int dp = 1 - radius;
		do
		{
			if (dp < 0) {
				dp = dp + 2 * (++x) + 3;
			}
			else {
				dp = dp + 2 * (++x) - 2 * (--y) + 5;
			}

			pw.setColor(x0 + x, y0 + y, Color.BLACK);
			pw.setColor(x0 - x, y0 + y, Color.BLACK);
			pw.setColor(x0 + x, y0 - y, Color.BLACK);
			pw.setColor(x0 - x, y0 - y, Color.BLACK);
			pw.setColor(x0 + y, y0 + x, Color.BLACK);
			pw.setColor(x0 - y, y0 + x, Color.BLACK);
			pw.setColor(x0 + y, y0 - x, Color.BLACK);
			pw.setColor(x0 - y, y0 - x, Color.BLACK);

		} while (x < y);
	}
}
