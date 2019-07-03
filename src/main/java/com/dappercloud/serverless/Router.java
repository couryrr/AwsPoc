package com.dappercloud.serverless;

import java.util.HashMap;
import java.util.Map;

public class Router {

	public static Map<String, Object> route(Map<String, Object> body) {
		Map<String, Object> response = new HashMap<String, Object>();
		Map<String, Object> payload = new HashMap<String, Object>();
		if (body.containsKey("method") && body.containsKey("data")) {
			switch (body.get("method").toString()) {
			case "health":
				response.put("statusCode", 200);
				payload.put("health", "good");
				break;
			case "grant":
				String token = AuthHandler.grant((Map)body.get("data"));
				response.put("statusCode", 200);
				payload.put("token", token);
				break;
			case "verify":
				boolean valid = AuthHandler.verify((Map)body.get("data"));
				response.put("statusCode", 200);
				payload.put("valid", valid);
				break;
			default:
				response.put("statusCode", 400);
				payload.put("error", "Method not found");
				break;
			}
		} else {
			response.put("statusCode", 400);
			payload.put("error", "Malformed body");
		}

		
		response.put("payload", payload);
		return response;
	}
}
