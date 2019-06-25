package com.dappercloud.serverless;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

	public void runner(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		BufferedReader reader = null;
		JsonNode event = null;
		JsonNode body = null;

		ObjectNode payload = mapper.createObjectNode();
		if (inputStream != null) {
			reader = new BufferedReader(new InputStreamReader(inputStream));
			event = mapper.readTree(reader);
			body = mapper.readTree(event.get("body").asText());

		}

		ObjectNode responseJson = mapper.createObjectNode();

		if (body != null) {
			if (body.has("method")) {
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
						DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
						Date date = this.verify(body.get("token").asText());
						
						payload.put("currentTime", dateFormat.format(Calendar.getInstance().getTime()));
						payload.put("expiresAt", dateFormat.format(date));
					}

					break;
				default:
					break;
				}

			}

			responseJson.put("statusCode", 200);
			responseJson.put("body", payload.toString());
		} else {
			responseJson.put("statusCode", 400);
		}

		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
		writer.write(responseJson.toString());
		writer.close();
	}

	public String grant(int duration) {
		String token = null;

		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.MINUTE, duration);

		try {
			//Super insecure please do not use this.
			Algorithm algorithm = Algorithm.HMAC256("secret");
			token = JWT.create().withIssuer("dapper-cloud").withExpiresAt(cal.getTime()).sign(algorithm);
		} catch (JWTCreationException exception) {
			// Invalid Signing configuration / Couldn't convert Claims.
		}

		return token;

	}

	public Date verify(String token) {
		//Not really verifying just checking that it does not fail
		DecodedJWT jwt = null;
		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			JWTVerifier verifier = JWT.require(algorithm).withIssuer("dapper-cloud").build(); // Reusable verifier
																								// instance
			jwt = verifier.verify(token);
		} catch (JWTVerificationException exception) {
			
		}

		return jwt.getExpiresAt();
	}

}
