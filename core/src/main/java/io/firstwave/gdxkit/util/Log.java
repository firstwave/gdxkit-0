package io.firstwave.gdxkit.util;

/**
 * Simple logging facility to allow platform agnostic logging.
 * A LogHandler implementation is required to handle logging messages, so clients are free to choose how logging messages are delivered.
 * First version created on 5/25/14.
 */
public class Log {

	public static final int NONE = 0;
	public static final int ERROR = 1;
	public static final int WARN = 2;
	public static final int INFO = 3;
	public static final int DEBUG = 4;
	public static final int VERBOSE = 5;

	private static int logLevel = INFO;
	private static LogHandler handler;

	public static void setLevel(int logLevel) {
		Log.logLevel = logLevel;
	}

	public static int getLevel() {
		return logLevel;
	}

	public static void setHandler(LogHandler handler) {
		Log.handler = handler;
	}

	private static void out(int level, String tag, String message, Throwable t, Object... formatArgs) {
		if (level < ERROR || level > VERBOSE || level > logLevel) return;
		if (handler == null) return;

		if (tag == null) {
			tag = tag();
		}

		if (formatArgs != null) {
			try {
				message = String.format(message, formatArgs);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}

		try {
			handler.handleMessage(level, tag, message, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fetches the name of the class that sent the logging message
	 * @return
	 */
	private static String tag() {
		StackTraceElement e  = Thread.currentThread().getStackTrace()[4];
		return e.getClassName() + "@" + e.getLineNumber();
	}


	public static void v(String message) {
		out(VERBOSE, null, message, null, null);
	}

	public static void v(String tag, String message) {
		out(VERBOSE, tag, message, null, null);
	}

	public static void v(String tag, String message, Throwable t) {
		out(VERBOSE, tag, message, t, null);
	}

	public static void v(String tag, String message, Object... formatArgs) {
		out(VERBOSE, tag, message, null, formatArgs);
	}
	

	public static void d(String message) {
		out(DEBUG, null, message, null, null);
	}
	
	public static void d(String tag, String message) {
		out(DEBUG, tag, message, null, null);
	}
	
	public static void d(String tag, String message, Throwable t) {
		out(DEBUG, tag, message, t, null);
	}
	
	public static void d(String tag, String message, Object... formatArgs) {
		out(DEBUG, tag, message, null, formatArgs);
	}

	public static void i(String message) {
		out(INFO, null, message, null, null);
	}

	public static void i(String tag, String message) {
		out(INFO, tag, message, null, null);
	}

	public static void i(String tag, String message, Throwable t) {
		out(INFO, tag, message, t, null);
	}

	public static void i(String tag, String message, Object... formatArgs) {
		out(INFO, tag, message, null, formatArgs);
	}

	public static void w(String message) {
		out(WARN, null, message, null, null);
	}

	public static void w(String tag, String message) {
		out(WARN, tag, message, null, null);
	}

	public static void w(String tag, String message, Throwable t) {
		out(WARN, tag, message, t, null);
	}

	public static void w(String tag, String message, Object... formatArgs) {
		out(WARN, tag, message, null, formatArgs);
	}

	public static void e(String message) {
		out(ERROR, null, message, null, null);
	}

	public static void e(String tag, String message) {
		out(ERROR, tag, message, null, null);
	}

	public static void e(String tag, String message, Throwable t) {
		out(ERROR, tag, message, t, null);
	}

	public static void e(String tag, String message, Object... formatArgs) {
		out(ERROR, tag, message, null, formatArgs);
	}

}
