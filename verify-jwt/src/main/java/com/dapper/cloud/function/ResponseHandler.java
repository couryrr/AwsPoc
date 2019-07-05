package com.dapper.cloud.function;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class ResponseHandler {

	public static APIGatewayProxyResponseEvent build(Map<String, Object> response) {
		int statusCode = Integer.parseInt(response.get("statusCode").toString());
		Map<String, Object> payload = (Map<String, Object>)response.get("payload");
		return new APIGatewayProxyResponseEvent().withBody(JsonHandler.toJson(payload))
				.withStatusCode(statusCode);
	}

}
