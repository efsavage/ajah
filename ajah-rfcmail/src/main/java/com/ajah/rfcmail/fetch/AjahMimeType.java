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
package com.ajah.rfcmail.fetch;

import com.ajah.util.StringUtils;

/**
 * An enum for known Mime types.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum AjahMimeType {

	/**
	 * text/plain
	 */
	TEXT_PLAIN("text", "plain"),
	/**
	 * multipart/alternative
	 */
	MULTIPART_ALTERNATIVE("multipart", "alternative"),
	/**
	 * text/html
	 */
	TEXT_HTML("text", "html"),
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
	 * Unknown
	 */
	UNKNOWN("unknown", "unknown");

	private final String primaryType;

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
	 * The mime type, i.e. the primary type followed by the sub type.
	 * 
	 * @return The mime type, i.e. the primary type followed by the sub type.
	 */
	public String getBaseType() {
		return this.baseType;
	}

	private final String subType;
	private final String baseType;

	private AjahMimeType(final String primaryType, final String subType) {
		this.primaryType = primaryType;
		this.subType = subType;
		this.baseType = primaryType + "/" + subType;
	}

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
		return UNKNOWN;
	}

}
