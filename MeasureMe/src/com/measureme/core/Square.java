package com.measureme.core;

import java.awt.Point;

public class Square {

	private String mDebugName;
	public Point mUpperRight;
	public Point mUpperLeft;
	public Point mLowerRight;
	public Point mLowerLeft;
	
	public Square(String debugName, Point upperRight, Point upperLeft, Point lowerRight, Point lowerLeft) {
		mDebugName = debugName;
		mUpperRight = upperRight;
		mUpperLeft = upperLeft;
		mLowerRight = lowerRight;
		mLowerLeft = lowerLeft;
	}
	
	public String getDebugName() {
		return mDebugName;
	}

	public double getDistancePixelsTo(Square square) {
		// for now, we get the mid points of all of the edges, and find the minimum distance from each of the mid points to the other mid points.
		double topToBottom = getTopPoint().distance(square.getBottomPoint());
		double bottomToTop = getBottomPoint().distance(square.getTopPoint());
		double rightToLeft = getRightPoint().distance(square.getLeftPoint());
		double leftToRight = getLeftPoint().distance(square.getRightPoint());
		return Math.min(Math.min(topToBottom, bottomToTop), Math.min(rightToLeft, leftToRight));
	}
	
	public Point getTopPoint() {
		return new Point((mUpperRight.x + mUpperLeft.x)/2, (mUpperRight.y + mUpperLeft.y)/2);
	}
	
	public Point getBottomPoint() {
		return new Point((mLowerRight.x + mLowerLeft.x)/2, (mLowerRight.y + mLowerLeft.y)/2);
	}
	
	public Point getRightPoint() {
		return new Point((mLowerRight.x + mUpperRight.x)/2, (mLowerRight.y + mUpperRight.y)/2);
	}
	
	public Point getLeftPoint() {
		return new Point((mLowerLeft.x + mUpperLeft.x)/2, (mLowerLeft.y + mUpperLeft.y)/2);
	}

	// for now return the largest size
	public double getSizePixels() {
		return Math.max(Math.max(mUpperRight.distance(mUpperLeft), mLowerRight.distance(mLowerLeft)), Math.max(mUpperRight.distance(mLowerRight), mUpperLeft.distance(mLowerLeft)));
	}
	
	@Override
	public String toString() {
		return String.format("Square(%s) - [%s, %s, %s, %s]", mDebugName, mUpperLeft, mUpperRight, mLowerLeft, mLowerRight);
	}
}
