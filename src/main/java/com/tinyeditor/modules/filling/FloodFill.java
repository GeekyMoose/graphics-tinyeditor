package com.tinyeditor.modules.filling;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.LinkedList;

/**
 * Implements a FloodFill Algorithm.
 *
 * Algorithm inspired from:
 * https://en.wikipedia.org/wiki/Flood_fill
 *
 * @since   May 18, 2016
 * @author  Constantin MASSON
 */
public class FloodFill {

	/**
	 * Apply a 4 connection FloodFill algorithm.
	 *
	 * @param image         Image to modify
	 * @param x             X origin position
	 * @param y             Y origin position
	 * @param targetColor   Color to modify (Color under x,y position)
	 * @param replaceColor  Color to apply
	 * @return              The new image.
	 */
	public static WritableImage floodFill4(Image image, int x, int y, Color targetColor, Color replaceColor){
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

		//Keep in memory if pixel was visited.
		boolean[][] visited = new boolean[height][width];

		//Each node to process is placed in the queue.
		//Queue save data as FIFO, formatted as: x,y
		LinkedList<Integer> queue = new LinkedList<>(); //Add x,y inside
		queue.add(x);
		queue.add(y);

		//Main loop. If still elements to process, continue.
		while (!queue.isEmpty()){
			x = queue.remove();
			y = queue.remove();

			if(x<0 || x>=width || y<0 || y>=height){
				continue; //Not in the image
			}

			//Get current color and check if should be modified.
			Color current = pixelReader.getColor(x,y);
			if(!visited[y][x] && current.equals(targetColor)){
				pixelWriter.setColor(x, y, replaceColor);
				visited[y][x] = true;

				//Add the 4 neighbors in the queue. (in queue: x then y)
				queue.add(x); queue.add(y+1); //UP
				queue.add(x); queue.add(y-1); //DOWN
				queue.add(x-1); queue.add(y); //LEFT
				queue.add(x+1); queue.add(y); //RIGHT
			}
		}
		return wimage;
	}

	/**
	 * Apply a 8 connection FloodFill algorithm.
	 *
	 * @param image         Image to modify
	 * @param x             X origin position
	 * @param y             Y origin position
	 * @param targetColor   Color to modify (Color under x,y position)
	 * @param replaceColor  Color to apply
	 * @return              The new image.
	 */
	public static WritableImage floodFill8(Image image, int x, int y, Color targetColor, Color replaceColor){
		int height  = (int) image.getHeight();
		int width   = (int) image.getWidth();
		//Set the pixel reader and writer
		PixelReader     pixelReader = image.getPixelReader();
		WritableImage   wimage      = new WritableImage(pixelReader, width, height);
		PixelWriter     pixelWriter = wimage.getPixelWriter();

		//Keep in memory if pixel was visited.
		boolean[][] visited = new boolean[height][width];

		//Each node to process is placed in the queue.
		//Queue save data as FIFO, formatted as: x,y
		LinkedList<Integer> queue = new LinkedList<>(); //Add x,y inside
		queue.add(x);
		queue.add(y);

		//Main loop. If still elements to process, continue.
		while (!queue.isEmpty()){
			x = queue.remove();
			y = queue.remove();

			if(x<0 || x>=width || y<0 || y>=height){
				continue; //Not in the image
			}

			//Get current color and check if should be modified.
			Color current = pixelReader.getColor(x,y);
			if(!visited[y][x] && current.equals(targetColor)){
				pixelWriter.setColor(x, y, replaceColor);
				visited[y][x] = true;

				//Add the 4 neighbors in the queue. (in queue: x then y)
				queue.add(x); queue.add(y+1); //UP
				queue.add(x); queue.add(y-1); //DOWN
				queue.add(x-1); queue.add(y); //LEFT
				queue.add(x+1); queue.add(y); //RIGHT

				queue.add(x-1); queue.add(y+1); //UP LEFT
				queue.add(x+1); queue.add(y+1); //UP RIGHT
				queue.add(x-1); queue.add(y-1); //DOWN LEFT
				queue.add(x+1); queue.add(y-1); //DOWN RIGHT
			}
		}
		return wimage;
	}
}
