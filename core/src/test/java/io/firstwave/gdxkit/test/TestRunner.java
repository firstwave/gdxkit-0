package io.firstwave.gdxkit.test;

import io.firstwave.gdxkit.util.Log;
import io.firstwave.gdxkit.util.LogHandler;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * First version created on 5/25/14.
 */
public class TestRunner extends BlockJUnit4ClassRunner {
	static {
		Log.setHandler(LogHandler.DEFAULT);
		Log.setLevel(Log.VERBOSE);
	}

	/**
	 * Creates a BlockJUnit4ClassRunner to run {@code klass}
	 *
	 * @param klass
	 * @throws org.junit.runners.model.InitializationError if the test class is malformed.
	 */
	public TestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

}
