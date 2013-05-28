package com.measureme.core;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SquareFinder {
	static int debugInt = 0;

	public static List<Square> findSquares(List<Point> redDots, ImageInterface image) {
		// the general algorithm, is take a dot, find the closest dot left in the list.
		// see if that dot and this one has blue between it. If yes, add it to the list and
		// keep trying until you have 4 dots that start at the first dot.
		List<Square> squares = new ArrayList<Square>();
		while (redDots.size() >= 4) {
			Point dot = redDots.remove(redDots.size() - 1);
			System.out.println("Starting with point (" + dot.x + ", " + dot.y + ")");
			Point[] dots = new Point[4];
			dots[0] = dot;
			for (int i = 0; i < 3; i ++) {
				Point connectingDot = findConnectingPoint(dot, redDots, image);
				if (connectingDot != null) {
					System.out.println("Found point (" + connectingDot.x + ", " + connectingDot.y + ")");
					redDots.remove(connectingDot);
					dots[i+1] = connectingDot;
					dot = connectingDot;
				}
				else {
					System.out.println("Giving Up");
					break;
				}
			}
			if (dots[3] != null) {
				Square square = new Square(dots, String.valueOf(debugInt++));
				squares.add(square);
				System.out.println("Found square " + square);
			}
		}
		return squares;
	}

	private static Point findConnectingPoint(Point point, List<Point> redDots,
			ImageInterface image) {
		List<Point> points = new ArrayList<Point>(redDots);
		points.addAll(redDots);
		while (!points.isEmpty()) {
			Point p = findClosestPoint(point, points);
			if (pointsConnect(point, p, image)) {
				return p;
			}
			points.remove(p);
		}
		return null;
	}

	// we are making an assumption that if two points connect, they have blue all around them
	// which isn't always true
	private static boolean pointsConnect(Point point1, Point point2, ImageInterface image) {
		int xDiff = point1.x - point2.x;
		int yDiff = point1.y - point2.y;
		int minY = Math.min(point1.y, point2.y);
		int minX = Math.min(point1.x, point2.x);
		int maxY = Math.max(point1.y, point2.y);
		int maxX = Math.max(point1.x, point2.x);
		
		Line2D line = new Line2D.Double();
		line.setLine(point1, point2);
		int blueRed = 0;
		int notBlueRed = 0;
		if (Math.abs(xDiff) > Math.abs(yDiff)) {
			double slope = ((double)(point1.y - point2.y)) / ((double)(point1.x - point2.x));
			double b = (double)point1.y - slope * point1.x;

			// we are x major, so go along x axis;
			for (int i = minX; i < maxX; i++) {
				double y = slope * i + b;
				int yUp = (int) Math.round(y + .5);
				int yDown = (int) y;
				if (!isBlueOrRed(i, yUp, image) && !isBlueOrRed(i, yDown, image) && !isBlueOrRed(i, yUp + 1, image) && !isBlueOrRed(i, yDown -1, image)) {
					notBlueRed++;
				}
				else {
					blueRed++;
				}
			}
		}
		else {
			double slope = (point1.x - point2.x) / (point1.y - point2.y);
			double b = point1.x - slope * point1.y;

			// we are y major, so go along y axis;
			for (int i = minY; i < maxY; i++) {
				double x = slope * i + b;
				int xUp = (int) Math.round(x + .5);
				int xDown = (int) x;
				if (!isBlueOrRed(xUp, i, image) && !isBlueOrRed(xDown, i, image) && !isBlueOrRed(xUp + 1, i, image) && !isBlueOrRed(xDown - 1, i, image)) {
					notBlueRed++;
				}
				else {
					blueRed++;
				}
			}
		}
		return blueRed > notBlueRed;
	}

	private static boolean isBlueOrRed(int x, int y, ImageInterface image) {
		return ColorTester.isblue(image.getPixel(x, y)) || ColorTester.isRed(image.getPixel(x, y));
	}

	private static Point findClosestPoint(Point point, List<Point> points) {
		double minDistance = Double.MAX_VALUE;
		int minPointIndex = -1;
		for (int i = 1; i < points.size(); i++) {
			double distance = point.distance(points.get(i));
			if (distance < minDistance) {
				minDistance = distance;
				minPointIndex = i;
			}
		}
		if (minPointIndex >= 0) {
			return points.get(minPointIndex);
		}
		return null;
	}

}
