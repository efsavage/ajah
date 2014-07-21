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
	TEXT_PLAIN("text", "plain", true),
	/**
	 * multipart/alternative
	 */
	MULTIPART_ALTERNATIVE("multipart", "alternative"),
	/**
	 * application/javascript
	 */
	APPLICATION_JAVASCRIPT("application", "javascript", true),
	/**
	 * application/json
	 */
	APPLICATION_JSON("application", "json", true),
	/**
	 * application/xhtml+xml (XHTML)
	 */
	APPLICATION_XHTML("application", "xhtml+xml", true),
	/**
	 * application/xml
	 */
	APPLICATION_XML("application", "xml", true),
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
	IMAGE_PNG("image", "png"),
	/**
	 * image/gif
	 */
	IMAGE_GIF("image", "gif"),
	/**
	 * image/png
	 */
	IMAGE_JPG("image", "jpeg"),
	/**
	 * image/svg+xml
	 */
	IMAGE_SVG("image", "svg+xml"),
	/**
	 * text/html
	 */
	TEXT_HTML("text", "html", true),
	/**
	 * text/css
	 */
	TEXT_CSS("text", "css", true),
	/**
	 * text/xml
	 */
	TEXT_XML("text", "xml", true),
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

	private final String primaryType;

	private final String subType;

	private final String baseType;

	private boolean text;

	private AjahMimeType(final String primaryType, final String subType) {
		this.primaryType = primaryType;
		this.subType = subType;
		this.baseType = primaryType + "/" + subType;
		this.text = false;
	}

	private AjahMimeType(final String primaryType, final String subType, final boolean text) {
		this.primaryType = primaryType;
		this.subType = subType;
		this.baseType = primaryType + "/" + subType;
		this.text = text;
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
