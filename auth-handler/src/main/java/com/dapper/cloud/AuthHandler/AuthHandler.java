package com.dapper.cloud.AuthHandler;

import java.util.Calendar;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthHandler {
	public static String grant(Map<String, Object> body) {
		String token = null;
		if (body.containsKey("duration")) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, Integer.parseInt(body.get("duration").toString()));

			try {
				// Super insecure please do not use this.
				Algorithm algorithm = Algorithm.HMAC256("secret");
				token = JWT.create().withIssuer("dapper-cloud").withExpiresAt(cal.getTime()).sign(algorithm);
			} catch (JWTCreationException exception) {
				token = exception.getMessage();
			}

			return token;
		}

		return null;

	}

	public static boolean verify(Map<String, Object> body) {
		if (body.containsKey("token")) {
			DecodedJWT jwt = null;
			try {
				Algorithm algorithm = Algorithm.HMAC256("secret");
				JWTVerifier verifier = JWT.require(algorithm).withIssuer("dapper-cloud").build();

				jwt = verifier.verify(body.get("token").toString());
			} catch (JWTVerificationException exception) {
				return false;
			}

			return true;
		}
		return false;
	}
}