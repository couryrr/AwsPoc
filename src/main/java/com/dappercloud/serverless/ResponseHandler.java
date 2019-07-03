package com.dappercloud.serverless;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class ResponseHandler {

	public static APIGatewayProxyResponseEvent build(Map<String, Object> payload) {
		int statusCode = Integer.parseInt(payload.get("statusCode").toString());
		return new APIGatewayProxyResponseEvent().withBody(JsonHandler.toJson(payload))
				.withStatusCode(statusCode);
	}

}
