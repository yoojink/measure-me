package com.measureme.core;

public class BlockPair {

	private int mLowerIndex;
	private int mUpperIndex;
	public BlockPair(int index1, int index2) {
		mLowerIndex = Math.min(index1, index2);
		mUpperIndex = Math.max(index1, index2);
	}
	
	@Override
	public boolean equals(Object other) {
		BlockPair obp = (BlockPair) other;
		return (mLowerIndex == obp.mLowerIndex) && (mUpperIndex == obp.mUpperIndex);
	}
}
