package com.tinyeditor.modules.filling;


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
	private int     nbEdges;
	private int[]   xPos; //X position of each polygon vertices
	private int[]   yPos; //Y position of each polygon vertices

	private LinkedList<RowAET> globalEdgeTable;


	public ScanlineFill(int[] x, int[] y){
		this.nbEdges = x.length;
		this.xPos = x;
		this.yPos = y;
		this.initGlobalEdgeTable();
	}

	private void initGlobalEdgeTable(){
		this.globalEdgeTable = new LinkedList<>(); //Create the list of edge
		//Create RowAET for each edge.
		// %nbEdge cuz last edge use last Point and first Point.
		for(int k=0; k<(nbEdges); k++){
			RowAET tmp = new RowAET(xPos[k], yPos[k], xPos[(k+1)%nbEdges], yPos[(k+1)%nbEdges]);
			// Do not add in table id 1/m is infinite.
			if(tmp.rSlope != tmp.INFINITE){
				this.globalEdgeTable.add(tmp);
			}
		}
		//Sort the edge
		Collections.sort(this.globalEdgeTable);
	}


	// -------------------------------------------------------------------------
	// Nested class
	// -------------------------------------------------------------------------
	private class RowAET implements Comparable <RowAET>{
		// *********************************************************************
		// Attributes (Package local scope)
		// *********************************************************************
		//Asset constants
		int      INFINITE    = -42;

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
				this.rSlope = INFINITE;
				return;
			}
			this.rSlope = (double)dx/dy; //Cuz 1/m and m = dy/dx
		}

		@Override
		// -1 inf, 0 equals, +1 sup
		/*
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
			if(this.ymin > o.ymin){
				return 1;
			}
			if(this.ymin < o.ymin){
				return -1;
			}
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
