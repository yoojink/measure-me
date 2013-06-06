package com.measureme.core;

import java.awt.Color;

public class ColorTester {

	private static final double COLOR_CONST = 1.2;

	public static boolean isblue(int pixel) {
		Color color = new Color(pixel);
		return (color.getBlue() > (color.getRed() * COLOR_CONST)) && (color.getBlue() > (color.getGreen() * COLOR_CONST));
	}

	public static boolean isRed(int pixel) {
		Color color = new Color(pixel);
		return (color.getRed() > (color.getBlue() * 1.7)) && (color.getRed() > (color.getGreen() * 1.7) && (color.getRed() > 100) && (Math.abs(color.getBlue() - color.getGreen()) < 10));
	}

	public static boolean isGreen(int pixel) {
		Color color = new Color(pixel);
		return (color.getGreen() > (color.getBlue() * COLOR_CONST)) && (color.getGreen() > (color.getRed() * COLOR_CONST));
	}

}
