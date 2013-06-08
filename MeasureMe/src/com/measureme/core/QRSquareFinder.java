package com.measureme.core;

import java.util.List;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.multi.qrcode.detector.MultiDetector;

public class QRSquareFinder {

	public static List<Square> findSquares(ImageInterface image) {
		BitMatrix bm = createMatrixFromImage(image);
		MultiDetector detector = new MultiDetector(bm);
		try {
			detector.detectMulti(null);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	// In case the golden images are not monochromatic, convert the RGB values to greyscale.
	private static BitMatrix createMatrixFromImage(ImageInterface image) {
	    int width = image.getWidth();
	    int height = image.getHeight();
	    int[] pixels = new int[width * height];
	    image.getRGB(0, 0, width, height, pixels, 0, width);
	
	    BitMatrix matrix = new BitMatrix(width, height);
	    for (int y = 0; y < height; y++) {
		    for (int x = 0; x < width; x++) {
		        int pixel = pixels[y * width + x];
		        int luminance = (306 * ((pixel >> 16) & 0xFF) +
		            601 * ((pixel >> 8) & 0xFF) +
		            117 * (pixel & 0xFF)) >> 10;
		        if (luminance <= 0x7F) {
		            matrix.set(x, y);
		        }
		    }
	    }
	    return matrix;
	}
}
