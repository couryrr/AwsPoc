package com.dappercloud.serverless;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHandler {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static JsonNode toJson(String body) {
		System.out.println("In the method to parse");
		JsonNode n;
		try {
			n = mapper.readTree(body);
		} catch (IOException e) {
			n = mapper.createObjectNode().put("error", "Malformed body");
		}
		System.out.println(n.toString());
		return n;
	}

	public static String toJson(Map<String, Object> response) {
		System.out.println("In the method to parse");
		String n = null;
		try {
			n = mapper.writeValueAsString(response.get("payload"));
		} catch (IOException e) {
			// n = mapper.createObjectNode().put("error", "Malformed body");
		}
		return n;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String body) {
		System.out.println("In the method to parse");
		JsonNode n = toJson(body);
		Map<String, Object> m = mapper.convertValue(n, Map.class);
		System.out.println(n.toString());
		return m;
	}

}
