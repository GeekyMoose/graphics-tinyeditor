package com.tinyeditor.modules.draw;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Draw a line using Gupta-Sproul Algorithm (Anti-aliasing).
 *
 * @since   Apr 27, 2016
 * @author  Constantin MASSON
 */
public class GuptaSproull {

	/**
	 * Draw a line using Gupta Sproull Algorithm.
	 *
	 * @param image     Image where to draw.
	 * @param x1        X start position.
	 * @param y1        Y start position.
	 * @param x2        X end position.
	 * @param y2        Y end position.
	 * @param color     Color to apply.
	 * @return          New image with the line drawn.
	 */
	public static WritableImage draw(Image image, int x1, int y1, int x2, int y2, Color color){
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
		GuptaSproull.applyFilter(pixelWriter, pixelReader, x1, y1, x2, y2, color);
		return wimage;
	}

	/*
	 * Apply the filter.
	 *
	 * The pixelReader is used in order to know what was the color before drawing.
	 * It is required for the transparency.
	 *
	 * Logic is created thanks to the help of the website topics:
	 * https://courses.engr.illinois.edu/ece390/archive/archive-f2000/mp/mp4/anti.html
	 * http://nokola.com/blog/post/2010/10/14/Anti-aliased-Lines-And-Optimizing-Code-for-Windows-Phone-7e28093First-Look.aspx
	 */
	public static void applyFilter(PixelWriter pw, PixelReader pr, int x1, int y1, int x2, int y2, Color color){
		int dx = x2 - x1;
		int dy = y2 - y1;

		int du, dv, u, x, y, ix, iy;

		// By switching to (u,v), we combine all eight octant
		int adx = dx < 0 ? -dx : dx;
		int ady = dy < 0 ? -dy : dy;
		x = x1;
		y = y1;
		if (adx > ady) {
			du = adx;
			dv = ady;
			u = x2;
			ix = dx < 0 ? -1 : 1;
			iy = dy < 0 ? -1 : 1;
		} else {
			du = ady;
			dv = adx;
			u = y2;
			ix = dx < 0 ? -1 : 1;
			iy = dy < 0 ? -1 : 1;
		}

		int uEnd = u + du;
		int d = (2 * dv) - du; // Initial value as in Bresenham's
		int incrS = 2 * dv; // Δd for straight increments
		int incrD = 2 * (dv - du); // Δd for diagonal increments
		int twovdu = 0; // Numerator of distance
		double invD = 1.0 / (2.0 * Math.sqrt(du * du + dv * dv)); // Precomputed inverse denominator
		double invD2du = 2.0 * (du * invD); // Precomputed constant

		if (adx > ady) {
			do {
				newColorPixel(pw, pr, x, y, twovdu * invD, color);
				newColorPixel(pw, pr, x, y + iy, invD2du - twovdu * invD, color);
				newColorPixel(pw, pr, x, y - iy, invD2du + twovdu * invD, color);

				if (d < 0) {
					// Choose straight
					twovdu = d + du;
					d += incrS;

				} else {
					// Choose diagonal
					twovdu = d - du;
					d += incrD;
					y += iy;
				}
				u++;
				x += ix;
			} while (u < uEnd);
		} else {
			do {
				newColorPixel(pw, pr, x, y, twovdu * invD, color);
				newColorPixel(pw, pr, x, y + iy, invD2du - twovdu * invD, color);
				newColorPixel(pw, pr, x, y - iy, invD2du + twovdu * invD, color);

				if (d < 0) {
					// Choose straight
					twovdu = d + du;
					d += incrS;
				} else {
					// Choose diagonal
					twovdu = d - du;
					d += incrD;
					x += ix;
				}
				u++;
				y += iy;
			} while (u < uEnd);
		}
	}

	/* Get the new color according to the distance from the center */
	private static void newColorPixel(PixelWriter pw, PixelReader pr, int x, int y, double dist, Color c) {
		double value = 1-Math.pow( (dist*2/3), 2);
		Color old = pr.getColor(x, y);
		Color color = new Color(c.getRed(), c.getGreen(), c.getBlue(), 1);
		color = old.interpolate(color, value);
		pw.setColor(x, y, color);
	}
}
