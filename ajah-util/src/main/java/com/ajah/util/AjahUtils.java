package com.ajah.util;

public class AjahUtils {

	public static void requireParam(Object parameter, String name) {
		if (parameter == null) {
			throw new IllegalArgumentException(name + " cannot be null");
		}
		if (parameter instanceof String && ((String) parameter).length() < 1) {
			throw new IllegalArgumentException(name + " cannot be empty");
		}
	}

}