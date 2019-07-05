package com.dappercloud.serverless;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class AuthHandlerTest {
	
	@Test
	public void grantHappyPathTest() {
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("duration", 25);
		String token = AuthHandler.grant(data);
			
		assertNotNull("Token is generated", token);
		
	}
	
	@Test
	public void grantMissingDurationTest() {
		Map<String, Object> data = new HashMap<String,Object>();
		String token = AuthHandler.grant(data);
			
		assertNull("Token is not generated when no duration is provided", token);
		
	}
}
