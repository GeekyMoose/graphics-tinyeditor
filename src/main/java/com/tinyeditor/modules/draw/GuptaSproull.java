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
		GuptaSproull.applyFilter(pixelWriter, x1, y1, x2, y2, color);
		return wimage;
	}

	public static void applyFilter(PixelWriter pw, int x1, int y1, int x2, int y2, Color color){
	}
	/*
	private static int[] p1 = null;
	private static int[] p2 = null;

	public static WritableImage drawAALine(Image editor, int x, int y){
		int height	= (int)editor.getHeight();
		int width	= (int)editor.getWidth();
		//Set the pixel reader and writer
		PixelReader     pixelReader = editor.getPixelReader();
		WritableImage   wimage      = new WritableImage(width, height);
		PixelWriter     pixelWriter = wimage.getPixelWriter();

		//Copy editor
		for(int j=0; j<height; j++){
			for(int i=0; i<width; i++){
				pixelWriter.setColor(i,j,pixelReader.getColor(i,j));
			}
		}

		//Process draw
		if (GuptaSproull.p1 == null){
			GuptaSproull.p1 = new int[2];
			GuptaSproull.p1[0] = x;
			GuptaSproull.p1[1] = y;
			return wimage;
		}
		GuptaSproull.p2 = new int[2];
		GuptaSproull.p2[0] = x;
		GuptaSproull.p2[1] = y;
		GuptaSproull.aaLine(pixelWriter, p1[0], p1[1], p2[0], p2[1]);
		p1 = null;
		p2 = null;
		return wimage;
	}

	private static void aaLine(PixelWriter pw, int x0, int y0, int x1, int y1) {
		//Algo inspired from
		// https://courses.engr.illinois.edu/ece390/archive/archive-f2000/mp/mp4/anti.html

		int addr = (y0*640+x0)*4;
		int dx = x1-x0;
		int dy = y1-y0;

		int du = 0;
		int dv= 0;
		int u= 0;
		int v= 0;
		int uincr = 0;
		int vincr = 0;

		if (abs(dx) > abs(dy)){
			du = abs(dx);
			dv = abs(dy);
			u = x1;
			v = y1;
			uincr = 4;
			vincr = 640*4;
			if (dx < 0) uincr = -uincr;
			if (dy < 0) vincr = -vincr;
		}
		else{
			du = abs(dy);
			dv = abs(dx);
			u = y1;
			v = x1;
			uincr = 640*4;
			vincr = 4;
			if (dy < 0) uincr = -uincr;
			if (dx < 0) vincr = -vincr;
		}
		int uend = u + 2 * du;
		int d = (2 * dv) - du;
		int incrS = 2 * dv;
		int incrD = 2 * (dv - du);
		int twovdu = 0;
		double invD = 1.0 / (2.0*sqrt(du*du + dv*dv));
		double invD2du = 2.0 * (du*invD);

		do{
			//DrawPixelD(addr, twovdu*invD);
			//DrawPixelD(addr + vincr, invD2du - twovdu*invD);
			//DrawPixelD(addr - vincr, invD2du + twovdu*invD);

			if (d < 0){
				twovdu = d + du;
				d = d + incrS;
			}
			else{
				twovdu = d - du;
				d = d + incrD;
				v = v+1;
				addr = addr + vincr;
			}
			u = u+1;
			addr = addr+uincr;
		} while (u < uend);
	}

	private static void IntensifyPixel(PixelWriter pw, int x, int y, double distance, Color color){
	}
	*/
}
