/*
 *  Copyright 2013 Eric F. Savage, code@efsavage.com
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
package com.ajah.util.io;

import java.util.Scanner;

import com.ajah.util.StringUtils;

/**
 * Methods for reading input out of a command-line console.
 * 
 * @author <a href="http://efsavage.com">Eric F. Savage</a>, <a
 *         href="mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class ConsoleUtils {

	/**
	 * Asks a question with a text answer, appending the default value if
	 * present.
	 * 
	 * @param message
	 *            The question to ask, required.
	 * @return The response to the question.
	 */
	@SuppressWarnings("resource")
	// Don't need to close System.in
	public static String ask(final String message) {
		final Scanner in = new Scanner(System.in);
		String response = null;
		while (StringUtils.isBlank(response)) {
			System.out.println(message);
			response = in.nextLine().trim();
		}
		return response;
	}

	/**
	 * Asks a yes or no question, appending the default value.
	 * 
	 * @param message
	 *            The question to ask.
	 * @param defaultAnswer
	 *            The answer if someone just hits enter.
	 * @return The response to the question.
	 */
	@SuppressWarnings("resource")
	// Don't need to close System.in
	public static boolean ask(final String message, final boolean defaultAnswer) {
		while (true) {
			boolean answer = defaultAnswer;
			final Scanner in = new Scanner(System.in);
			System.out.println(message + " [" + (defaultAnswer ? 'y' : 'n') + "]");
			final String response = in.nextLine().trim();
			if (StringUtils.isBlank(response)) {
				answer = defaultAnswer;
			} else if ("y".equalsIgnoreCase(response) || "yes".equalsIgnoreCase(response)) {
				answer = true;
			} else if ("n".equalsIgnoreCase(response) || "no".equalsIgnoreCase(response)) {
				answer = false;
			} else {
				System.out.println("Unrecognized response, please answer 'y' or 'n'");
				continue;
			}
			return answer;
		}
	}

	/**
	 * Asks a question, requiring a numerical answer, appending the default
	 * value.
	 * 
	 * @param message
	 *            The question to ask.
	 * @param defaultAnswer
	 *            The answer if someone just hits enter.
	 * @return The response to the question.
	 */
	@SuppressWarnings("resource")
	// Don't need to close System.in
	public static double ask(final String message, final double defaultAnswer) {
		while (true) {
			double answer = defaultAnswer;
			final Scanner in = new Scanner(System.in);
			System.out.println(message + " [" + defaultAnswer + "]");
			final String response = in.nextLine().trim();
			if (StringUtils.isBlank(response)) {
				answer = defaultAnswer;
			} else {
				try {
					answer = Double.parseDouble(response);
				} catch (final NumberFormatException e) {
					System.out.println("Unrecognized response, please answer 'y' or 'n'");
					continue;
				}
			}
			return answer;
		}
	}

	/**
	 * Asks a question with a text answer, appending the default value if
	 * present.
	 * 
	 * @param message
	 *            The question to ask, required.
	 * @param defaultAnswer
	 *            The answer if someone just hits enter.
	 * @return The response to the question.
	 */
	@SuppressWarnings("resource")
	// Don't need to close System.in
	public static String ask(final String message, final String defaultAnswer) {
		final Scanner in = new Scanner(System.in);
		System.out.println(message + (!StringUtils.isBlank(defaultAnswer) ? " [" + defaultAnswer + "]" : ""));
		final String response = in.nextLine().trim();
		if (StringUtils.isBlank(response)) {
			return defaultAnswer;
		}
		return response;
	}

}
