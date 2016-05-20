package com.tinyeditor.modules.filling;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Arrays;

/**
 * Draw a Polygon using Scanline Algorithm.
 * The given polygon is a list of points.
 *
 * Algorithm is inspired from:
 * https://www.cs.rit.edu/~icss571/filling/index.html
 * http://rsb.info.nih.gov/ij/developer/source/ij/process/PolygonFiller.java.html
 *
 * @since   May 18, 2016
 * @author  Constantin MASSON
 */
public class ScanlineFill {
	// *************************************************************************
	// Attributes
	// *************************************************************************
	//Polygon data
	private int         nbVertices; //Total number of vertices
	private int[]       xPos; //X position of each polygon vertices
	private int[]       yPos; //Y position of each polygon vertices

	//Edge table
	private int         nbEdges;
	private int[]       ymin;
	private int[]       ymax;
	private double[]    xminy;
	private double[]    rSlope;

	//Active Edge Table
	private int[]       aet; //Index of elements in AET
	private int         nbActivesEdges; //Number of active edge in AET


	// *************************************************************************
	// Initialization - Constructors
	// *************************************************************************
	public ScanlineFill(int[] x, int[] y){
		this.xPos = x;
		this.yPos = y;
		this.nbVertices = this.xPos.length;
		this.initArrayData(this.nbVertices);
		this.buildEdgeTable();
	}


	/**
	 * Initialize all the array data.
	 *
	 * @param size Size of the array.
	 */
	private void initArrayData(int size){
		this.ymin   = new int[size];
		this.ymax   = new int[size];
		this.xminy  = new double[size];
		this.rSlope = new double[size];
		this.aet    = new int[size];
	}

	/**
	 * Build the Edge Table.
	 * Reset nb actives edge to 0.
	 */
	private void buildEdgeTable(){
		int iplus1; //Used to manage last case (Last vertices - first one)
		int x1, y1; //First vertices position
		int x2, y2; //Second vertices position
		nbEdges = 0;
		for(int i=0; i<nbVertices; i++){
			iplus1 = (i==nbVertices-1) ? 0 : i+1; //Handle case last vertices
			y1 = yPos[i]; y2 = yPos[iplus1];
			x1 = xPos[i]; x2 = xPos[iplus1];
			if(y1==y2){
				//Ignore horizontal lines
				continue;
			}
			if(y1>y2){
				//p1(x1,y1) and p2(x2,y2) must be sorted using y value
				int tmp = y1;
				y1=y2; y2=tmp;
				tmp=x1;
				x1=x2; x2=tmp;
			}
			//Create the actual data and add this edge in array
			double slope    = (x2-x1)/(double)(y2-y1); //(double) is important
			ymax[nbEdges]   = y2;
			ymin[nbEdges]   = y1;
			xminy[nbEdges]  = y1 < y2 ? x1 : x2;
			rSlope[nbEdges] = slope;
			nbEdges++;
		}
		nbActivesEdges = 0;
	}


	// *************************************************************************
	// Active Edge Table functions
	// *************************************************************************
	/**
	 * Adds edges to the active edge table.
	 *
	 * @param y New y position.
	 */
	private void activateEdges(int y){
		//Check each edge from global edge table.
		for(int edge=0; edge<nbEdges; edge++){
			//If this edge should be added in AET
			if(y == ymin[edge]){
				int index = 0;
				//Get index where to place (Elt are sorted by x value)
				while(index < nbActivesEdges && xminy[edge]>xminy[aet[index]]){
					index++; //Recover the last position in AET
				}
				//Recreate the array after index position
				for(int k=(nbActivesEdges-1); k>=index; k--){
					aet[k+1] = aet[k];
				}
				aet[index] = edge;
				nbActivesEdges++;
			}
		}
	}

	/**
	 * Removes edges from the active edge table that are no longer needed.
	 *
	 * @param y New y position.
	 */
	private void removeInactiveEdges(int y){
		int i=0;
		while(i<nbActivesEdges){
			int index = aet[i];
			if(y<ymin[index] || y>=ymax[index]){
				//Reset aet array without this value
				for(int k=i; k<(nbActivesEdges-1); k++){
					aet[k] = aet[k+1];
				}
				nbActivesEdges--;
			} else {
				i++;
			}
		}
	}

	/**
	 * Update all x coordinate for element in Active Edge Table.
	 */
	private void updateXCoordinates(){
		int index;
		double x1 = -Double.MAX_VALUE, x2;
		boolean sorted = true;
		for (int i=0; i<nbActivesEdges; i++) {
			index = aet[i];
			x2 = xminy[index] + rSlope[index];
			xminy[index] = x2;
			if (x2<x1){
				sorted = false;
			}
			x1 = x2;
		}
		if(!sorted){
			//Sort if need to be done.
			this.sortActiveEdges();
		}
	}

	/**
	 * Sorts the actives edges list by x coordinate using a selection sort.
	 */
	private void sortActiveEdges(){
		int min, tmp;
		for(int k=0; k<nbActivesEdges; k++){
			min = k;
			for(int j=k; j<nbActivesEdges; j++){
				if(xminy[aet[min]]>xminy[aet[j]]){
					min = j;
				}
			}
			tmp     = aet[k];
			aet[k]  = aet[min];
			aet[min]= tmp;
		}
	}



	// *************************************************************************
	// Functions Draw
	// *************************************************************************
	/**
	 * Draw the filled polygon.
	 *
	 * @param image Image where to draw.
	 * @param color Color to fill with.
	 * @return      The new image.
	 */
	public WritableImage draw(Image image, Color color){
		int height  = (int) image.getHeight();
		int width   = (int) image.getWidth();
		//Set the pixel reader and writer
		PixelReader     pixelReader = image.getPixelReader();
		WritableImage   wimage      = new WritableImage(pixelReader, width, height);
		PixelWriter     pixelWriter = wimage.getPixelWriter();

		//Recover the min and max value from the polygon vertices.
		int minY = Arrays.stream(yPos).min().getAsInt();
		int maxY = Arrays.stream(yPos).max().getAsInt();
		int xStart, xStop;

		//Draw pixel from odd value edge to even.
		for(int y = minY; y < maxY; y++){
			this.removeInactiveEdges(y);
			this.activateEdges(y);
			for(int k=0; k<nbActivesEdges; k+=2){
				//Deal with all 2 aet row
				xStart  = (int)(xminy[aet[k]]);
				xStop   = (int)(xminy[aet[k+1]]);
				for(int x=xStart; x<=xStop; x++){
					//Draw the line
					pixelWriter.setColor(x, y, color);
				}
			}
			this.updateXCoordinates();
		}
		return wimage;
	}
}
