package com.measureme.core;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.measureme.suitinfo.BlockInfo;
import com.measureme.suitinfo.BlockList;
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
		Collection<ImageInterface> images = new ArrayList<ImageInterface>();
		File imageFolder = new File("../images");
		File[] imageFiles = imageFolder.listFiles();
		for (File imageFile : imageFiles) {
			ImageInterface image = new BufferedImageImpl(imageFile);
			images.add(image);
		}
		Main main = new Main();
		main.start(images);
	}
	
	public Main() {
		//String fakeSuitInfo = "blocksize,2|block,0,2,1,-1,-1|block,1,3,-1,-1,0|block,2,-1,3,0,-1|block,3,-1,-1,1,2|blocklist,width1,0,2,3|blocklist,width2,0,0,1|blocklist,height1,0,2,0|blocklist,height2,0,3,1";
		String fakeSuitInfo = "blocksize,1|block,0,-1,1,-1,-1|block,1,-1,2,5,0|block,2,-1,3,6,1|block,3,-1,4,7,2|block,4,-1,-1,8,3|block,5,1,6,-1,-1|block,6,2,7,-1,5|block,7,3,8,-1,6|block,8,4,-1,-1,7|blocklist,width1,0,0,1,2,3,4|blocklist,width2,0,5,6,7,8|blocklist,height1,0,1,5|blocklist,height2,0,2,6|blocklist,height3,0,3,7|blocklist,height4,4,8";
		mSuitInfo = new SuitInfo(fakeSuitInfo);
		mMeasurements = new HashMap<BlockPair, List<Measurement>>();
	}
	
	public void start(Collection<ImageInterface> images) {

		for (ImageInterface image : images) {
			addMeasurementsForImage(image);
		}
		
		List<BlockList> blockLists = mSuitInfo.getBlocklists();
		for (BlockList blockList : blockLists) {
			double circ = getCircumferenceInches(blockList);
			System.out.println(blockList.getDescription() + " has circumference " + circ + " inches");
		}
	}

	public void addMeasurementsForImage(ImageInterface image) {
		List<Block> blocks = QRSquareFinder.findBlocks(image);
		
		Map<Integer, Block> blockMap = new HashMap<Integer, Block>();
		for (Block block : blocks) {
			blockMap.put(new Integer(block.getIndex()), block);
			System.out.println("Found block " + block);
		}
		
		// for each block, see if you can find a pair with the bottom / right block
		// we don't need to do top/left since they will be some other block's bottom/right
		for (Block block : blockMap.values()) {
			BlockInfo bi = mSuitInfo.getBlockInfo(block.getIndex());
			if (bi == null) {
				throw new RuntimeException("Found a block that isn't in the database - " + block.getIndex());
			}
			int belowIndex = bi.getBottomBlockIndex();
			Block belowBlock = blockMap.get(new Integer(belowIndex));
			if (belowBlock != null) {
				// we have two blocks in the image that are next to each other
				addBlockPairToMeasurements(block, belowBlock, Direction.Down);
			}

			int rightIndex = bi.getRightBlockIndex();
			Block rightBlock = blockMap.get(new Integer(rightIndex));
			if (rightBlock != null) {
				// we have two blocks in the image that are next to each other
				addBlockPairToMeasurements(block, rightBlock, Direction.Right);
			}
		}
	}
	
	private void addBlockPairToMeasurements(Block b1, Block b2, Direction dir) {
		
		// get the distance between the two blocks
		double distancePixels = b1.getSquare().getDistancePixelsTo(b2.getSquare(), dir);
		BlockPair bp = new BlockPair(b1, b2);
		List<Measurement> measurements;
		if (mMeasurements.containsKey(bp)) {
			measurements = mMeasurements.get(bp);
		}
		else {
			measurements = new ArrayList<Measurement>();
			mMeasurements.put(bp, measurements);
		}
		
		// for now, get the largest side of any of the squares, and use that for the pixel to inch conversion
		double squareSizePixels = Math.max(b1.getSquare().getSizePixels(), b2.getSquare().getSizePixels());
		
		// convert from pixels to squares;
		double inchesOverPixels = mSuitInfo.getBlockSizeInches() / squareSizePixels;
		double distanceInches = distancePixels * inchesOverPixels;
		
		System.out.println("Adding to block pair " + bp + ", distance " + distanceInches + " inches");

		measurements.add(new Measurement(distanceInches));
	}

	// find the red dots in the image
	public List<Point> findRedDots(ImageInterface image) {
		return RedDotFinder.findRedDots(image);
	}

	// given the list of red dots, convert these into squares where QR codes will live
	public List<Square> findSquares(List<Point> redDots, ImageInterface image) {
		//return SquareFinder.findSquares(redDots, image);
		return null;
	}

	// detect the qr code if possible and find that block's index
	// return null if the qr code cannot be found
	public Block decodeBlock(Square square, ImageInterface image) {
		if (square.getDebugName().equals("2")) {
			return new Block(square, 3);
		}
		if (square.getDebugName().equals("3")) {
			return new Block(square, 2);
		}
		if (square.getDebugName().equals("0")) {
			return new Block(square, 1);
		}
		if (square.getDebugName().equals("1")) {
			return new Block(square, 0);
		}
		return null;
	}

	// given the block list, and all the measurements, find each of the circumferences in the suit
	public double getCircumferenceInches(BlockList blockList) {
		double circumferenceInches = blockList.getConstantAmount();
		List<Integer> blockIndices = blockList.getIndices();
		int first = blockIndices.get(0);
		for (int i = 1; i < blockIndices.size(); i++) {
			int second = blockIndices.get(i);
			BlockPair pair = new BlockPair(first, second);
			List<Measurement> measurements = mMeasurements.get(pair);
			if ((measurements != null) && (measurements.size() > 0)) {
				// for now, just get the max measurement
				double maxMeasurementInches = 0;
				for (Measurement measurement : measurements) {
					maxMeasurementInches = Math.max(measurement.getDistanceInches(), maxMeasurementInches);
					System.out.println("Found block pair " + pair + " with measurement " + measurement);
				}
				circumferenceInches += maxMeasurementInches;
			}
			else {
				System.out.println("Could'nt find block pair " + pair + " measurements = " + measurements);
			}
			first = second;
		}
		circumferenceInches += blockIndices.size() * mSuitInfo.getBlockSizeInches();  
		return circumferenceInches;
	}
}
