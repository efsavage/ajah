/*
 * Copyright 2011 Eric F. Savage, code@efsavage.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ajah.util.text;

import java.util.Arrays;
import java.util.List;

import com.ajah.util.RandomUtils;
import com.ajah.util.StringUtils;

/**
 * Creates random "greek" text for testing purposes.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 * 
 */
public class LoremIpsum {

	/**
	 * The set of words used in this generator.
	 */
	public final static String[] WORDS = new String[] { "a", "ac", "accumsan", "ad", "adipiscing", "aenean", "aliquam", "aliquet", "amet", "ante", "aptent", "arcu", "at", "auctor", "augue",
			"bibendum", "blandit", "class", "commodo", "condimentum", "congue", "consectetur", "consequat", "conubia", "convallis", "cras", "cubilia", "cum", "curabitur", "curae", "cursus",
			"dapibus", "diam", "dictum", "dictumst", "dignissim", "dis", "dolor", "donec", "dui", "duis", "egestas", "eget", "eleifend", "elementum", "elit", "enim", "erat", "eros", "est", "et",
			"etiam", "eu", "euismod", "facilisi", "facilisis", "fames", "faucibus", "felis", "fermentum", "feugiat", "fringilla", "fusce", "gravida", "habitant", "habitasse", "hac", "hendrerit",
			"himenaeos", "iaculis", "id", "imperdiet", "in", "inceptos", "integer", "interdum", "ipsum", "justo", "lacinia", "lacus", "laoreet", "lectus", "leo", "libero", "ligula", "litora",
			"lobortis", "lorem", "luctus", "maecenas", "magna", "magnis", "malesuada", "massa", "mattis", "mauris", "metus", "mi", "molestie", "mollis", "montes", "morbi", "mus", "nam", "nascetur",
			"natoque", "nec", "neque", "netus", "nibh", "nisi", "nisl", "non", "nostra", "nulla", "nullam", "nunc", "odio", "orci", "ornare", "parturient", "pellentesque", "penatibus", "per",
			"pharetra", "phasellus", "placerat", "platea", "porta", "porttitor", "posuere", "potenti", "praesent", "pretium", "primis", "proin", "pulvinar", "purus", "quam", "quis", "quisque",
			"rhoncus", "ridiculus", "risus", "rutrum", "sagittis", "sapien", "scelerisque", "sed", "sem", "semper", "senectus", "sit", "sociis", "sociosqu", "sodales", "sollicitudin", "suscipit",
			"suspendisse", "taciti", "tellus", "tempor", "tempus", "tincidunt", "torquent", "tortor", "tristique", "turpis", "ullamcorper", "ultrices", "ultricies", "urna", "uspendisse", "ut",
			"varius", "vehicula", "vel", "velit", "venenatis", "vestibulum", "vitae", "vivamus", "viverra", "volutpat", "vulputate" };
	/**
	 * The number of unique words available.
	 */
	public final static int WORD_COUNT = WORDS.length;

	/**
	 * {@link #WORDS } as a List.
	 */
	public static final List<String> WORD_LIST = Arrays.asList(WORDS);

	/**
	 * Returns a random word.
	 * 
	 * @return A single word.
	 */
	public static String getWord() {
		return RandomUtils.getRandomElement(WORD_LIST);
	}

	/**
	 * Returns a sentence of 2-12 random words with a period at the end, and
	 * occasional commas.
	 * 
	 * @return A sentence of 2-12 random words.
	 */
	public static String getSentence() {
		return addSentence(new StringBuilder()).toString();
	}

	/**
	 * Returns a sentence of 1-5 random words with a period at the end, and
	 * occasional commas.
	 * 
	 * @return A sentence of 1-5 random words.
	 */
	public static String getProperPhrase() {
		return addProperPhrase(new StringBuilder()).toString();
	}

	/**
	 * Appends a sentence of 2-12 random words with a period at the end, and
	 * occasional commas.
	 * 
	 * @param builder
	 *            The builder to append the sentence to.
	 * 
	 * @return The builder supplied as a parameter, with an appended sentence of
	 *         2-12 random words.
	 */
	public static StringBuilder addProperPhrase(StringBuilder builder) {
		int words = RandomUtils.getRandomNumber(1, 5);
		for (int i = 0; i < words; i++) {
			if (i > 0) {
				builder.append(" ");
			}
			builder.append(StringUtils.capitalize(RandomUtils.getRandomElement(WORD_LIST)));
		}
		return builder;
	}

	/**
	 * Appends a sentence of 2-12 random words with a period at the end, and
	 * occasional commas.
	 * 
	 * @param builder
	 *            The builder to append the sentence to.
	 * 
	 * @return The builder supplied as a parameter, with an appended sentence of
	 *         2-12 random words.
	 */
	public static StringBuilder addSentence(StringBuilder builder) {
		builder.append(StringUtils.capitalize(RandomUtils.getRandomElement(WORD_LIST)));
		int words = RandomUtils.getRandomNumber(2, 12);
		for (int i = 0; i < words; i++) {
			builder.append(" ");
			builder.append(RandomUtils.getRandomElement(WORD_LIST));
			if (i < (words - 1)) {
				if (Math.random() < 0.01) {
					builder.append(",");
				}
			}
		}
		builder.append(".");
		return builder;
	}

	/**
	 * Returns a paragraph of 3-8 sentences.
	 * 
	 * @return A paragraph of 3-8 sentences.
	 */
	public static String getParagraph() {
		return addParagraph(new StringBuilder()).toString();
	}

	/**
	 * Appends a paragraph of 3-8 sentences.
	 * 
	 * @return The builder supplied as a parameter, with an appended paragraph
	 *         of 3-8 sentences.
	 */
	private static StringBuilder addParagraph(StringBuilder builder) {
		int sentences = RandomUtils.getRandomNumber(3, 8);
		for (int i = 0; i < sentences; i++) {
			addSentence(builder);
			builder.append("  ");
		}
		return builder;
	}

	/**
	 * Prints a paragraph.
	 * 
	 * @param args
	 *            Ignored.
	 */
	public static void main(String args[]) {
		System.out.println(getParagraph());
	}

}
