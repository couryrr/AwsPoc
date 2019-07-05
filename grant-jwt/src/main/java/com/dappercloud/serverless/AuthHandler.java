package com.dappercloud.serverless;

import java.util.Calendar;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

public class AuthHandler {
	public static String grant(Map<String, Object> body) {
		String token = null;
		if (body.containsKey("duration")) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, Integer.parseInt(body.get("duration").toString()));

			try {
				// Super insecure please do not use this.
				Algorithm algorithm = Algorithm.HMAC256("secret");
				token = JWT.create()
						.withIssuer("dapper-cloud")
						.withExpiresAt(cal.getTime())
						.sign(algorithm);
			} catch (JWTCreationException exception) {
				token = exception.getMessage();
			}

			return token;
		}

		return null;

	}
}
