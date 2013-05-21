package com.measureme.core;

// contains information about the 'space' between two squares
public class Measurement {
	
	public Measurement(double distance) {
		mDistance = distance;
	}

	// for now, this just holds the distance.
	// In the future, it could hold the distance at 2 or more points, etc.
	private double mDistance;

}
