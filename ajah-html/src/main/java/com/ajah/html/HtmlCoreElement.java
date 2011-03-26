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
package com.ajah.html;

import com.ajah.html.attr.ScriptAttribute;
import com.ajah.html.dtd.Direction;

/**
 * Defines attributes that match the core, i18n, and events attributes per the
 * HTML specification.
 * 
 * @author Eric F. Savage <code@efsavage.com>
 * @param <T>
 *            Self-reference to type of implementing class, for fluid methods.
 * 
 */
public interface HtmlCoreElement<T> extends HtmlElement<T> {

	/**
	 * Document-wide unique id.
	 * 
	 * @return The document-wide unique id.
	 */
	String getId();

	/**
	 * Space-separated list of classes
	 * 
	 * @return The space-separated list of classes.
	 */
	String getCssClass();

	/**
	 * Associated style info.
	 * 
	 * @return The associated style info.
	 */
	String getStyle();

	/**
	 * Advisory title.
	 * 
	 * @return The advisory title.
	 */
	String getTitle();

	/**
	 * Language code.
	 * 
	 * @return The language code.
	 */
	String getLang();

	/**
	 * Direction for weak/neutral text
	 * 
	 * @return The direction for weak/neutral text.
	 */
	Direction getDir();

	/**
	 * A pointer button was clicked.
	 * 
	 * @return The script to execute when a pointer button was clicked.
	 */
	ScriptAttribute getOnClick();

	/**
	 * A pointer button was double clicked.
	 * 
	 * @return The script to execute when a pointer button was double clicked.
	 */
	ScriptAttribute getOnDblClick();

	/**
	 * A pointer button was pressed down.
	 * 
	 * @return The script to execute when a pointer button was pressed down.
	 */
	ScriptAttribute getOnMouseDown();

	/**
	 * A pointer button was released.
	 * 
	 * @return The script to execute when a pointer button was released.
	 */
	ScriptAttribute getOnMouseUp();

	/**
	 * A pointer was moved onto.
	 * 
	 * @return The script to execute when a pointer was moved onto.
	 */
	ScriptAttribute getOnMouseOver();

	/**
	 * A pointer was moved within.
	 * 
	 * @return The script to execute when a pointer was moved within.
	 */
	ScriptAttribute getOnMouseMove();

	/**
	 * A pointer was moved away.
	 * 
	 * @return The script to execute when a pointer was moved away.
	 */
	ScriptAttribute getOnMouseOut();

	/**
	 * A key was pressed and released.
	 * 
	 * @return The script to execute when a key was pressed and released.
	 */
	ScriptAttribute getOnKeyPress();

	/**
	 * A key was pressed down
	 * 
	 * @return The script to execute when a key was pressed down.
	 */
	ScriptAttribute getOnKeyDown();

	/**
	 * A key was released.
	 * 
	 * @return The script to execute when a key was released.
	 */
	ScriptAttribute getOnKeyUp();

	/**
	 * Appends a css class. Different than setCssClass, which replaces the class
	 * attribute.
	 * 
	 * @param cssClass
	 *            CSS class to append
	 * @return Reference to target object
	 */
	T css(String cssClass);

	/**
	 * Adds a data-* attribute to the element.
	 * 
	 * See <a href=
	 * "http://dev.w3.org/html5/spec/Overview.html#embedding-custom-non-visible-data-with-the-data-attributes"
	 * >HTML 5 Spec</a> for more information.
	 * 
	 * @param key
	 *            Key of the value, should be safe to be part of an attribute
	 *            name (alphanumeric + dashes).
	 * @param value
	 *            Value of the attribute to set.
	 * @return Reference to target object
	 */
	T data(String key, String value);

}