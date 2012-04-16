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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.ajah.util.AjahUtils;

/**
 * Utilities for processing images.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class ImageUtils {

	private static Logger log = Logger.getLogger(ImageUtils.class.getName());

	/**
	 * Blends two colors with a given alpha value.
	 * 
	 * @param first
	 *            The first color (subject to alpha).
	 * @param second
	 *            The second color (subject to alpha remainder).
	 * @param alpha
	 *            The alpha value of the blend.
	 * @return The blended color.
	 */
	public static Color blend(final Color first, final Color second, final float alpha) {
		final int red = (int) ((first.getRed() * alpha) + second.getRed() * (1 - alpha));
		final int green = (int) ((first.getGreen() * alpha) + second.getGreen() * (1 - alpha));
		final int blue = (int) ((first.getBlue() * alpha) + second.getBlue() * (1 - alpha));
		return new Color(red, green, blue);
	}

	/**
	 * Extracts some simple information about an image.
	 * 
	 * @param data
	 *            The image data.
	 * @return The image info bean.
	 * @throws IOException
	 *             If the image could not be analyzed.
	 */
	public static ImageInfo getInfo(final byte[] data) throws IOException {
		AjahUtils.requireParam(data, "data");
		final BufferedImage image = ImageIO.read(new ByteArrayInputStream(data));
		if (image == null) {
			throw new IllegalArgumentException("An image could not be constructed from the data");
		}
		final ImageInfo info = new ImageInfo();
		info.setHeight(image.getHeight());
		info.setWidth(image.getWidth());
		final ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(data));
		final Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
		if (readers.hasNext()) {
			info.setFormat(ImageFormat.from(readers.next().getFormatName()));
		} else {
			log.warning("No readers found for image");
		}
		return info;
	}

}
