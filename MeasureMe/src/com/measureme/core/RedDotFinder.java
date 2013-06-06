package com.measureme.core;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class RedDotFinder {
	 public static List<Point> findRedDots(ImageInterface image) {
		 List<Point> redDotCenters = new ArrayList<Point>();
		 List<Point> redPixels = new ArrayList<Point>();
		 
		 // first, find all of the red pixels
		 for (int i = 0; i < image.getHeight(); i++) {
			 for (int j = 0; j < image.getWidth(); j++) {
				 if (ColorTester.isRed(image.getPixel(j, i))) {
					 redPixels.add(new Point(j, i));
				 }
			 }
		 }
		 System.out.println("Found " + redPixels.size() + " red pixels");
		 
		 // second, group all neighborng pixels together
		 while (!redPixels.isEmpty()) {
			 Point point = redPixels.remove(redPixels.size() - 1);
			 NeighboringPoints neighbors = new NeighboringPoints(point);
			 int redPixelSizeFirst;
			 do {
				 redPixelSizeFirst = redPixels.size();
				 for (int i = redPixels.size() - 1; i >= 0; i--) {
					 if (neighbors.neighbors(redPixels.get(i))) {
						 Point p = redPixels.remove(i);
						 neighbors.add(p);
					 }
				 }
			 }
			 while (redPixelSizeFirst != redPixels.size());
			 
			 if (isRedDot(neighbors, image)) {
				 Point center = neighbors.getCenterPoint();
				 System.out.println(neighbors);
				 redDotCenters.add(center);
			 }
		 }
		 return redDotCenters;
	 }
	 
	 private static boolean isRedDot(NeighboringPoints points, ImageInterface image) {
		 if (points.getSize() > 7) {
			 return true;
			 /*Point centerPoint = points.getCenterPoint();
			 int outerRadius = (int)points.getRadius() + 3;
			 Point upperPoint = new Point(centerPoint.x, Math.min(centerPoint.y + outerRadius, image.getHeight() - 1));
			 Point lowerPoint = new Point(centerPoint.x, Math.max(centerPoint.y - outerRadius, 0));
			 Point rightPoint = new Point(Math.min(centerPoint.x + outerRadius, image.getWidth() - 1), centerPoint.y);
			 Point leftPoint = new Point(Math.max(centerPoint.x - outerRadius, 0), centerPoint.y);
			 return ColorTester.isblue(image.getPixel(upperPoint)) && ColorTester.isblue(image.getPixel(lowerPoint)) && ColorTester.isblue(image.getPixel(rightPoint)) && ColorTester.isblue(image.getPixel(leftPoint));*/
		 }
		 return false;
	 }
	 
	 private static class NeighboringPoints {
		 private static final double MIN_NEIGHBORING_DISTANCE = 20;
		List<Point> points;
		 Point2D.Double centerPoint;
		 double radius;
		 
		 public NeighboringPoints(Point p) {
			 points = new ArrayList<Point>();
			 radius = 0;
			 centerPoint = new Point2D.Double(p.x, p.y);
			 points.add(p);
		 }
		 
		 public double getRadius() {
			// TODO Auto-generated method stub
			return 0;
		}

		public void add(Point p) {
			 centerPoint.x = ((centerPoint.x * points.size()) + p.x) / (points.size() + 1);
			 centerPoint.y = ((centerPoint.y * points.size()) + p.y) / (points.size() + 1);
			 radius = Math.max(radius, centerPoint.distance(p));
			 points.add(p);
		 }
		 
		 public boolean neighbors(Point p) {
			 double distance = p.distance(centerPoint);
			 if (distance < MIN_NEIGHBORING_DISTANCE) {
				 return true;
			 }
			 return false;
		 }
		 
		 public Point getCenterPoint() {
			 return new Point((int)Math.round(centerPoint.x), (int)Math.round(centerPoint.y));
		 }
		 
		 public int getSize() {
			 return points.size();
		 }
		 
		 @Override
		 public String toString() {
			 return String.format("Neighboring(%d,  %d), - radius %d, has %d pixels", (int)centerPoint.x, (int)centerPoint.y, (int)radius, points.size());
		 }
	 }
}
