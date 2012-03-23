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
package com.ajah.util.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * Attempts to replace the background of an image.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class BackgroundKnockout {

	private static Logger log = Logger.getLogger(BackgroundKnockout.class.getName());

	/**
	 * Knocks out the background with a transparent (0 alpha) one.
	 * 
	 * @param image
	 *            The image to modify.
	 * @param color
	 *            The background color.
	 * @throws IOException
	 *             If the image could not be read/written to.
	 */
	public void knockout(final BufferedImage image, final Color color) throws IOException {
		knockout(image, color, new Color(255, 255, 255, 0));
	}

	/**
	 * Knocks out the background color.
	 * 
	 * @param image
	 *            The image to modify.
	 * @param color
	 *            The background color.
	 * @param knockoutColor
	 *            The color to replace the background color with.
	 * @throws IOException
	 *             If the image could not be read/written to.
	 */
	public void knockout(final BufferedImage image, final Color color, final Color knockoutColor) throws IOException {

		final BufferedImage knocked = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final long start = System.currentTimeMillis();

		final int[][] mask = new int[image.getWidth()][image.getHeight()];
		// 0 = Unchecked
		// -1 = Fill
		// -2 = No Fill
		// -3 = Possible blend
		// -4 = Definite blend

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				final Color test = new Color(image.getRGB(x, y), true);
				final int distance = ColorUtils.getMaxDistance(color, test);
				if (distance > 10) {
					mask[x][y] = -2;
				} else if (distance > 30) {
					mask[x][y] = -3;
				}
			}
		}
		long changed = 0;
		do {
			changed = 0;
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					if (mask[x][y] == 0) {
						if (x == 0 || y == 0 || x == (mask.length - 1) || y == (mask[x].length - 1)) {
							// We're on the edge
							mask[x][y] = -1;
							changed++;
						}
						for (int x2 = x - 1; x2 <= (x + 1); x2++) {
							for (int y2 = y - 1; y2 <= (y + 1); y2++) {
								if (x2 >= 0 && y2 >= 0 && x2 < mask.length && y2 < mask[x2].length && mask[x2][y2] == -1) {
									// We've got a fill neighbor
									mask[x][y] = -1;
									changed++;
								}
							}
						}
					}
				}
			}
			log.finest(changed + " fill cells changed");
		} while (changed > 0);

		// At this point, anything that's still 0 is non-contiguous
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (mask[x][y] == 0) {
					mask[x][y] = -2;
				}
			}
		}

		// Let's look at the blend cells
		do {
			changed = 0;
			for (int x = 0; x < image.getWidth(); x++) {
				for (int y = 0; y < image.getHeight(); y++) {
					if (mask[x][y] == -3) {
						for (int x2 = x - 1; x2 <= (x + 1); x2++) {
							for (int y2 = y - 1; y2 <= (y + 1); y2++) {
								if (x2 >= 0 && y2 >= 0 && x2 < mask.length && y2 < mask[x2].length && (mask[x2][y2] == -1)) {
									// We've got a fill neighbor, we can blend
									mask[x][y] = -4;
									changed++;
								}
							}
						}
					}
				}
			}
			log.finest(changed + " blend cells changed");
		} while (changed > 0);

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (mask[x][y] == -2) {
					knocked.setRGB(x, y, image.getRGB(x, y));
				} else if (mask[x][y] == -1) {
					knocked.setRGB(x, y, knockoutColor.getRGB());
				} else if (mask[x][y] == -3) {
					knocked.setRGB(x, y, image.getRGB(x, y));
				} else if (mask[x][y] == -4) {
					knocked.setRGB(x, y, ImageUtils.blend(new Color(image.getRGB(x, y)), knockoutColor, .9f).getRGB());
				} else if (mask[x][y] == 0) {
					knocked.setRGB(x, y, image.getRGB(x, y));
				}
			}
		}
		final long end = System.currentTimeMillis();
		log.fine("Knocked out background in " + (end - start) + "ms");
		ImageIO.write(knocked, "png", new File("/tmp/knocked.png"));

	}

}
