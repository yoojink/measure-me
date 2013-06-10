package com.measureme.core;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;
import com.google.zxing.multi.qrcode.detector.MultiDetector;

public class QRSquareFinder {

	public static List<Block> findBlocks(ImageInterface image) {
		List<Block> blocks = new ArrayList<Block>();
		BinaryBitmap bb = createMatrixFromImage(image);
		QRCodeMultiReader reader = new QRCodeMultiReader();
		try {
			Map<DecodeHintType, Boolean> hints = new HashMap<DecodeHintType, Boolean>();
			hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
			Result[] results = reader.decodeMultiple(bb);
			System.out.println("REsults: " + results.length);
			for (Result result : results) {
				ResultPoint[] points = result.getResultPoints();
				Square square = new Square(new Point2D.Double(points[1].getX(), points[1].getY()),
						new Point2D.Double(points[2].getX(), points[2].getY()),
						new Point2D.Double(points[0].getX(), points[0].getY()), "qr");
				Block block = new Block(square, Integer.parseInt(result.getText()));
				blocks.add(block);
			}
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return blocks;
	}


	// In case the golden images are not monochromatic, convert the RGB values to greyscale.
	private static BinaryBitmap createMatrixFromImage(ImageInterface image) {
		BufferedImageImpl bii = (BufferedImageImpl)image;
		BufferedImage bi = bii.getImage();
		LuminanceSource ls = new BufferedImageLuminanceSource(bi);
		BinaryBitmap bb = new BinaryBitmap(new HybridBinarizer(ls));
		return bb;
	}
	
}
