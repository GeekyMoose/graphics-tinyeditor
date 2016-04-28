package com.tinyeditor.modules.draw;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * @since   Apr 27, 2016
 * @author  Constantin MASSON
 */
public class DrawLine {
	private static int[] p1 = null;
	private static int[] p2 = null;

	public static WritableImage drawMidPointLine(Image image, int x, int y){
		int height	= (int)image.getHeight();
		int width	= (int)image.getWidth();
		//Set the pixel reader and writer
		PixelReader     pixelReader	= image.getPixelReader();
		WritableImage   wimage		= new WritableImage(width, height);
		PixelWriter     pixelWriter = wimage.getPixelWriter();

		//Copy editor
		for(int j=0; j<height; j++){
			for(int i=0; i<width; i++){
				pixelWriter.setColor(i,j,pixelReader.getColor(i,j));
			}
		}

		//Process draw
		if (DrawLine.p1 == null){
			DrawLine.p1 = new int[2];
			DrawLine.p1[0] = x;
			DrawLine.p1[1] = y;
			return wimage;
		}
		DrawLine.p2 = new int[2];
		DrawLine.p2[0] = x;
		DrawLine.p2[1] = y;
		DrawLine.midpointLine(pixelWriter, p1[0], p1[1], p2[0], p2[1]);
		p1 = null;
		p2 = null;
		return wimage;
	}

	private static void midpointLine(PixelWriter pw, int x1, int y1, int x2, int y2){
		int dy = y2-y1;
		int dx = x2-x1;
		int p = dy-dx/2;
		int y=y1;
		for (int x=x1; x <=x2; x++) {
			pw.setColor(x,y, Color.BLACK);
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
