package com.measureme.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.measureme.suitinfo.SuitInfo;

public class Main {
	
	private SuitInfo mSuitInfo;
	private Map<BlockPair, List<Measurement>> mMeasurements;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// get the images somehow, from a directory?
		Collection<ImageInterface> images = null;
		Main main = new Main();
		main.start(images);
	}
	
	public Main() {
		mSuitInfo = new SuitInfo();
		mMeasurements = new HashMap<BlockPair, List<Measurement>>();
	}
	
	public void start(Collection<ImageInterface> images) {

		for (ImageInterface image : images) {
			addMeasurementsForImage(image);
		}
		
		List<SuitInfo.BlockList> blockLists = mSuitInfo.getBlocklists();
		for (SuitInfo.BlockList blockList : blockLists) {
			double circ = getCircumference(blockList);
			System.out.println(blockList.getDescription() + " has circumference " + circ);
		}
	}

	public void addMeasurementsForImage(ImageInterface image) {
		List<Point> points = findRedDots(image);
		List<Square> squares = findSquares(points, image);
		
		Map<Integer, Block> blockMap = new HashMap<Integer, Block>();
		for (Square square : squares) {
			Block block = decodeBlock(square, image);
			blockMap.put(new Integer(block.getIndex()), block);
		}
		
		// for each block, see if you can find a pair with the bottom / right block
		// we don't need to do top/left since they will be some other block's bottom/right
		for (Block block : blockMap.values()) {
			int belowIndex = mSuitInfo.getBelow(block.getIndex());
			Block belowBlock = blockMap.get(new Integer(belowIndex));
			if (belowBlock != null) {
				// we have two blocks in the image that are next to each other
				addBlockPairToMeasurements(block, belowBlock);
			}

			int rightIndex = mSuitInfo.getRightOf(block.getIndex());
			Block rightBlock = blockMap.get(new Integer(rightIndex));
			if (rightBlock != null) {
				// we have two blocks in the image that are next to each other
				addBlockPairToMeasurements(block, rightBlock);
			}
		}
	}
	
	private void addBlockPairToMeasurements(Block b1, Block b2) {
		double distance = b1.getSquare().getDistanceTo(b2.getSquare());
		BlockPair bp = new BlockPair(b1, b2);
		List<Measurement> measurements;
		if (mMeasurements.containsKey(bp)) {
			measurements = mMeasurements.get(bp);
		}
		else {
			measurements = new ArrayList<Measurement>();
			mMeasurements.put(bp, measurements);
		}
		measurements.add(new Measurement(distance));
	}

	// find the red dots in the image
	public List<Point> findRedDots(ImageInterface image) {
		return null;
	}

	// given the list of red dots, convert these into squares where QR codes will live
	public List<Square> findSquares(List<Point> redDots, ImageInterface image) {
		return null;
	}

	// detect the qr code if possible and find that block's index
	// return null if the qr code cannot be found
	public Block decodeBlock(Square square, ImageInterface image) {
		return null;
	}

	// given the block list, and all the measurements, find each of the circumferences in the suit
	public double getCircumference(SuitInfo.BlockList blockList) {
		return 0;
	}
}
