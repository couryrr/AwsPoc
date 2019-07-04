package com.dappercloud.serverless;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public class Function implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

		Map<String, Object> body = JsonHandler.toMap(input.getBody());

		Map<String, Object> response = new HashMap<String, Object>();
		if (body.containsKey("data")) {
			String token = AuthHandler.grant((Map) body.get("data"));
			response.put("statusCode", 200);

			Map<String, Object> payload = new HashMap<String, Object>();
			payload.put("token", token);
			response.put("payload", payload);
		}

		return ResponseHandler.build(response);

	}

}
