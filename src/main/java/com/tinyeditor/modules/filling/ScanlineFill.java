package com.tinyeditor.modules.filling;


import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Draw a Polygon using Scanline Algorithm.
 * The given polygon is a list of points.
 *
 * Algorithm is inspired from:
 * https://www.cs.rit.edu/~icss571/filling/index.html
 *
 * @since   May 18, 2016
 * @author  Constantin MASSON
 */
public class ScanlineFill {
	// *************************************************************************
	// Attributes
	// *************************************************************************
	private int     nbEdges;
	private int[]   xPos; //X position of each polygon vertices
	private int[]   yPos; //Y position of each polygon vertices

	private LinkedList<RowAET> globalEdgeTable;
	private int startAET; //Index of the first element in Active Edge Table (Included)
	private int lastAET; //Index of the last element currently in AET (Excluded)


	// *************************************************************************
	// Initialization - Constructors
	// *************************************************************************
	public ScanlineFill(int[] x, int[] y){
		this.nbEdges = x.length;
		this.xPos = x;
		this.yPos = y;
		this.startAET = this.lastAET = 0;
		this.initGlobalEdgeTable();
	}

	/**
	 * Initialize the Global Edge Table.
	 * All edge are added (Except horizontal one). Then, list is sorted.
	 */
	private void initGlobalEdgeTable(){
		this.globalEdgeTable = new LinkedList<>(); //Create the list of edge
		//Create RowAET for each edge.
		// modulo %nbEdge cuz last edge use last Point and first Point.
		for(int k=0; k<(nbEdges); k++){
			RowAET tmp = new RowAET(xPos[k], yPos[k], xPos[(k+1)%nbEdges], yPos[(k+1)%nbEdges]);
			// Do not add in table id 1/m is infinite. (Horizontal)
			if(tmp.rSlope != tmp.INFINITE){
				this.globalEdgeTable.add(tmp);
			}
		}
		//Sort the edge
		Collections.sort(this.globalEdgeTable);

		//@TODO DEBUG - TO DELETE LATER
		System.out.println("DEBUG::::AFTER");
		for(RowAET ro : this.globalEdgeTable){
			System.out.println(ro.toString());
		}
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
		this.startAET = this.lastAET = 0; //Init value for active AET

		//Draw pixel from odd value edge to even.
		for(int yLine = minY; yLine< maxY; yLine++){
			this.updateAET(yLine);
			int nbActiveEdges = this.lastAET - this.startAET;
			for(int k=0; k<nbActiveEdges; k=k+2){
				int xstart = this.globalEdgeTable.get(this.startAET+k).xval;
				int xstop = this.globalEdgeTable.get(this.startAET+k+1).xval;
				for(int posX=xstart; posX<xstop; posX++){
					pixelWriter.setColor(posX, yLine, color);
				}
			}
		}
		return wimage;
	}

	/**
	 * Update the AET for the new position y.
	 *
	 * @param y New position to use.
	 */
	private void updateAET(int y){
		//Get the first row where ymin is equals to current y
		for(int k = this.startAET; k<this.globalEdgeTable.size(); k++){
			if(this.globalEdgeTable.get(k).ymin == y){
				this.startAET = k;
				break;
			}
		}

		//Get the first row where ymin is greater than current y
		for(int k = this.lastAET; k<this.globalEdgeTable.size(); k++){
			if(this.globalEdgeTable.get(k).ymin <= y){
				continue;
			}
			this.lastAET = k;
			break;
		}


		//Update the x position for all element in active table
		for(int k = this.startAET; k<this.lastAET; k++){
			RowAET tmp = this.globalEdgeTable.get(k);
			this.globalEdgeTable.get(k).xval = (int)(tmp.xval + tmp.rSlope);
		}
	}



	// -------------------------------------------------------------------------
	// Nested class
	// -------------------------------------------------------------------------
	private class RowAET implements Comparable <RowAET>{
		// *********************************************************************
		// Attributes (Package local scope)
		// *********************************************************************
		//Asset constants
		int      INFINITE    = -42; //Horizontal edge

		int      ymax; //Max y value between 2 vertices
		int      ymin; //Min y value between 2 vertices
		int      xval; //x value at the ymin
		double   rSlope; // 1/slope. -- slope = (ymax - ymin) / (xmax - xmin).


		// *********************************************************************
		// Constructor - Initialization
		// *********************************************************************

		/**
		 * Create a new row for the edge between P1 and P2
		 *
		 * @param xP1 x coordinate of point 1
		 * @param yP1 y coordinate of point 1
		 * @param xP2 x coordinate of point 2
		 * @param yP2 y coordinate of point 2
		 */
		private RowAET(int xP1, int yP1, int xP2, int yP2){
			this.init(xP1, yP1, xP2, yP2);
		}

		/**
		 * Initialize the value for one AET row.
		 * The managed edge is between point p1 and point p2.
		 *
		 * @param xP1 x coordinate of point 1
		 * @param yP1 y coordinate of point 1
		 * @param xP2 x coordinate of point 2
		 * @param yP2 y coordinate of point 2
		 */
		private void init(int xP1, int yP1, int xP2, int yP2){
			//Set the basics data
			this.ymax = (yP1 > yP2) ? yP1 : yP2;
			this.ymin = (yP1 < yP2) ? yP1 : yP2;
			this.xval = (yP1 == this.ymin) ? xP1 : xP2;

			//Calculate the delta values
			int dy = yP2 - yP1;
			int dx = xP2 - xP1;

			//Calculate the rSlope value
			if(dy == 0){
				this.rSlope = INFINITE; //Horizontal edge
				return;
			}
			this.rSlope = (double)dx/dy; //Cuz 1/m and m = dy/dx
		}

		@Override
		/*
		 * -1 inf, 0 equals, +1 sup
		 * This function check whether an edge is greater than another.
		 * This is used to create the Global Edge Table.
		 *
		 * The edge with INFINITE slope shouldn't be used in the table.
		 * compareTo will push them as the lowest values. (We can remove easily).
		 */
		public int compareTo(RowAET o) {
			//If equals
			if(ymax == o.ymax && ymin == o.ymin && xval == o.xval &&rSlope == o.rSlope){
				return 0; //Equals
			}
			//If slope = 0 (rSlope INFINITE), always the lower.
			if(this.rSlope == INFINITE){ return -1; }
			if(this.ymin > o.ymin){ return 1; }
			if(this.ymin < o.ymin){ return -1; }
			//Here, means ymin are equals, we check x value then
			return (this.xval > o.xval) ? 1 : -1;
		}

		@Override
		public String toString(){
			//Note: rSolve infinite value will return it's int representation.
			return "RowAET: ymin: "+ymin+", ymax: "+ymax+", x: "+xval+", 1/m: "+rSlope;
		}
	}
}
