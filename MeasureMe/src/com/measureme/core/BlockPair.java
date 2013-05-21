package com.measureme.core;

public class BlockPair {

	private int mLowerIndex;
	private int mUpperIndex;
	public BlockPair(int index1, int index2) {
		init(index1, index2);
	}
	
	public BlockPair(Block b1, Block b2) {
		init(b1.getIndex(), b2.getIndex());
	}
	
	private void init(int index1, int index2) {
		mLowerIndex = Math.min(index1, index2);
		mUpperIndex = Math.max(index1, index2);
	}

	@Override
	public boolean equals(Object other) {
		BlockPair obp = (BlockPair) other;
		return (mLowerIndex == obp.mLowerIndex) && (mUpperIndex == obp.mUpperIndex);
	}
}
