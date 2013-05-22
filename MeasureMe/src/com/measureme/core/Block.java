package com.measureme.core;

// This contains the block info - include what the QR code's data was in index
public class Block {

	private Square mSquare;
	int mIndex;
	
	public Block(Square square, int index) {
		mSquare = square;
		mIndex = index;
	}
	
	public Square getSquare() {
		return mSquare;
	}
	
	public int getIndex() {
		return mIndex;
	}
	
	@Override
	public String toString() {
		return String.format("Block(%d) with %s", mIndex, mSquare.toString());
	}

}
