package com.dappercloud.serverless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.amazonaws.services.lambda.runtime.Context;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Function {

	public void health(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		String name = null;
		if (context != null) {
			name = context.getFunctionName();
		}

		ObjectMapper mapper = new ObjectMapper();
		
		BufferedReader reader = null;
		JsonNode event = null;
		JsonNode body = null;
		
		if (inputStream != null) {
			reader = new BufferedReader(new InputStreamReader(inputStream));
			event = mapper.readTree(reader);
			body = event.get("body");
		}


		ObjectNode responseJson = mapper.createObjectNode();

		ObjectNode responseBody = mapper.createObjectNode();
		responseBody.put("health", "good");
		
		if(name != null) {
			responseBody.put("function", name);
		}
		if(body != null) {
			responseBody.put("event", body.toString());
		}

		responseJson.put("statusCode", 200);
		responseJson.put("body", responseBody.toString());
		

		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
		writer.write(responseJson.toString());
		writer.close();
	}
}
