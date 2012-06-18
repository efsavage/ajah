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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.java.Log;

import com.ajah.css.util.RuleComparator;
import com.ajah.util.AjahUtils;
import com.ajah.util.StringUtils;
import com.ajah.util.io.file.FileUtils;

/**
 * Parses a raw CSS file into a {@link CssDocument}. Very much alpha quality!
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
@Log
public class CssParser {

	private static final Pattern propertyPattern = Pattern.compile("\\*{0,1}([-a-z]+)\\s*:\\s*(.*?);$");
	private static final CssParser INSTANCE = new CssParser();

	/**
	 * Return singleton instance of parser.
	 * 
	 * @return Singleton instance of parser.
	 */
	public static CssParser getInstance() {
		return INSTANCE;
	}

	private static List<String> getLines(final String rawCss) {
		final List<String> lines = new ArrayList<>();
		StringBuffer line = new StringBuffer();
		for (final char c : rawCss.toCharArray()) {
			switch (c) {
			case '{':
				if (!StringUtils.isBlank(line.toString())) {
					lines.add(line.toString().trim());
					line = new StringBuffer();
				}
				lines.add("{");
				break;
			case '}':
				if (!StringUtils.isBlank(line.toString())) {
					lines.add(line.toString().trim());
					line = new StringBuffer();
				}
				lines.add("}");
				break;
			case ';':
				line.append(';');
				final String strLine = line.toString().trim();
				if (strLine.startsWith("base64")) {
					final String previousLine = lines.get(lines.size() - 1);
					lines.remove(previousLine);
					lines.add(previousLine + strLine);
				} else {
					lines.add(strLine);
				}
				line = new StringBuffer();
				break;
			case '\r':
				break;
			case '_':
				break;
			case '\n':
				break;
			default:
				line.append(c);
			}
		}
		return lines;
	}

	private static CssRule getRule(final String line, final CssRule parent) {
		return new CssRule(line.replaceAll("\\s\\{", ""), parent);
	}

	/**
	 * Process a file
	 * 
	 * @param args
	 *            The name of the file to process.
	 * @throws IOException
	 *             If the file could not be read.
	 */
	public static void main(final String[] args) throws IOException {
		final String rawCss = FileUtils.readFile(args[0]);
		final CssDocument doc = parse(rawCss);
		for (final CssRule rule : doc.getRules()) {
			log.fine(rule.toString());
			for (final CssSelector selector : rule.getSelectors()) {
				log.fine(selector.getRaw() + ": " + selector.getType());
			}
		}
	}

	private static CssDocument parse(final String rawCss) {
		final List<CssRule> rules = new ArrayList<>();
		final List<String> lines = getLines(rawCss);
		log.finer(lines.size() + " lines");
		for (final String line : lines) {
			log.finest("Line: " + line);
		}
		CssRule currentRule = null;
		boolean inComment = false;
		for (int i = 0; i < lines.size(); i++) {
			final String trimmed = trim(lines.get(i));
			if (StringUtils.isBlank(trimmed)) {
				// Blank line
			} else if (trimmed.startsWith("//")) {
				// Comment
				log.finest("Comment: " + trimmed.substring(2));
			} else if (trimmed.startsWith("/*")) {
				inComment = true;
			} else if (inComment && trimmed.endsWith("*/")) {
				inComment = false;
			} else if (currentRule == null) {
				currentRule = getRule(trimmed, currentRule);
			} else if (trimmed.equals("}")) {
				if (currentRule.getParent() == null) {
					rules.add(currentRule);
					currentRule = null;
				} else {
					currentRule = currentRule.getParent();
				}
			} else if (trimmed.equals("{")) {
				// We're in a rule, this line is extraneous
			} else if (propertyPattern.matcher(trimmed).matches()) {
				final Matcher matcher = propertyPattern.matcher(trimmed);
				matcher.find();
				final String property = matcher.group(1);
				final CssProperty cssProperty = CssProperty.get(property);
				if (cssProperty != null) {
					final CssDeclaration declaration = new CssDeclaration(currentRule, cssProperty, matcher.group(2));
					log.finest("Declaration: " + declaration.toString());
					log.finest("\tValue: " + matcher.group(2));
				} else {
					log.warning("Unknown property: " + property);
					System.err.println("/** " + property + " */");
					System.err.println(property.toUpperCase().replaceAll("-", "_") + "(\"" + property + "\"),");
				}
			} else {
				currentRule = getRule(trimmed, currentRule);
			}
		}
		int i = 0;
		for (final CssRule rule : rules) {
			rule.add(parseSelector(rule.getRaw(), i++));
		}
		Collections.sort(rules, RuleComparator.INSTANCE);
		return new CssDocument().addAll(rules);
	}

	private static CssSelector parseSelector(final String raw, final int position) {
		final CssSelector selector = new CssSelector(raw, position);
		if (raw.matches("[a-z0-9]+")) {
			// Simple element-level selector
			selector.setType(CssSelectorType.ELEMENT);
		} else if (raw.matches("(([a-z0-9]+) *)+")) {
			// Simple element-level selector
			selector.setType(CssSelectorType.ELEMENT_DESCENDENT);
		} else if (raw.matches("\\.[-a-zA-Z0-9]+")) {
			// Simple element-level selector
			selector.setType(CssSelectorType.SIMPLE_CLASS);
		} else if (raw.matches("\\#[-a-zA-Z0-9]+")) {
			// Simple element-level selector
			selector.setType(CssSelectorType.SIMPLE_ID);
		} else if (raw.matches("[a-z0-9]+\\.[-a-zA-Z0-9]+")) {
			// Simple element-level selector
			selector.setType(CssSelectorType.ELEMENT_CLASS);
		} else if (raw.matches("[a-z0-9]+\\#[-a-zA-Z0-9]+")) {
			// Simple element-level selector
			selector.setType(CssSelectorType.ELEMENT_ID);
		} else {
			selector.setType(CssSelectorType.UNKNOWN);
		}
		return selector;
	}

	private static String trim(final String line) {
		return line.replaceAll("/\\*.*\\*/", "").replaceAll("/\\/{2,}/.*", "").trim();
	}

	private CssParser() {
		// Private Constructor
	}

	/**
	 * Parses an {@link InputStream} of raw CSS.
	 * 
	 * @param css
	 *            The raw CSS.
	 * @return The resulting CssDocument.
	 */
	public CssDocument parse(final InputStream css) {
		AjahUtils.requireParam(css, "css");
		final Scanner scanner = new Scanner(css).useDelimiter("\\A");
		if (scanner.hasNext()) {
			return parse(scanner.next());
		}
		return null;
	}

}
