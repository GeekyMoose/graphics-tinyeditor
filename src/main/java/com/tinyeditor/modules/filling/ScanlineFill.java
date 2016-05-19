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


		//@TODO DEBUG : MOCK : TO DELETE LATER
		this.xPos = new int[6];
		this.yPos = new int[6];
		this.xPos[0] = 10; this.yPos[0] = 10;
		this.xPos[1] = 10; this.yPos[1] = 16;
		this.xPos[2] = 16; this.yPos[2] = 20;
		this.xPos[3] = 28; this.yPos[3] = 10;
		this.xPos[4] = 28; this.yPos[4] = 16;
		this.xPos[5] = 22; this.yPos[5] = 10;
		this.initArrayData(this.nbVertices);
		//@TODO DEBUG: END

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
				// swap ends
				int tmp = y1;
				y1=y2; y2=tmp;
				tmp=x1;
				x1=x2; x2=tmp;
			}
			//Create the actual data and add this edge in array
			double slope    = (double)(x2-x1)/(y2-y1);
			ymax[nbEdges]   = y1;
			ymin[nbEdges]   = y2;
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
		for(int k=0; k<nbEdges; k++){
			int edge = k;
			if(y == ymin[k]){
				int index = 0;
			}
		}
	}

	/**
	 * Removes edges from the active edge table that are no longer needed.
	 *
	 * @param y New y position.
	 */
	private void removeInactiveEdges(int y){

	}



	// *************************************************************************
	// Functions
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

		//Draw pixel from odd value edge to even.
		for(int yLine = minY; yLine < maxY; yLine++){
			/*
			this.updateAET(yLine);

			for(int k=0; k<this.nbActives; k+=2){
				int xstart = this.globalEdgeTable.get(this.AET[k]).xval;
				int xstop = this.globalEdgeTable.get(this.AET[k+1]).xval;

				//@TODO DEBUG
				System.out.println("start: "+xstart+", stop: "+xstop);

				for(int posX=xstart; posX<=xstop; posX++){
					System.out.print(" "+posX);
					pixelWriter.setColor(posX, yLine, color);
				}
				System.out.println("");
			}
			this.updateXpositions();
			*/
		}
		return wimage;
	}
}
