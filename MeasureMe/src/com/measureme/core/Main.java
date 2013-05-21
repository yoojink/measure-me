package com.measureme.core;

import java.awt.Point;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.measureme.blockdatabase.BlockDatabase;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// get the images somehow, from a directory?
		Collection<ImageInterface> images = null;
		
		for (ImageInterface image : images) {
			List<Point> points = findRedDots(image);
			List<Square> squares = findSquares(points, image);
			
			for (Square square : squares) {
				Block block = decodeBlock(square, image);
				
			}
		}
	}
	
	// find the red dots in the image
	public static List<Point> findRedDots(ImageInterface image) {
		return null;
	}

	// given the list of red dots, convert these into squares where QR codes will live
	public static List<Square> findSquares(List<Point> redDots, ImageInterface image) {
		return null;
	}

	// detect the qr code if possible and find that block's index
	// return null if the qr code cannot be found
	public static Block decodeBlock(Square square, ImageInterface image) {
		return null;
	}
	
	// given the block database, find the height of the suit
	public double getHeight(BlockDatabase database, Map<BlockPair, List<Measurement>> measurements) {
		return -1;
	}

	// given the block database, and all the measurements, find each of the circumferences in the suite
	public static List<Double> getCircumferences(BlockDatabase database, Map<BlockPair, List<Measurement>> measurements) {
		return null;
	}
}
