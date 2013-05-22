package com.measureme.core;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class BufferedImageImpl implements ImageInterface {

	private String mFilename;
	private BufferedImage mBufferedImage;

	public BufferedImageImpl(File file) {
		mFilename = file.getAbsolutePath();
		try {
			System.out.println("Trying to read file " + mFilename);
			mBufferedImage = ImageIO.read(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		return (int)(0.2126 * getRed(i, j) + 0.7152 * getGreen(i, j) + 0.0722 * getBlue(i, j));
	}

}
