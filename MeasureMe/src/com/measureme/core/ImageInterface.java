package com.measureme.core;

import java.awt.Point;

// since we don't know what type of image data we are using (android camera has bit arrays, opencv has their rep, regular java has BufferedInfo
public interface ImageInterface {

	public String getDebugName(); // only for debugging purposes
	public int getPixel(int i, int j); // in RGB format
	public int getRed(int i, int j);
	public int getBlue(int i, int j);
	public int getGreen(int i, int j);
	public int getLuminance(int i, int j);
	public int getWidth();
	public int getHeight();
	public int getPixel(Point point);
}
