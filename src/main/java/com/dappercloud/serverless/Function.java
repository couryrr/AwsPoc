package com.dappercloud.serverless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import com.amazonaws.services.lambda.runtime.Context;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Function {

	private static final ObjectMapper mapper = new ObjectMapper();

	public void runner(InputStream inputStream, OutputStream outputStream, Context context) {

		JsonNode event = null;

		try {
			event = parse(inputStream);
		} catch (IOException e) {
			// Set empty request response
		}

		JsonNode body = null;
		try {
			body = parse(event);
		} catch (IOException e) {
			// Set empty request response
		}

		ObjectNode responseJson = router(body);
		send(outputStream, responseJson);

	}

	private JsonNode parse(InputStream is) throws IOException {
		BufferedReader reader = null;
		if (is != null) {
			reader = new BufferedReader(new InputStreamReader(is));
			return mapper.readTree(reader);
		}
		return null;
	}

	private JsonNode parse(JsonNode event) throws IOException {
		if (event.has("body")) {
			return mapper.readTree(event.get("body").asText());
		}
		return null;
	}

	private ObjectNode router(JsonNode body) {
		ObjectNode responseJson = mapper.createObjectNode();
		ObjectNode payload = mapper.createObjectNode();

		if (body != null && body.has("method")) {
			String method = body.get("method").asText();
			switch (method) {
			case "health":
				payload.put("health", "good");
				break;

			case "request":
				if (body.has("duration")) {
					String token = this.grant(body.get("duration").asInt());
					payload.put("token", token);
				}
				break;
			case "verify":
				if (body.has("token")) {
					boolean valid = this.verify(body.get("token").asText());
					payload.put("valid", valid);
				}

				break;
			default:
				responseJson.put("statusCode", 400);
				payload.put("error", "Method not found");
				responseJson.put("body", payload.toString());
				break;
			}

			responseJson.put("statusCode", 200);

		} else {
			responseJson.put("statusCode", 400);
			payload.put("error", "Malformed body");
			responseJson.put("body", payload.toString());
		}

		responseJson.put("body", payload.toString());

		return responseJson;
	}

	private void send(OutputStream outputStream, ObjectNode responseJson) {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
			writer.write(responseJson.toString());
			writer.close();
		} catch (UnsupportedEncodingException e) {

		} catch (IOException e) {

		}
	}
	
	public String grant(int duration) {
		String token = null;

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MINUTE, duration);

		try {
			// Super insecure please do not use this.
			Algorithm algorithm = Algorithm.HMAC256("secret");
			token = JWT.create().withIssuer("dapper-cloud").withExpiresAt(cal.getTime()).sign(algorithm);
		} catch (JWTCreationException exception) {
			// Invalid Signing configuration / Couldn't convert Claims.
		}

		return token;

	}

	public boolean verify(String token) {
		// Not really verifying just checking that it does not fail
		DecodedJWT jwt = null;
		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("dapper-cloud").build(); // Reusable verifier
																								// instance
			jwt = verifier.verify(token);
		} catch (JWTVerificationException exception) {
			return false;
		}

		return true;
	}

}
