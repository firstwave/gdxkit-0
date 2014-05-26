package io.firstwave.gdxkit.util;

import io.firstwave.gdxkit.test.TestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * First version created on 5/25/14.
 */
@RunWith(TestRunner.class)
public class LogTest {

	@Test
	public void testLogging() {
		Log.d("This is a message");
		Log.d("LogTest", "This is a message");
		Log.d("LogTest", "This is a message", new RuntimeException("Test exception handling"));
		Log.d("LogTest", "This is a message (%d)", 2);
		Log.d("LogTest", "This is a message (%d)", 2, 2);
		Log.d("LogTest", null, 4);
	}
}
