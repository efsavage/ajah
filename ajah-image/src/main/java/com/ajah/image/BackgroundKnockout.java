package com.ajah.image;

/*
 *  Copyright 2012 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.BoxBlurFilter;
import com.jhlabs.image.DespeckleFilter;
import com.jhlabs.image.SmartBlurFilter;
import com.jhlabs.image.ThresholdFilter;

/**
 * Attempts to replace the background of an image.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class BackgroundKnockout {

	private static final int UNKNOWN = 0;
	private static final int KNOCK = 1;
	private static final int SOURCE = 2;

	/**
	 * Knocks out the background with a transparent (0 alpha) one.
	 * 
	 * @param image
	 *            The image to modify.
	 * @param color
	 *            The background color.
	 * @return The processed image.
	 * @throws IOException
	 *             If the image could not be read/written to.
	 */
	public static BufferedImage knockout(final BufferedImage image, final Color color) throws IOException {
		return knockout(image, color, new Color(255, 255, 255, 0));
	}

	/**
	 * Knocks out the background with a specified color.
	 * 
	 * @param image
	 *            The image to modify.
	 * @param color
	 *            The background color.
	 * @param knockoutColor
	 *            The color to replace the background with.
	 * @return The processed image.
	 * @throws IOException
	 *             If the image could not be read/written to.
	 */
	public static BufferedImage knockout(final BufferedImage image, final Color color, final Color knockoutColor) throws IOException {
		final ThresholdFilter filter = new ThresholdFilter();
		final BufferedImage knocked = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		new DespeckleFilter().filter(image, knocked);
		new BoxBlurFilter().filter(knocked, knocked);
		new SmartBlurFilter().filter(knocked, knocked);
		filter.filter(knocked, knocked);
		new SmartBlurFilter().filter(knocked, knocked);
		filter.filter(knocked, knocked);
		final int[][] mask = new int[image.getWidth()][image.getHeight()];
		// Initialize the mask
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (knocked.getRGB(x, y) != -1) {
					mask[x][y] = SOURCE;
				}
			}
		}

		long changed = 0;
		do {
			changed = 0;
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					if (mask[x][y] == UNKNOWN) {
						for (int x2 = x - 1; x2 <= (x + 1); x2++) {
							for (int y2 = y - 1; y2 <= (y + 1); y2++) {
								if (x2 >= 0 && y2 >= 0 && x2 < mask.length && y2 < mask[x2].length) {
									if (mask[x2][y2] == KNOCK || x2 == 0 || y2 == 0 || x2 == mask.length - 1 || y2 == mask[x2].length - 1) {
										// Do we have a neighbor that is a
										// knock, or are we on the edge?
										mask[x][y] = KNOCK;
										changed++;
									}
								}
							}
						}
					}
				}
			}
			// Keep looping until we haven't changed anything.
		} while (changed > 0);

		// Write to the new image
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (mask[x][y] == KNOCK) {
					knocked.setRGB(x, y, knockoutColor.getRGB());
				} else {
					knocked.setRGB(x, y, image.getRGB(x, y));
				}
			}
		}
		return knocked;
	}

}
