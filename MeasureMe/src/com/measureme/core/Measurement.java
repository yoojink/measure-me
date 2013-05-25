package com.measureme.core;

// contains information about the 'space' between two squares
public class Measurement {
	
	public Measurement(double distanceInches) {
		mDistanceInches = distanceInches;
	}
	
	public double getDistanceInches() {
		return mDistanceInches;
	}
	
	@Override
	public String toString() {
		return String.format("Measurement(%.2f)", mDistanceInches);
	}

	// for now, this just holds the distance.
	// In the future, it could hold the distance at 2 or more points, etc.
	private double mDistanceInches;

}
