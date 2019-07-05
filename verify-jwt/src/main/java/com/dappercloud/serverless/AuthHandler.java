package com.dappercloud.serverless;

import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthHandler {
	public static boolean verify(Map<String, Object> body) {
		if (body.containsKey("token")) {
			DecodedJWT jwt = null;
			try {
				Algorithm algorithm = Algorithm.HMAC256("secret");
				JWTVerifier verifier = JWT.require(algorithm)
						.withIssuer("dapper-cloud")
						.build();

				jwt = verifier.verify(body.get("token").toString());
			} catch (JWTVerificationException exception) {
				return false;
			}

			return true;
		}
		return false;
	}

}
