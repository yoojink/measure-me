package com.measureme.suitinfo;

// we can serialize this from a string
public class BlockInfo {
	public BlockInfo(int index, int top, int right, int bottom, int left) {
		mIndex = index;
		mTopBlockIndex = top;
		mBottomBlockIndex = bottom;
		mLeftBlockIndex = left;
		mRightBlockIndex = right;
	}
	public int getIndex() {
		return mIndex;
	}
	public int getTopBlockIndex() {
		return mTopBlockIndex;
	}
	public int getBottomBlockIndex() {
		return mBottomBlockIndex;
	}
	public int getLeftBlockIndex() {
		return mLeftBlockIndex;
	}
	public int getRightBlockIndex() {
		return mRightBlockIndex;
	}
	private int mIndex;
	private int mTopBlockIndex;
	private int mBottomBlockIndex;
	private int mLeftBlockIndex;
	private int mRightBlockIndex;
	
	@Override
	public String toString() {
		return String.format("BlockInfo - index %d,  top %d, right %d, bottom %d, left %d", mIndex, mTopBlockIndex, mRightBlockIndex, mBottomBlockIndex, mLeftBlockIndex);
	}
}
