package io.firstwave.gdxkit.util;

/**
 * Provides an interface to allow clients the ability to direct log messages
 */
public interface LogHandler {
	public void handleMessage(int level, String tag, String message, Throwable t);
}
