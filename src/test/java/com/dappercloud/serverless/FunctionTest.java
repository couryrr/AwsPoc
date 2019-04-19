package com.dappercloud.serverless;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

public class FunctionTest {
	@Test
	public void healthTest() {
		Function fn = new Function();
		InputStream inputStream = null;
		
		OutputStream outputStream = System.out;
		
		try {
			fn.health(inputStream, outputStream, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
