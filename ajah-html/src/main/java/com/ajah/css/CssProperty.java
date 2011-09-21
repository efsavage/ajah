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
package com.ajah.css;

/**
 * Enumeration of valid/supported properties.
 * 
 * <strong>This is not a complete list!</strong>
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public enum CssProperty {

	// Permitted CSS Properties
	/**
	 * background
	 */
	BACKGROUND("background"),
	/**
	 * background-color
	 */
	BACKGROUND_COLOR("background-color"),
	/**
	 * background-image
	 */
	BACKGROUND_IMAGE("background-image"),
	/**
	 * border
	 */
	BORDER("border"),
	/**
	 * border-bottom
	 */
	BORDER_BOTTOM("border-bottom"),
	/**
	 * border-collapse
	 */
	BORDER_COLLAPSE("border-collapse"),
	/**
	 * border-color
	 */
	BORDER_COLOR("border-color"),
	/**
	 * border-radius
	 */
	BORDER_RADIUS("border-radius"),
	/**
	 * border-spacing
	 */
	BORDER_SPACING("border-spacing"),
	/**
	 * border-width
	 */
	BORDER_WIDTH("border-width"),
	/**
	 * box-shadow
	 */
	BOX_SHADOW("box-shadow"),
	/**
	 * color
	 */
	COLOR("color"),
	/**
	 * content
	 */
	CONTENT("content"),
	/**
	 * cursor
	 */
	CURSOR("cursor"),
	/**
	 * display
	 */
	DISPLAY("display"),
	/**
	 * filter
	 */
	FILTER("filter"),
	/**
	 * float
	 */
	FLOAT("float"),
	/**
	 * font
	 */
	FONT("font"),
	/**
	 * font-family
	 */
	FONT_FAMILY("font-family"),
	/**
	 * font-size
	 */
	FONT_SIZE("font-size"),
	/**
	 * font-style
	 */
	FONT_STYLE("font-style"),
	/**
	 * font-variant
	 */
	FONT_VARIANT("font-variant"),
	/**
	 * font-weight
	 */
	FONT_WEIGHT("font-weight"),
	/**
	 * left
	 */
	LEFT("left"),
	/**
	 * height
	 */
	HEIGHT("height"),
	/**
	 * letter-spacing
	 */
	LETTER_SPACING("letter-spacing"),
	/**
	 * line-height
	 */
	LINE_HEIGHT("line-height"),
	/**
	 * list-style
	 */
	LIST_STYLE("list-style"),
	/**
	 * list-style-image
	 */
	LIST_STYLE_IMAGE("list-style-image"),
	/**
	 * list-style-type
	 */
	LIST_STYLE_TYPE("list-style-type"),
	/**
	 * margin
	 */
	MARGIN("margin"),
	/**
	 * margin-bottom
	 */
	MARGIN_BOTTOM("margin-bottom"),
	/**
	 * margin-left
	 */
	MARGIN_LEFT("margin-left"),
	/**
	 * -moz-box-shadow
	 */
	MOZ_BOX_SHADOW("-moz-box-shadow"),
	/**
	 * opacity
	 */
	OPACITY("opacity"),
	/**
	 * outline
	 */
	OUTLINE("outline"),
	/**
	 * padding
	 */
	PADDING("padding"),
	/**
	 * padding-right
	 */
	PADDING_RIGHT("padding-right"),
	/**
	 * padding-top
	 */
	PADDING_TOP("padding-top"),
	/**
	 * padding-left
	 */
	PADDING_LEFT("padding-left"),
	/**
	 * padding-bottom
	 */
	PADDING_BOTTOM("padding-bottom"),
	/**
	 * position
	 */
	POSITION("position"),
	/**
	 * text-align
	 */
	TEXT_ALIGN("text-align"),
	/**
	 * text-shadow
	 */
	TEXT_SHADOW("text-shadow"),
	/**
	 * top
	 */
	TOP("top"),
	/**
	 * vertical-align
	 */
	VERTICAL_ALIGN("vertical-align"),
	/**
	 * -webkit-animation-duration
	 */
	WEBKIT_ANIMATION_DURATION("-webkit-animation-duration"),
	/**
	 * -webkit-animation-iteration-count
	 */
	WEBKIT_ANIMATION_ITERATION_COUNT("-webkit-animation-iteration-count"),
	/**
	 * -webkit-animation-name
	 */
	WEBKIT_ANIMATION_NAME("-webkit-animation-name"),
	/**
	 * -webkit-box-shadow
	 */
	WEBKIT_BOX_SHADOW("-webkit-box-shadow"),
	/**
	 * width
	 */
	WIDTH("width"),
	/**
	 * word-spacing
	 */
	WORD_SPACING("word-spacing"),
	/**
	 * zoom
	 */
	ZOOM("zoom");

	/**
	 * Finds a property that matches the parameter, if possible.
	 * 
	 * @param property
	 *            The property to match on.
	 * @return The matching property, if found, otherwise null.
	 */
	public static CssProperty get(final String property) {
		for (final CssProperty prop : values()) {
			if (prop.getName().equals(property)) {
				return prop;
			}
		}
		return null;
	}

	private final String name;

	private CssProperty(final String name) {
		this.name = name;
	}

	/**
	 * Returns the usable name of the property.
	 * 
	 * @return The usable name of the property.
	 */
	public String getName() {
		return this.name;
	}

}
