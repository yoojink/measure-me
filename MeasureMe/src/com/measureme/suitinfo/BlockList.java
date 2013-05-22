package com.measureme.suitinfo;

import java.util.List;


public class BlockList {
	private double mConstantAmount; // if we have a panel that is of constant size, we can include that here
	private String mDescription;
	private List<Integer> mIndices;
	
	public BlockList(String desc, double constantAmount, List<Integer> indices) {
		mDescription = desc;
		mConstantAmount = constantAmount;
		mIndices = indices;
	}
	
	public String getDescription() {
		return mDescription;
	}
	
	public List<Integer> getIndices() {
		return mIndices;
	}
	
	public double getConstantAmount() {
		return mConstantAmount;
	}
	
	@Override
	public String toString() {
		StringBuilder list = new StringBuilder();
		list.append(mIndices.get(0));
		for (int i = 1; i < mIndices.size(); i++) {
			list.append(", ");
			list.append(mIndices.get(i));
		}
		
		return String.format("BlockList(%s), const = %f, list = {%s}", mDescription, mConstantAmount, list.toString());
	}
}