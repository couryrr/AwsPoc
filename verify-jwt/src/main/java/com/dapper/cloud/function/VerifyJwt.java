package com.dapper.cloud.function;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.dapper.cloud.AuthHandler.AuthHandler;

public class VerifyJwt implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

		Map<String, Object> body = JsonHandler.toMap(input.getBody());

		Map<String, Object> response = new HashMap<String, Object>();
		boolean valid = AuthHandler.verify((Map)body);
		response.put("statusCode", 200);

		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("valid", valid);
		response.put("payload", payload);

		return ResponseHandler.build(response);

	}

}
