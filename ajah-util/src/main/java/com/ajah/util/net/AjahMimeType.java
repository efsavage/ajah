/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
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
package com.ajah.util.net;

import com.ajah.util.Identifiable;
import com.ajah.util.StringUtils;

/**
 * An enum for known Mime types.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum AjahMimeType implements Identifiable<String> {

	/**
	 * text/plain
	 */
	TEXT_PLAIN("text", "plain", true, "txt"),
	/**
	 * multipart/alternative
	 */
	MULTIPART_ALTERNATIVE("multipart", "alternative"),
	/**
	 * application/javascript
	 */
	APPLICATION_JAVASCRIPT("application", "javascript", true, "js"),
	/**
	 * application/json
	 */
	APPLICATION_JSON("application", "json", true, "json", "map"),
	/**
	 * application/xhtml+xml (XHTML)
	 */
	APPLICATION_XHTML("application", "xhtml+xml", true),
	/**
	 * application/xml
	 */
	APPLICATION_XML("application", "xml", true, "xml"),
	/**
	 * application/postscript
	 */
	APPLICATION_POSTSCRIPT("application", "postscript", false, "eps"),
	/**
	 * application/vnd.ms-fontobject
	 */
	APPLICATION_VND_MS_FONTOBJECT("application", "vnd.ms-fontobject", false, "eot"),
	/**
	 * multipart/signed
	 */
	MULTIPART_RELATED("multipart", "related"),
	/**
	 * multipart/mixed
	 */
	MULTIPART_MIXED("multipart", "mixed"),
	/**
	 * multipart/signed
	 */
	MULTIPART_SIGNED("multipart", "signed"),
	/**
	 * multipart/signed
	 */
	MULTIPART_REPORT("multipart", "report"),
	/**
	 * image/png
	 */
	IMAGE_PNG("image", "png", false, "png"),
	/**
	 * image/gif
	 */
	IMAGE_GIF("image", "gif", false, "gif"),
	/**
	 * image/png
	 */
	IMAGE_JPG("image", "jpeg", false, "jpeg", "jpg"),
	/**
	 * image/svg+xml
	 */
	IMAGE_SVG("image", "svg+xml", false, "svg"),
	/**
	 * text/html
	 */
	TEXT_HTML("text", "html", true, "html"),
	/**
	 * text/css
	 */
	TEXT_CSS("text", "css", true, "css"),
	/**
	 * text/csv
	 */
	TEXT_CSV("text", "csv", true, "csv"),
	/**
	 * text/xml
	 */
	TEXT_XML("text", "xml", true),
	/**
	 * text/x-markdown
	 */
	TEXT_MARKDOWN("text", "x-markdown", true, "md", "markdown"),
	/**
	 * application/x-font-opentype
	 */
	APPLICATION_FONT_OPENTYPE("application", "x-font-opentype", false, "otf"),
	/**
	 * application/font-woff
	 */
	APPLICATION_WOFF("application", "font-woff", false, "woff"),
	/**
	 * text/x-font-woff2
	 */
	APPLICATION_WOFF2("application", "x-font-woff2", false, "woff2"),
	/**
	 * application/font-sfnt
	 */
	APPLICATION_TTF("application", "font-sfnt", false, "ttf"),

	/**
	 * Unknown
	 */
	UNKNOWN("unknown", "unknown");

	/**
	 * Matches a contentType to a known Mime type.
	 * 
	 * @param contentType
	 *            The content type to match.
	 * @return The matching {@link AjahMimeType} or {@link #UNKNOWN}.
	 */
	public static AjahMimeType get(final String contentType) {
		if (StringUtils.isBlank(contentType)) {
			return UNKNOWN;
		}
		for (final AjahMimeType ajahMimeType : values()) {
			if (contentType.startsWith(ajahMimeType.getBaseType())) {
				return ajahMimeType;
			}
		}
		final String lower = contentType.toLowerCase();
		if (!contentType.equals(lower)) {
			for (final AjahMimeType ajahMimeType : values()) {
				if (lower.startsWith(ajahMimeType.getBaseType())) {
					return ajahMimeType;
				}
			}
		}
		switch (contentType) {
		case "html":
			return TEXT_HTML;
		case "xhtml":
			return APPLICATION_XHTML;
		case "xml":
			return APPLICATION_XML;
		case "text":
			return TEXT_PLAIN;
		case "json":
			return APPLICATION_JSON;
		default:
			return UNKNOWN;
		}
	}

	/**
	 * Find a content type based on a file extension. E.G. stylesheet.css would
	 * return CSS.
	 * 
	 * @param toMatch
	 *            The file extension to match on.
	 * @return The matching type, or UNKNOWN.
	 */
	public static AjahMimeType getByFileExtension(final String toMatch) {
		for (AjahMimeType ajahMimeType : values()) {
			if (ajahMimeType.fileExtensions != null) {
				for (String fileExtension : ajahMimeType.fileExtensions) {
					if (toMatch.equalsIgnoreCase(fileExtension)) {
						return ajahMimeType;
					}
				}
			}
		}
		return UNKNOWN;
	}

	private final String primaryType;

	private final String subType;

	private final String baseType;

	private final boolean text;

	private final String[] fileExtensions;

	private AjahMimeType(final String primaryType, final String subType) {
		this.primaryType = primaryType;
		this.subType = subType;
		this.fileExtensions = null;
		this.baseType = primaryType + "/" + subType;
		this.text = false;
	}

	private AjahMimeType(final String primaryType, final String subType, final boolean text, final String... fileExtensions) {
		this.primaryType = primaryType;
		this.subType = subType;
		this.baseType = primaryType + "/" + subType;
		this.text = text;
		this.fileExtensions = fileExtensions;
	}

	/**
	 * The mime type, i.e. the primary type followed by the sub type.
	 * 
	 * @return The mime type, i.e. the primary type followed by the sub type.
	 */
	public String getBaseType() {
		return this.baseType;
	}

	/**
	 * Returns this types id.
	 * 
	 * @see com.ajah.util.Identifiable#getId()
	 */
	@Override
	public String getId() {
		return getBaseType();
	}

	/**
	 * The primary type of the mime type, i.e. the part before the slash.
	 * 
	 * @return The primary type of the mime type, i.e. the part before the
	 *         slash.
	 */
	public String getPrimaryType() {
		return this.primaryType;
	}

	/**
	 * The sub type of the mime type, i.e. the part after the slash.
	 * 
	 * @return The sub type of the mime type, i.e. the part after the slash.
	 */
	public String getSubType() {
		return this.subType;
	}

	/**
	 * Is this content type a text type? E.G. plaintext, html, xml, etc.
	 * 
	 * @return true if this is a known text type, otherwise false.
	 */
	public boolean isText() {
		return this.text;
	}

	@Override
	public void setId(final String id) {
		throw new UnsupportedOperationException();
	}

}
