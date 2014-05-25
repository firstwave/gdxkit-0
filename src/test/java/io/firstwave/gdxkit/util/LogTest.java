package io.firstwave.gdxkit.util;

import org.junit.Test;

/**
 * First version created on 5/25/14.
 */
public class LogTest {

	@Test
	public void testLogging() {
		Log.setHandler(new Log.StdHandler());
		Log.setLevel(Log.DEBUG);
		Log.d("This is a message");
		Log.d("LogTest", "This is a message");
		Log.d("LogTest", "This is a message", new RuntimeException("Test exception handling"));
		Log.d("LogTest", "This is a message (%d)", 2);
		Log.d("LogTest", "This is a message (%d)", 2, 2);
		Log.d("LogTest", null, 4);
	}
}
