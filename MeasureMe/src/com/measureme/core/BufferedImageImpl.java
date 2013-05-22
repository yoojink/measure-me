package com.measureme.core;

import javax.imageio.ImageIo;
import java.io.File;
import java.awt.image.BufferedImage;

public class BufferedImageImpl implements ImageInterface {

	private String mFilename;
	private BufferedImage mBufferedImage;

	public BufferedImageImpl(String filename) {
		mFilename = filename;
		mBufferedImage = ImageIO.read(new File(filename));
	}

	@Override
	public String getDebugName() {
		return mFilename;
	}

	@Override
	public int getPixel(int i, int j) {
		return mBufferedImage.getRGB(i, j);
	}

	@Override
	public int getRed(int i, int j) {
		return (getPixel(i, j) >> 16) & 0xFF; 
	}

	@Override
	public int getBlue(int i, int j) {
		return (getPixel(i, j)) & 0xFF;
	}

	@Override
	public int getGreen(int i, int j) {
		return (getPixel(i, j) >> 8) & 0xFF;
	}

	@Override
	public int getLuminance(int i, int j) {
		return 0.2126 * getRed(i, j) + 0.7152 * getGreen(i, j) + 0.0722 * getBlue(i, j);
	}

}
