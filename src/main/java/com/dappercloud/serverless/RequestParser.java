package com.dappercloud.serverless;

import java.util.Map;

public class RequestParser {
	public static Map<String, Object> parse(String body) {
		Map<String, Object> m = JsonHandler.toMap(body);
		return m;
	}
}
