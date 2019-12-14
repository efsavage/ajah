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

	/** animation */
	ANIMATION("animation"),
	// Permitted CSS Properties
	/**
	 * background
	 */
	BACKGROUND("background"),
	/** background-clip */
	BACKGROUND_CLIP("background-clip"),
	/**
	 * background-color
	 */
	BACKGROUND_COLOR("background-color"),
	/**
	 * background-image
	 */
	BACKGROUND_IMAGE("background-image"),
	/** background-position */
	BACKGROUND_POSITION("background-position"),
	/** background-repeat */
	BACKGROUND_REPEAT("background-repeat"),
	/** background-size */
	BACKGROUND_SIZE("background-size"),
	/**
	 * border
	 */
	BORDER("border"),
	/**
	 * border-bottom
	 */
	BORDER_BOTTOM("border-bottom"),
	/** border-bottom-color */
	BORDER_BOTTOM_COLOR("border-bottom-color"),
	/** border-bottom-left-radius */
	BORDER_BOTTOM_LEFT_RADIUS("border-bottom-left-radius"),
	/** border-bottom-right-radius */
	BORDER_BOTTOM_RIGHT_RADIUS("border-bottom-right-radius"),
	/** border-bottom-width */
	BORDER_BOTTOM_WIDTH("border-bottom-width"),
	/**
	 * border-collapse
	 */
	BORDER_COLLAPSE("border-collapse"),
	/**
	 * border-color
	 */
	BORDER_COLOR("border-color"),
	/** border-left */
	BORDER_LEFT("border-left"),
	/** border-left-color */
	BORDER_LEFT_COLOR("border-left-color"),
	/** border-left-width */
	BORDER_LEFT_WIDTH("border-left-width"),
	/**
	 * border-radius
	 */
	BORDER_RADIUS("border-radius"),
	/** border-right */
	BORDER_RIGHT("border-right"),
	/** border-right-color */
	BORDER_RIGHT_COLOR("border-right-color"),
	/** border-right-width */
	BORDER_RIGHT_WIDTH("border-right-width"),
	/**
	 * border-spacing
	 */
	BORDER_SPACING("border-spacing"),
	/**
	 * border-style
	 */
	BORDER_STYLE("border-style"),
	/**
	 * border-top
	 */
	BORDER_TOP("border-top"),
	/**
	 * border-top-color
	 */
	BORDER_TOP_COLOR("border-top-color"),
	/**
	 * border-top-left-radius
	 */
	BORDER_TOP_LEFT_RADIUS("border-top-left-radius"),
	/**
	 * border-top-right-radius
	 */
	BORDER_TOP_RIGHT_RADIUS("border-top-right-radius"),
	/**
	 * border-top-width
	 */
	BORDER_TOP_WIDTH("border-top-width"),
	/**
	 * border-width
	 */
	BORDER_WIDTH("border-width"),
	/**
	 * bottom
	 */
	BOTTOM("bottom"),
	/**
	 * box-shadow
	 */
	BOX_SHADOW("box-shadow"),
	/** box-sizing */
	BOX_SIZING("box-sizing"),
	/** clear */
	CLEAR("clear"),
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
	 * height
	 */
	HEIGHT("height"),
	/**
	 * left
	 */
	LEFT("left"),
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
	/** margin-right */
	MARGIN_RIGHT("margin-right"),
	/** margin-top */
	MARGIN_TOP("margin-top"),
	/** max-height */
	MAX_HEIGHT("max-height"),
	/** max-width */
	MAX_WIDTH("max-width"),
	/** min-height */
	MIN_HEIGHT("min-height"),
	/**
	 * min-width
	 */
	MIN_WIDTH("min-width"),
	/**
	 * -moz-animation
	 */
	MOZ_ANIMATION("-moz-animation"),
	/**
	 * -moz-background-clip
	 */
	MOZ_BACKGROUND_CLIP("-moz-background-clip"),
	/**
	 * -moz-background-size
	 */
	MOZ_BACKGROUND_SIZE("-moz-background-size"),
	/**
	 * -moz-border-radius
	 */
	MOZ_BORDER_RADIUS("-moz-border-radius"),
	/**
	 * -moz-border-radius-bottomleft
	 */
	MOZ_BORDER_RADIUS_BOTTOMLEFT("-moz-border-radius-bottomleft"),
	/**
	 * -moz-border-radius-bottomright
	 */
	MOZ_BORDER_RADIUS_BOTTOMRIGHT("-moz-border-radius-bottomright"),
	/**
	 * -moz-border-radius-topleft
	 */
	MOZ_BORDER_RADIUS_TOPLEFT("-moz-border-radius-topleft"),
	/**
	 * -moz-border-radius-topright
	 */
	MOZ_BORDER_RADIUS_TOPRIGHT("-moz-border-radius-topright"),
	/**
	 * -moz-box-shadow
	 */
	MOZ_BOX_SHADOW("-moz-box-shadow"),
	/**
	 * -moz-box-sizing
	 */
	MOZ_BOX_SIZING("-moz-box-sizing"),
	/**
	 * -moz-transition
	 */
	MOZ_TRANSITION("-moz-transition"),
	/**
	 * -ms-animation
	 */
	MS_ANIMATION("-ms-animation"),
	/**
	 * -ms-box-sizing
	 */
	MS_BOX_SIZING("-ms-box-sizing"),
	/**
	 * -ms-interpolation-mode
	 */
	MS_INTERPOLATION_MODE("-ms-interpolation-mode"),
	/**
	 * -ms-text-size-adjust
	 */
	MS_TEXT_SIZE_ADJUST("-ms-text-size-adjust"),
	/**
	 * -ms-transition
	 */
	MS_TRANSITION("-ms-transition"),
	/**
	 * -o-animation
	 */
	O_ANIMATION("-o-animation"),
	/**
	 * -o-background-size
	 */
	O_BACKGROUND_SIZE("-o-background-size"),
	/**
	 * -o-transition
	 */
	O_TRANSITION("-o-transition"),
	/**
	 * opacity
	 */
	OPACITY("opacity"),
	/**
	 * outline
	 */
	OUTLINE("outline"),
	/** outline-offset */
	OUTLINE_OFFSET("outline-offset"),
	/** overflow */
	OVERFLOW("overflow"),
	/** overflow-y */
	OVERFLOW_Y("overflow-y"),
	/** padding */
	PADDING("padding"),
	/**
	 * padding-bottom
	 */
	PADDING_BOTTOM("padding-bottom"),
	/**
	 * padding-left
	 */
	PADDING_LEFT("padding-left"),
	/**
	 * padding-right
	 */
	PADDING_RIGHT("padding-right"),
	/**
	 * padding-top
	 */
	PADDING_TOP("padding-top"),
	/** position */
	POSITION("position"),
	/** resize */
	RESIZE("resize"),
	/** right */
	RIGHT("right"),
	/** text-align */
	TEXT_ALIGN("text-align"),
	/** text-decoration */
	TEXT_DECORATION("text-decoration"),
	/** text-indent */
	TEXT_INDENT("text-indent"),
	/** text-overflow */
	TEXT_OVERFLOW("text-overflow"),
	/**
	 * text-rendering
	 */
	TEXT_RENDERING("text-rendering"),
	/**
	 * text-shadow
	 */
	TEXT_SHADOW("text-shadow"),
	/**
	 * text-transform
	 */
	TEXT_TRANSFORM("text-transform"),
	/**
	 * top
	 */
	TOP("top"),
	/**
	 * transition
	 */
	TRANSITION("transition"),
	/**
	 * vertical-align
	 */
	VERTICAL_ALIGN("vertical-align"),
	/**
	 * visibility
	 */
	VISIBILITY("visibility"),
	/**
	 * -webkit-animation
	 */
	WEBKIT_ANIMATION("-webkit-animation"),
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
	 * -webkit-appearance
	 */
	WEBKIT_APPEARANCE("-webkit-appearance"),
	/** -webkit-background-clip */
	WEBKIT_BACKGROUND_CLIP("-webkit-background-clip"),
	/** -webkit-background-size */
	WEBKIT_BACKGROUND_SIZE("-webkit-background-size"),
	/** -webkit-border-bottom-left-radius */
	WEBKIT_BORDER_BOTTOM_LEFT_RADIUS("-webkit-border-bottom-left-radius"),
	/** -webkit-border-bottom-right-radius */
	WEBKIT_BORDER_BOTTOM_RIGHT_RADIUS("-webkit-border-bottom-right-radius"),
	/** -webkit-border-radius */
	WEBKIT_BORDER_RADIUS("-webkit-border-radius"),
	/** -webkit-border-top-left-radius */
	WEBKIT_BORDER_TOP_LEFT_RADIUS("-webkit-border-top-left-radius"),
	/** -webkit-border-top-right-radius */
	WEBKIT_BORDER_TOP_RIGHT_RADIUS("-webkit-border-top-right-radius"),
	/** -webkit-box-shadow */
	WEBKIT_BOX_SHADOW("-webkit-box-shadow"),
	/** -webkit-box-sizing */
	WEBKIT_BOX_SIZING("-webkit-box-sizing"),
	/** -webkit-margin-top-collapse */
	WEBKIT_MARGIN_TOP_COLLAPSE("-webkit-margin-top-collapse"),
	/** -webkit-text-size-adjust */
	WEBKIT_TEXT_SIZE_ADJUST("-webkit-text-size-adjust"),
	/** -webkit-transition */
	WEBKIT_TRANSITION("-webkit-transition"),
	/** white-space */
	WHITE_SPACE("white-space"),
	/** width */
	WIDTH("width"),
	/** word-break */
	WORD_BREAK("word-break"),
	/** word-spacing */
	WORD_SPACING("word-spacing"),
	/** word-wrap */
	WORD_WRAP("word-wrap"),
	/** z-index */
	Z_INDEX("z-index"),
	/** zoom */
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

	CssProperty(final String name) {
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
