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
	public static boolean ask(String message, boolean defaultAnswer) {
		while (true) {
			boolean answer = defaultAnswer;
			Scanner in = new Scanner(System.in);
			System.out.println(message + " [" + (defaultAnswer ? 'y' : 'n') + "]");
			String response = in.nextLine().trim();
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
}
