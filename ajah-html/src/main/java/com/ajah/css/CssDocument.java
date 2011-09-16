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

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import com.ajah.util.io.AjahStringBuilder;
import com.ajah.util.io.Compact;

/**
 * A wrapper around a set of {@link CssRule}s.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class CssDocument {

	private final List<CssRule> rules = new ArrayList<>();

	/**
	 * Add these rules to this document.
	 * 
	 * @param newRules
	 *            The rules to add.
	 * @return This document.
	 */
	public CssDocument addAll(List<CssRule> newRules) {
		this.rules.addAll(newRules);
		return this;
	}

	/**
	 * Returns the valid, usable version of this document with
	 * {@link Compact#NONE}.
	 * 
	 * @see #toString(Compact)
	 */
	@Override
	public String toString() {
		return toString(Compact.NONE);
	}

	/**
	 * Returns the valid, usable version of this document with the specified
	 * level of compactness.
	 * 
	 * @param compact
	 *            The desired level of compactness.
	 * @return The valid, usable version of this document
	 */
	public String toString(Compact compact) {
		AjahStringBuilder string = new AjahStringBuilder(compact);
		for (CssRule rule : this.rules) {
			string.append(rule.toString(compact));
			string.append('\n', Compact.MAX);
		}
		return string.toString();
	}
}
