/*
 *  Copyright 2013-2014 Eric F. Savage, code@efsavage.com
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
package com.ajah.report;

import java.util.Set;
import java.util.TreeSet;

import com.ajah.lang.ListMap;
import com.ajah.util.text.Strings;

/**
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class Report {

	ListMap<String, String> sections = new ListMap<>();

	public void add(final String section, final String message) {
		add(section, message, false);
	}

	public void add(final String section, final String message, final boolean unique) {
		if (!unique || this.sections.getList(section) == null || !this.sections.getList(section).contains(message)) {
			this.sections.putValue(section, message);
		}
	}

	public String getHtml() {
		final StringBuilder text = new StringBuilder();
		final Set<String> sortedSections = new TreeSet<>(this.sections.keySet());
		for (final String section : sortedSections) {
			text.append("<h2>" + section + "</h2>");
			text.append(System.lineSeparator());
			text.append("<hr />");
			text.append(System.lineSeparator());
			text.append("<ul>");
			for (final String message : this.sections.get(section)) {
				text.append("<li>" + message + "</li>");
				text.append(System.lineSeparator());
			}
			text.append("</ul>");
			text.append(System.lineSeparator());
		}
		return text.toString();
	}

	public String getText() {
		final StringBuilder text = new StringBuilder();
		for (final String section : this.sections.keySet()) {
			text.append(section);
			text.append(System.lineSeparator());
			text.append(Strings.HYPEN35);
			text.append(System.lineSeparator());
			for (final String message : this.sections.get(section)) {
				text.append(message);
				text.append(System.lineSeparator());
			}
			text.append(System.lineSeparator());
		}
		return text.toString();
	}

}
