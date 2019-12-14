package com.ajah.image;

import com.ajah.util.net.AjahMimeType;

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

/**
 * Supported image formats.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public enum ImageFormat {

	/**
	 * PNG
	 */
	PNG("png", AjahMimeType.IMAGE_PNG),
	/**
	 * GIF
	 */
	GIF("gif", AjahMimeType.IMAGE_GIF),
	/**
	 * JPEG
	 */
	JPG("jpg", AjahMimeType.IMAGE_JPG, "jpeg");

	/**
	 * Matches a format based on the name and aliases.
	 * 
	 * @param value
	 *            The value to try and match.
	 * @return The matching format, or null.
	 */
	public static ImageFormat from(final String value) {
		for (final ImageFormat format : values()) {
			if (format.name().equalsIgnoreCase(value)) {
				return format;
			}
			if (format.getAliases() != null) {
				for (final String alias : format.getAliases()) {
					if (alias.equalsIgnoreCase(value)) {
						return format;
					}
				}
			}
		}
		return null;
	}

	private final String suffix;

	private final String[] aliases;

	private final AjahMimeType mimeType;

	ImageFormat(final String suffix, final AjahMimeType mimeType, final String... aliases) {
		this.suffix = suffix;
		this.mimeType = mimeType;
		this.aliases = aliases;
	}

	/**
	 * Returns the suffix aliases for this image format.
	 * 
	 * @return the suffix aliases for this image format.
	 */
	public String[] getAliases() {
		return this.aliases;
	}

	/**
	 * Returns the mimeType for this image format.
	 * 
	 * @return the mimeType for this image format.
	 */
	public AjahMimeType getMimeType() {
		return this.mimeType;
	}

	/**
	 * Returns the canonical suffix for this image format.
	 * 
	 * @return the canonical suffix for this image format.
	 */
	public String getSuffix() {
		return this.suffix;
	}
}
