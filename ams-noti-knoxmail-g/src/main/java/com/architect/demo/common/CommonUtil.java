package com.architect.demo.common;

import java.util.Arrays;

public class CommonUtil {

	public static boolean isStringNullOrEmpty(final String... strs) {
		return Arrays.asList(strs).stream().anyMatch(str -> {
			return str == null || str.isEmpty();
		});
	}
}
