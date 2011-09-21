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
import java.util.SortedSet;
import java.util.TreeSet;

import lombok.Data;

import com.ajah.util.io.AjahStringBuilder;
import com.ajah.util.io.Compact;
import com.ajah.util.log.Report;

/**
 * Represents a CSS rule. Example
 * 
 * <code>td {
 * 	border-width:1px;
 * 	padding:5px;
 * 	}</code>
 * 
 * in its entirety, is a rule.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Data
public class CssRule {

	private final String raw;
	private final CssRule parent;
	private final List<CssRule> children = new ArrayList<>();
	private final SortedSet<CssDeclaration> declarations = new TreeSet<>();
	private final List<CssSelector> selectors = new ArrayList<>();

	/**
	 * Public constructor.
	 * 
	 * @param raw
	 *            The raw rule input, required.
	 * @param parent
	 *            The parent of this rule, if applicable. May be null.
	 */
	public CssRule(String raw, CssRule parent) {
		this.raw = raw;
		this.parent = parent;
		if (parent != null) {
			parent.add(this);
		}
	}

	private void add(CssRule cssRule) {
		this.children.add(cssRule);
	}

	/**
	 * Add a declaration to this Rule.
	 * 
	 * @param cssDeclaration
	 */
	public void add(CssDeclaration cssDeclaration) {
		this.declarations.add(cssDeclaration);
	}

	/**
	 * Add a selector to this Rule.
	 * 
	 * @param cssSelector
	 */
	public void add(CssSelector cssSelector) {
		this.selectors.add(cssSelector);
	}

	/**
	 * Returns the valid, usable version of this rule with {@link Compact#NONE}.
	 * 
	 * @see #toString(Compact)
	 */
	@Override
	public String toString() {
		return toString(Compact.NONE, 0);
	}

	/**
	 * Returns the valid, usable version of this document with the specified
	 * level of Compactness.
	 * 
	 * @param compact
	 *            The desired level of compactness.
	 * @return The valid, usable version of this document
	 */
	public String toString(Compact compact) {
		return toString(compact, 0);
	}

	private String toString(Compact compact, int depth) {
		AjahStringBuilder string = new AjahStringBuilder(compact);
		string.append(Report.tabs(depth), Compact.LOW);
		string.append(this.raw);
		string.append(' ', Compact.MED);
		string.append("{");
		string.append(' ', Compact.MED);
		string.append('\n', Compact.LOW);
		for (CssDeclaration declaration : this.declarations) {
			string.append(Report.tabs(depth + 1), Compact.LOW);
			string.append(declaration.toString());
			string.append('\n', Compact.MED);
		}
		for (CssRule child : this.children) {
			string.append(child.toString(compact, depth + 1));
			string.append('\n', Compact.MED);
		}
		string.append(Report.tabs(depth), Compact.LOW);
		string.append('\t', Compact.LOW);
		string.append("}");
		return string.toString();
	}

}
