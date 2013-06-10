package com.measureme.suitinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// this class contains the information you need to figure out where each block is on the suite.
// given the index of a block, you can figure out which block was to the right/left/top/below it.
// return -1 if there are no blocks below/above it.

// We are going to deserialize this class from some constant strings
// The format is seperated by |
// blocksize,2 - block size is 2 inches
// block,0,1,2,3,-1 - block with index 0, has going in clockwise order from the top, blocks 1, 2, 3, and no blocks to the left
// blocklist,foo,.25,1,2,3 - blocklist with name 'foo' with blocks 1, 2, 3 with constant amount .25 inches
// "blocksize,2|block0,2,1,-1,-1|block1,3,-1,-1,0|block2,-1,3,0,-1|block3,-1,-1,1,2|blocklist,width1,0,2,3|blocklist,width2,0,0,1|blocklist,height1,0,2,0|blocklist,height2,0,3,1";
public class SuitInfo{
	private double mBlockSizeInches = -1;
	List<BlockInfo> mBlockInfos;
	List<BlockList> mBlockLists;
	public SuitInfo(String suitInfo) {
		mBlockInfos = new ArrayList<BlockInfo>();
		mBlockLists = new ArrayList<BlockList>();
		parse(suitInfo);
		if (mBlockInfos.isEmpty() || mBlockLists.isEmpty()) {
			throw new RuntimeException("You need at least one block and blockinfo but you have " + mBlockInfos.size() + " blocks, and " + mBlockLists.size() + " block lists");
		}
		if (mBlockSizeInches < 0) {
			throw new RuntimeException("You need to specify the size of the block");
		}
	}
	
	public double getBlockSizeInches() {
		return mBlockSizeInches;
	}

	public BlockInfo getBlockInfo(int index) {
		if (mBlockInfos.size() <= index) {
			return null;
		}
		return mBlockInfos.get(index);
	}

	// this returns the list of blocks that will make up the 'height'
	public List<BlockList> getBlocklists() {
		return mBlockLists;
	}
	
	private void parse(String suitInfo) {
		String[] params = suitInfo.split("\\|");
		for (String param : params) {
			Scanner scanner = new Scanner(param).useDelimiter(",");
			String command = scanner.next();
			if (command.equals("blocksize")) {
				mBlockSizeInches = scanner.nextDouble();
				System.out.println("Block size is " + mBlockSizeInches + " inches");
			}
			else if (command.equals("block")) {
				int index = scanner.nextInt();
				int topIndex = scanner.nextInt();
				int rightIndex = scanner.nextInt();
				int bottomIndex = scanner.nextInt();
				int leftIndex = scanner.nextInt();
				BlockInfo blockInfo = new BlockInfo(index, topIndex, rightIndex, bottomIndex, leftIndex);
				System.out.println("Adding block " + blockInfo);
				mBlockInfos.add(index, blockInfo);
			}
			else if (command.equals("blocklist")) {
				String desc = scanner.next();
				double constAmount = scanner.nextDouble();
				List<Integer> indices = new ArrayList<Integer>();
				while (scanner.hasNext()) {
					indices.add(scanner.nextInt());
				}
				if (indices.isEmpty()) {
					throw new RuntimeException("Block list " + desc + " has no indices");
				}
				BlockList bl = new BlockList(desc, constAmount, indices);
				System.out.println("Adding block list " + bl + " with " + indices.size() + " indices");
				mBlockLists.add(bl);
			}
		}
	}

}
