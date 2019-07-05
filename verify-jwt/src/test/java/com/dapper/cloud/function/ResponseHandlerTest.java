package com.dapper.cloud.function;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.dapper.cloud.function.ResponseHandler;

public class ResponseHandlerTest {
	
	@Test
	public void buildHappyPathTest() {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("statusCode", 200);
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("test", true);
		response.put("payload", payload);
		APIGatewayProxyResponseEvent event = ResponseHandler.build(response);
		
		assertEquals("This is a mock object", "{statusCode: 200,body: {\"test\":true}}", event.toString());
		
	}

}
