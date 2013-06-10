package com.measureme.core;

import java.awt.Point;
import java.awt.geom.Point2D;

// TODO: This is not going to work for non-full squares
public class Square {

	private String mDebugName;
	private Point2D.Double mUpperLeft;
	private Point2D.Double mUpperRight;
	private Point2D.Double mLowerLeft;
	private Point2D.Double mLowerRight;
	
	public Square(Point2D.Double upperLeft, Point2D.Double upperRight, Point2D.Double lowerLeft, String debugName) {
		mUpperLeft = upperLeft;
		mUpperRight = upperRight;
		mLowerLeft = lowerLeft;
		mLowerRight = new Point2D.Double(mUpperLeft.x - (mUpperLeft.x - mLowerLeft.x),  mLowerLeft.y + (mUpperRight.y - mUpperLeft.y));
		mDebugName = debugName;
	}

	public String getDebugName() {
		return mDebugName;
	}

	public double getDistancePixelsTo(Square square, Direction directionFrom) {
		System.out.println("Direcriont from " + directionFrom + " " + this + " to " + square);
		// for now, we get the mid points of all of the edges, and find the minimum distance from each of the mid points to the other mid points.
		if (directionFrom == Direction.Right) {
			Point2D.Double thisRightMid = getMidPoint(mUpperRight, mLowerRight);
			Point2D.Double otherLeftMid = getMidPoint(square.mLowerLeft, square.mUpperLeft);
			System.out.println(String.format("Distance from (%2f, %2f) to (%2f, %2f) from %s", thisRightMid.x, thisRightMid.y, otherLeftMid.x, otherLeftMid.y, directionFrom));
			return thisRightMid.distance(otherLeftMid);
		}
		if (directionFrom == Direction.Left) {
			Point2D.Double otherRightMid = getMidPoint(square.mUpperRight, square.mLowerRight);
			Point2D.Double thisLeftMid = getMidPoint(mLowerLeft, mUpperLeft);
			return otherRightMid.distance(thisLeftMid);
		}
		if (directionFrom == Direction.Up) {
			Point2D.Double thisUpMid = getMidPoint(mUpperRight, mUpperLeft);
			Point2D.Double otherDownMid = getMidPoint(square.mLowerLeft, square.mLowerRight);
			return thisUpMid.distance(otherDownMid);
		}
		if (directionFrom == Direction.Down) {
			Point2D.Double otherUpMid = getMidPoint(square.mUpperRight, square.mUpperLeft);
			Point2D.Double thisDownMid = getMidPoint(mLowerLeft, mLowerRight);
			System.out.println(String.format("Distance from (%2f, %2f) to (%2f, %2f) from %s", otherUpMid.x, otherUpMid.y, thisDownMid.x, thisDownMid.y, directionFrom));
			return thisDownMid.distance(otherUpMid);
		}
		return -1;
	}
	
	private static Point2D.Double getMidPoint(Point2D.Double p1, Point2D.Double p2) {
		return new Point2D.Double((p1.x + p2.x)/2, (p1.y + p2.y)/2); 
	}

	// for now return the largest size
	public double getSizePixels() {
		return Math.max(Math.max(mUpperLeft.distance(mUpperRight), mLowerLeft.distance(mLowerRight)), Math.max(mUpperRight.distance(mLowerRight), mUpperLeft.distance(mLowerLeft)));
	}
	
	@Override
	public String toString() {
		return String.format("Square(%s) - [%s, %s, %s, %s]", mDebugName, mUpperLeft, mUpperRight, mLowerLeft, mLowerRight);
	}
}
