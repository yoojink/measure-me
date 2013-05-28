package com.measureme.core;

import java.awt.Point;

// TODO: This is not going to work for non-full squares
public class Square {

	private String mDebugName;
	public Point[] mPoints;
	
	public Square(Point[] points, String debugName) {
		mPoints = points;
		mDebugName = debugName;
	}

	public String getDebugName() {
		return mDebugName;
	}

	public double getDistancePixelsTo(Square square) {
		// for now, we get the mid points of all of the edges, and find the minimum distance from each of the mid points to the other mid points.
		Point[] midPoints1 = getMidPoints();
		Point[] midPoints2 = square.getMidPoints();
		double minDistance = Double.MAX_VALUE;
		for (int i = 0; i < midPoints1.length; i ++) {
			for (int j = 0; j < midPoints2.length; j++) {
				minDistance = Math.min(minDistance, midPoints1[i].distance(midPoints2[j]));
			}
		}
		return minDistance;
	}
	
	public Point[] getMidPoints() {
		Point[] midPoints = new Point[4];
		for (int i = 0; i < mPoints.length; i++) {
			int first = i;
			int second = (i + 1) % mPoints.length;
			midPoints[i] = new Point((mPoints[first].x + mPoints[second].x)/2, (mPoints[first].y + mPoints[second].y)/2); 
		}
		return midPoints;
	}

	// for now return the largest size
	public double getSizePixels() {
		double maxLength = 0;
		for (int i = 0; i < mPoints.length; i++) {
			int first = i;
			int second = (i + 1) % mPoints.length;
			maxLength = Math.max(maxLength, mPoints[first].distance(mPoints[second]));
		}
		return maxLength;
	}
	
	@Override
	public String toString() {
		return String.format("Square(%s) - [%s, %s, %s, %s]", mDebugName, mPoints[0], mPoints[1], mPoints[2], mPoints[3]);
	}
}
